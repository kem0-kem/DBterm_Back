-- =========================================================
-- Transparent Donation DB Schema (PostgreSQL)
-- =========================================================
-- 논리 모델 ↔ 실제 테이블명 매핑
-- 1. User        -> app_user
-- 2. Campaign    -> campaign
-- 3. Donation    -> donation
-- 4. Receiver    -> receiver
-- 5. Allocation  -> allocation
-- 6. Disbursement-> disbursement
-- 7. Document    -> document
-- 8. Audit_Log   -> audit_log
-- =========================================================

-- -----------------------------------------------------------------
-- 0. 타입 & 도메인 정의
-- -----------------------------------------------------------------
-- ENUM(user_role, allocation_status, disbursement_status)는 더 이상 사용하지 않음
-- 역할/상태는 VARCHAR로 관리
-- 금액 공통 도메인 (양수 또는 0)
CREATE DOMAIN money_amount AS NUMERIC(18,2)
  CHECK (VALUE >= 0);

-- -----------------------------------------------------------------
-- 1. User(user_id, username, role, is_anonymous)
--    - Primary key: user_id
--    - Unique: username
--    - Authorization: Admin만 삽입/수정 가능 (DB 레벨 권한은 뒤쪽 GRANT에서 예시)
-- -----------------------------------------------------------------
CREATE TABLE app_user (
  user_id      BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  username     VARCHAR(100) NOT NULL,
  password     VARCHAR(255) NOT NULL,
  name         VARCHAR(100) NOT NULL,
  phone_num    VARCHAR(30)  NOT NULL,
  role         VARCHAR(20)  NOT NULL DEFAULT 'DONOR',
  is_anonymous BOOLEAN      NOT NULL DEFAULT FALSE,

  created_at   TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  updated_at   TIMESTAMPTZ NOT NULL DEFAULT NOW(),

  CONSTRAINT uq_app_user_username UNIQUE (username)
);

CREATE OR REPLACE FUNCTION trg_app_user_set_updated_at()
RETURNS TRIGGER LANGUAGE plpgsql AS $$
BEGIN
  NEW.updated_at := NOW();
  RETURN NEW;
END;
$$;

CREATE TRIGGER trg_app_user_set_updated_at
BEFORE UPDATE ON app_user
FOR EACH ROW
EXECUTE FUNCTION trg_app_user_set_updated_at();

-- -----------------------------------------------------------------
-- 2. Campaign(campaign_id, title, description, department, goal_amount,
--             start_date, end_date, created_by)
--    - Primary key: campaign_id
--    - Foreign key: created_by → User(user_id)
-- -----------------------------------------------------------------
CREATE TABLE campaign (
  campaign_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  title       VARCHAR(255) NOT NULL,
  description TEXT,
  department  VARCHAR(100),
  goal_amount money_amount,
  start_date  DATE NOT NULL,
  end_date    DATE NOT NULL,
  created_by  BIGINT NOT NULL REFERENCES app_user(user_id),

  created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  updated_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),

  CONSTRAINT chk_campaign_date_range CHECK (end_date >= start_date)
);

CREATE OR REPLACE FUNCTION trg_campaign_set_updated_at()
RETURNS TRIGGER LANGUAGE plpgsql AS $$
BEGIN
  NEW.updated_at := NOW();
  RETURN NEW;
END;
$$;

CREATE TRIGGER trg_campaign_set_updated_at
BEFORE UPDATE ON campaign
FOR EACH ROW
EXECUTE FUNCTION trg_campaign_set_updated_at();

-- -----------------------------------------------------------------
-- 3. Donation(donation_id, donor_id, campaign_id, amount,
--             payment_method, donated_at, verified)
--    - Primary key: donation_id
--    - Foreign key: donor_id → User(user_id)
--                    campaign_id → Campaign(campaign_id)
--    - Constraint: amount > 0
-- -----------------------------------------------------------------
CREATE TABLE donation (
  donation_id    BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  donor_id       BIGINT NOT NULL REFERENCES app_user(user_id),
  campaign_id    BIGINT NOT NULL REFERENCES campaign(campaign_id),

  amount         NUMERIC(18,2) NOT NULL,
  payment_method VARCHAR(30)   NOT NULL,
  donated_at     TIMESTAMPTZ   NOT NULL DEFAULT NOW(),
  verified       BOOLEAN       NOT NULL DEFAULT FALSE,

  CONSTRAINT chk_donation_amount_positive CHECK (amount > 0)
);

