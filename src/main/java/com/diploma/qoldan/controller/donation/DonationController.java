package com.diploma.qoldan.controller.donation;

import com.diploma.qoldan.dto.donation.DonationDto;
import com.diploma.qoldan.exception.donation.DonationAnnouncementNotFoundException;
import com.diploma.qoldan.exception.donation.DonationNotFoundException;
import com.diploma.qoldan.exception.donation.DonationStatusNotFound;
import com.diploma.qoldan.exception.image.ImageNotFoundException;
import com.diploma.qoldan.exception.user.UserHasNoAccessException;
import com.diploma.qoldan.service.donation.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/donations")
@RequiredArgsConstructor
public class DonationController {

    private final DonationService service;

    @GetMapping("/my")
    public ResponseEntity<List<DonationDto>> getMyDonations(Authentication auth) {
        List<DonationDto> dtoList = service.getMyDonations(auth.getName());
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/to-organization")
    public ResponseEntity<List<DonationDto>> getDonationToConfirm(Authentication auth) {
        List<DonationDto> dtoList = service.getDonationsToConfirm(auth.getName());
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping
    public ResponseEntity<Long> createDonation(@RequestBody DonationDto dto,
                                               Authentication auth)
            throws DonationAnnouncementNotFoundException, ImageNotFoundException {
        Long id = service.createDonation(dto, auth.getName());
        return ResponseEntity.ok(id);
    }

    @PutMapping("/to-organization/{id}")
    public ResponseEntity<String> processDonation(@PathVariable Long id,
                                                  @RequestParam("status") String status,
                                                  Authentication auth)
            throws DonationStatusNotFound, DonationNotFoundException, UserHasNoAccessException {
        service.processDonation(id, status, auth.getName());
        return ResponseEntity.ok("Donation was processed successfully");
    }

}
