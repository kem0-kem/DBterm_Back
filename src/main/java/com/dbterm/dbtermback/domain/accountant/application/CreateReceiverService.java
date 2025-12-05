package com.dbterm.dbtermback.domain.accountant.application;

import com.dbterm.dbtermback.domain.accountant.dto.request.CreateReceiverRequest;
import com.dbterm.dbtermback.domain.accountant.dto.response.ReceiverResponse;
import com.dbterm.dbtermback.domain.accountant.entity.Receiver;
import com.dbterm.dbtermback.domain.accountant.repository.ReceiverRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateReceiverService {

    private final ReceiverRepository receiverRepository;

    public CreateReceiverService(ReceiverRepository receiverRepository) {
        this.receiverRepository = receiverRepository;
    }

    @Transactional
    public ReceiverResponse create(CreateReceiverRequest request) {
        Receiver receiver = new Receiver(
                request.getName(),
                request.getType(),
                request.getBankAccount()
        );
        Receiver saved = receiverRepository.save(receiver);
        return ReceiverResponse.fromEntity(saved);
    }
}
