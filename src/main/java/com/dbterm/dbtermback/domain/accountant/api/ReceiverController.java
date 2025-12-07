package com.dbterm.dbtermback.domain.accountant.api;

import com.dbterm.dbtermback.domain.accountant.application.CreateReceiverService;
import com.dbterm.dbtermback.domain.accountant.dto.request.CreateReceiverRequest;
import com.dbterm.dbtermback.domain.accountant.dto.response.ReceiverResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/receivers")
@PreAuthorize("hasRole('OPERATOR') or hasRole('ADMIN')")
public class ReceiverController {

    private final CreateReceiverService createReceiverService;

    public ReceiverController(CreateReceiverService createReceiverService) {
        this.createReceiverService = createReceiverService;
    }

    @PostMapping
    public ReceiverResponse createReceiver(@RequestBody CreateReceiverRequest request) {
        return createReceiverService.create(request);
    }
}