-- -----------------------------------------------------------------
-- 4. Receiver(receiver_id, name, type, bank_account)
--    - Primary key: receiver_id
-- -----------------------------------------------------------------
CREATE TABLE receiver (
  receiver_id  BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  name         VARCHAR(255) NOT NULL,
  type         VARCHAR(100) NOT NULL,           -- 예: 'INDIVIDUAL', 'NGO', ...
  bank_account VARCHAR(255) NOT NULL,           -- 실제 운영 시 별도 암호화/토큰화 고려
  created_at   TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  updated_at   TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE OR REPLACE FUNCTION trg_receiver_set_updated_at()
RETURNS TRIGGER LANGUAGE plpgsql AS $$
BEGIN
  NEW.updated_at := NOW();
  RETURN NEW;
END;
$$;

CREATE TRIGGER trg_receiver_set_updated_at
BEFORE UPDATE ON receiver
FOR EACH ROW
EXECUTE FUNCTION trg_receiver_set_updated_at();

-- -----------------------------------------------------------------
-- 5. Allocation(allocation_id, donation_id, campaign_id, receiver_id, amount, status)
--    - Primary key: allocation_id
--    - Foreign key: donation_id → Donation(donation_id)
--                    campaign_id → Campaign(campaign_id)
--                    receiver_id → Receiver(receiver_id)
--    - Constraint: amount ≥ 0
--                  같은 donation에 대한 Allocation의 합이 Donation.amount를 초과하지 않도록 트리거
-- -----------------------------------------------------------------
CREATE TABLE allocation (
  allocation_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  donation_id   BIGINT NOT NULL REFERENCES donation(donation_id),
  campaign_id   BIGINT NOT NULL REFERENCES campaign(campaign_id),
  receiver_id   BIGINT NOT NULL REFERENCES receiver(receiver_id),

  amount        money_amount NOT NULL,
  status        VARCHAR(30) NOT NULL DEFAULT 'PENDING',

  created_at    TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  updated_at    TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE OR REPLACE FUNCTION trg_allocation_set_updated_at()
RETURNS TRIGGER LANGUAGE plpgsql AS $$
BEGIN
  NEW.updated_at := NOW();
  RETURN NEW;
END;
$$;

CREATE TRIGGER trg_allocation_set_updated_at
BEFORE UPDATE ON allocation
FOR EACH ROW
EXECUTE FUNCTION trg_allocation_set_updated_at();

-- donation별 allocation 합계가 donation.amount를 넘지 않도록 검사
CREATE OR REPLACE FUNCTION fn_check_allocation_total()
RETURNS TRIGGER LANGUAGE plpgsql AS $$
DECLARE
  v_donation_amount NUMERIC(18,2);
  v_allocated_sum   NUMERIC(18,2);
BEGIN
  SELECT amount INTO v_donation_amount
  FROM donation
  WHERE donation_id = NEW.donation_id;

  IF v_donation_amount IS NULL THEN
    RAISE EXCEPTION 'Donation % not found for allocation check', NEW.donation_id;
  END IF;

  -- 이미 저장된 다른 allocation 합 + 현재 NEW.amount
  SELECT COALESCE(SUM(a.amount), 0)
  INTO v_allocated_sum
  FROM allocation a
  WHERE a.donation_id = NEW.donation_id
    AND (TG_OP = 'INSERT' OR a.allocation_id <> NEW.allocation_id);

  v_allocated_sum := v_allocated_sum + NEW.amount;

  IF v_allocated_sum > v_donation_amount THEN
    RAISE EXCEPTION
      'Total allocation (%.2f) exceeds donation amount (%.2f) for donation_id=%',
      v_allocated_sum, v_donation_amount, NEW.donation_id;
  END IF;

  RETURN NEW;
END;
$$;

CREATE TRIGGER trg_check_allocation_total
BEFORE INSERT OR UPDATE OF amount, donation_id
ON allocation
FOR EACH ROW
EXECUTE FUNCTION fn_check_allocation_total();

-- -----------------------------------------------------------------
-- 6. Disbursement(disbursement_id, allocation_id, executed_by, executed_at,
--                 amount, status, payment_tx_ref)
--    - Primary key: disbursement_id
--    - Foreign key: allocation_id → Allocation(allocation_id)
--                    executed_by → User(user_id)
--    - Constraint: allocation_id UNIQUE (한 allocation 당 1회 지급)
--                  amount = allocation.amount 가 원칙 → 트리거로 검사
-- -----------------------------------------------------------------
CREATE TABLE disbursement (
  disbursement_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  allocation_id   BIGINT NOT NULL REFERENCES allocation(allocation_id),
  executed_by     BIGINT NOT NULL REFERENCES app_user(user_id),

  executed_at     TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  amount          money_amount NOT NULL,
  status          VARCHAR(20) NOT NULL DEFAULT 'PENDING',
  payment_tx_ref  VARCHAR(255),

  CONSTRAINT uq_disbursement_allocation UNIQUE (allocation_id)
);

-- disbursement.amount = allocation.amount 검사
CREATE OR REPLACE FUNCTION fn_check_disbursement_amount()
RETURNS TRIGGER LANGUAGE plpgsql AS $$
DECLARE
  v_alloc_amount NUMERIC(18,2);
BEGIN
  SELECT amount INTO v_alloc_amount
  FROM allocation
  WHERE allocation_id = NEW.allocation_id;

  IF v_alloc_amount IS NULL THEN
    RAISE EXCEPTION 'Allocation % not found for disbursement check', NEW.allocation_id;
  END IF;

  IF NEW.amount <> v_alloc_amount THEN
    RAISE EXCEPTION
      'Disbursement amount (%.2f) must equal allocation amount (%.2f) for allocation_id=%',
      NEW.amount, v_alloc_amount, NEW.allocation_id;
  END IF;

  RETURN NEW;
END;
$$;

CREATE TRIGGER trg_check_disbursement_amount
BEFORE INSERT OR UPDATE OF amount, allocation_id
ON disbursement
FOR EACH ROW
EXECUTE FUNCTION fn_check_disbursement_amount();

-- -----------------------------------------------------------------
-- 7. Document(document_id, disbursement_id, storage_path, file_hash, uploaded_by)
--    - Primary key: document_id
--    - Foreign key: disbursement_id → Disbursement(disbursement_id)
--                    uploaded_by → User(user_id)
--    - Constraint: file_hash NOT NULL
-- -----------------------------------------------------------------
CREATE TABLE document (
  document_id     BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  disbursement_id BIGINT NOT NULL REFERENCES disbursement(disbursement_id),
  storage_path    TEXT   NOT NULL,
  file_hash       TEXT   NOT NULL,   -- 위변조 검증용
  uploaded_by     BIGINT NOT NULL REFERENCES app_user(user_id),
  uploaded_at     TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 후원자에게는 전체 경로 대신 요약 정보만 제공하기 위한 VIEW
CREATE OR REPLACE VIEW v_document_summary_for_donor AS
SELECT
  d.document_id,
  d.disbursement_id,
  ds.status       AS disbursement_status,
  ds.amount       AS disbursement_amount,
  d.uploaded_at,
  d.uploaded_by
FROM document d
JOIN disbursement ds ON ds.disbursement_id = d.disbursement_id;

-- -----------------------------------------------------------------
-- 8. Audit_Log(audit_id, event_time, actor_user_id, action, object_table,
--              object_id, old_data, new_data)
--    - Primary key: audit_id
--    - Foreign key: actor_user_id → User(user_id)
--    - Constraint: append-only (삭제/수정 불가)
-- -----------------------------------------------------------------
CREATE TABLE audit_log (
  audit_id      BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  event_time    TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  actor_user_id BIGINT REFERENCES app_user(user_id),
  action        VARCHAR(100) NOT NULL,
  object_table  VARCHAR(100) NOT NULL,
  object_id     BIGINT,
  old_data      JSONB,
  new_data      JSONB
);

-- audit_log는 UPDATE/DELETE 금지
CREATE OR REPLACE FUNCTION fn_prevent_audit_log_modification()
RETURNS TRIGGER LANGUAGE plpgsql AS $$
BEGIN
  RAISE EXCEPTION 'audit_log is append-only; % not allowed', TG_OP;
END;
$$;

CREATE TRIGGER trg_prevent_audit_log_update
BEFORE UPDATE ON audit_log
FOR EACH ROW
EXECUTE FUNCTION fn_prevent_audit_log_modification();

CREATE TRIGGER trg_prevent_audit_log_delete
BEFORE DELETE ON audit_log
FOR EACH ROW
EXECUTE FUNCTION fn_prevent_audit_log_modification();

-- -----------------------------------------------------------------
-- 9. 권한(Authorization) 설계를 위한 예시 역할/권한 부여
--    실제 서비스에서는 애플리케이션 계정, RLS, 보안 정책과 함께 튜닝 필요
-- -----------------------------------------------------------------

-- 예시 DB ROLE 정의 (원하면 애플리케이션에서만 사용 / 매핑)
CREATE ROLE role_admin;
CREATE ROLE role_operator;
CREATE ROLE role_accountant;
CREATE ROLE role_auditor;
CREATE ROLE role_donor;
CREATE ROLE role_system;   -- 배분 로직/트리거용 애플리케이션 계정 등

-- 기본 권한 제거
REVOKE ALL ON ALL TABLES IN SCHEMA public FROM PUBLIC;

-- app_user: Admin만 INSERT/UPDATE 가능하게 예시
GRANT SELECT ON app_user TO role_admin, role_operator, role_accountant, role_auditor;
GRANT INSERT, UPDATE, DELETE ON app_user TO role_admin;

-- campaign: Operator만 생성/수정, 후원자는 조회만
GRANT SELECT ON campaign TO role_donor, role_operator, role_admin;
GRANT INSERT, UPDATE, DELETE ON campaign TO role_operator, role_admin;

-- donation: 후원자 본인 또는 결제 모듈만 INSERT (실제론 애플리케이션 계정에서 처리)
GRANT SELECT ON donation TO role_admin, role_operator, role_accountant;
GRANT UPDATE (verified) ON donation TO role_operator, role_admin;
-- INSERT/UPDATE는 보통 role_system 계정으로만 허용하고, 애플리케이션이 그 계정으로 동작하게 구성
GRANT INSERT ON donation TO role_system;

-- receiver: Accountant/관리자만 INSERT/UPDATE
GRANT SELECT ON receiver TO role_admin, role_accountant, role_operator;
GRANT INSERT, UPDATE, DELETE ON receiver TO role_admin, role_accountant;

-- allocation: 시스템 계정/운영자만 INSERT, status 승인/반려는 운영자/회계
GRANT SELECT ON allocation TO role_admin, role_accountant, role_operator;
GRANT INSERT ON allocation TO role_system, role_operator;
GRANT UPDATE (status) ON allocation TO role_operator, role_accountant, role_admin;

-- disbursement: Accountant/관리자만 INSERT/UPDATE
GRANT SELECT ON disbursement TO role_admin, role_accountant, role_operator;
GRANT INSERT, UPDATE ON disbursement TO role_admin, role_accountant;

-- document: 담당자/운영자만 업로드, 후원자는 VIEW만
GRANT SELECT, INSERT ON document TO role_admin, role_accountant, role_operator;
REVOKE ALL ON document FROM role_donor;
GRANT SELECT ON v_document_summary_for_donor TO role_donor;

-- audit_log: 애플리케이션/트리거만 INSERT, Auditor/관리자만 전체 조회
GRANT INSERT ON audit_log TO role_system;
GRANT SELECT ON audit_log TO role_admin, role_auditor;

-- 여기까지가 기본 스키마 + 핵심 제약 조건입니다.
-- 애플리케이션 로그인 사용자 ↔ DB ROLE 매핑/Row Level Security 등은
-- 실제 구현 전략에 맞게 추가하시면 됩니다.
