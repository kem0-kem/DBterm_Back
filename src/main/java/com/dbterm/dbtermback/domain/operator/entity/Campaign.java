package com.dbterm.dbtermback.domain.operator.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "campaign")
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "campaign_id")
    private Long id;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "department", length = 100)
    private String department;

    @Column(name = "goal_amount")
    private Double goalAmount;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    public Campaign() {
    }

    public static Campaign create(String title,
                                  String description,
                                  String department,
                                  Double goalAmount,
                                  LocalDate startDate,
                                  LocalDate endDate,
                                  Long createdBy) {
        Campaign c = new Campaign();
        c.title = title;
        c.description = description;
        c.department = department;
        c.goalAmount = goalAmount;
        c.startDate = startDate;
        c.endDate = endDate;
        c.createdBy = createdBy;
        return c;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDepartment() {
        return department;
    }

    public Double getGoalAmount() {
        return goalAmount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
