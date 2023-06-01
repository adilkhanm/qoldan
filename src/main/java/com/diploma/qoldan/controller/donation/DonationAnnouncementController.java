package com.diploma.qoldan.controller.donation;

import com.diploma.qoldan.dto.donation.DonationAnnouncementDto;
import com.diploma.qoldan.exception.category.CategoryNotFoundException;
import com.diploma.qoldan.exception.donation.AnnouncementIsNotActiveException;
import com.diploma.qoldan.exception.donation.DonationAnnouncementNotFoundException;
import com.diploma.qoldan.exception.user.UserHasNoAccessException;
import com.diploma.qoldan.model.donation.DonationAnnouncement;
import com.diploma.qoldan.service.donation.DonationAnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/donation-announcements")
@RequiredArgsConstructor
public class DonationAnnouncementController {

    private final DonationAnnouncementService service;

    @GetMapping()
    public ResponseEntity<List<DonationAnnouncementDto>> getAnnouncements(@RequestParam(value = "organizationName", required = false) String organizationName,
                                                                          @RequestParam(value = "status", required = false) String status) {
        List<DonationAnnouncementDto> dtoList = service.getAnnouncements(organizationName, status);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/my")
    public ResponseEntity<List<DonationAnnouncementDto>> getMyAnnouncement(@RequestParam(value = "status", required = false) String status,
                                                                           Authentication auth) {
        List<DonationAnnouncementDto> dtoList = service.getMyAnnouncements(auth.getName(), status);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonationAnnouncementDto> getAnnouncement(@PathVariable Long id) throws DonationAnnouncementNotFoundException {
        DonationAnnouncementDto dto = service.getAnnouncement(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping()
    public ResponseEntity<String> postAnnouncement(@RequestBody DonationAnnouncementDto dto,
                                                   Authentication auth)
            throws CategoryNotFoundException {
        service.postAnnouncement(dto, auth.getName());
        return ResponseEntity.ok("Announcement was successfully posted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAnnouncement(@PathVariable Long id,
                                                     @RequestBody DonationAnnouncementDto dto,
                                                     Authentication auth)
            throws DonationAnnouncementNotFoundException, CategoryNotFoundException, UserHasNoAccessException {
        dto.setId(id);
        service.updateAnnouncement(dto, auth.getName());
        return ResponseEntity.ok("Announcement was successfully updated");
    }

    @PutMapping("/complete/{id}")
    public ResponseEntity<String> completeAnnouncement(@PathVariable Long id,
                                                       Authentication auth)
            throws DonationAnnouncementNotFoundException, AnnouncementIsNotActiveException, UserHasNoAccessException {
        service.completeAnnouncement(id, auth.getName());
        return ResponseEntity.ok("Announcement was completed");
    }

}
