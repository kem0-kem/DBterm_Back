package com.dbterm.dbtermback.domain.donor.api;

import com.dbterm.dbtermback.domain.donor.application.GetMyDonationsService;
import com.dbterm.dbtermback.domain.donor.dto.response.DonationSummaryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/donations")
public class MyDonationController {

    private final GetMyDonationsService getMyDonationsService;

    public MyDonationController(GetMyDonationsService getMyDonationsService) {
        this.getMyDonationsService = getMyDonationsService;
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<DonationSummaryResponse>> getMyDonations(
            @RequestParam(name = "period", required = false, defaultValue = "all") String period,
            @RequestParam(name = "sortBy", required = false, defaultValue = "donatedAt") String sortBy,
            @RequestParam(name = "order", required = false, defaultValue = "desc") String order
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long donorId = (Long) auth.getPrincipal();

        List<DonationSummaryResponse> result = getMyDonationsService.getMyDonations(donorId, period, sortBy, order);
        return ResponseEntity.ok(result);
    }
}
