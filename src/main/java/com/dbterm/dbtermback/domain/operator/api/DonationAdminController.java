package com.dbterm.dbtermback.domain.operator.api;

import com.dbterm.dbtermback.domain.operator.application.GetUnverifiedDonationsService;
import com.dbterm.dbtermback.domain.operator.application.VerifyDonationService;
import com.dbterm.dbtermback.domain.operator.dto.response.UnverifiedDonationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/api/donations")
public class DonationAdminController {

    private final GetUnverifiedDonationsService getUnverifiedDonationsService;
    private final VerifyDonationService verifyDonationService;

    public DonationAdminController(GetUnverifiedDonationsService getUnverifiedDonationsService,
                                   VerifyDonationService verifyDonationService) {
        this.getUnverifiedDonationsService = getUnverifiedDonationsService;
        this.verifyDonationService = verifyDonationService;
    }

    @PreAuthorize("hasRole('OPERATOR') or hasRole('ADMIN')")
    @GetMapping("/unverified")
    public List<UnverifiedDonationResponse> getUnverifiedDonations(
            @RequestParam(name = "date", required = false) String dateParam
    ) {
        LocalDate date = null;

        if (dateParam == null || dateParam.isBlank() || "today".equalsIgnoreCase(dateParam)) {
            date = null; // service 쪽에서 null 이면 LocalDate.now() 사용
        } else {
            try {
                date = LocalDate.parse(dateParam);
            } catch (DateTimeParseException e) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Invalid date format. Use 'today' or ISO-8601 date (e.g. 2025-12-06)."
                );
            }
        }

        return getUnverifiedDonationsService.getUnverifiedDonations(date);
    }

    @PreAuthorize("hasRole('OPERATOR') or hasRole('ADMIN')")
    @PatchMapping("/{id}/verify")
    public ResponseEntity<Void> verifyDonation(@PathVariable("id") Long donationId) {
        verifyDonationService.verifyDonation(donationId);
        return ResponseEntity.noContent().build();
    }
}
