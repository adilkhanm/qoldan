package com.diploma.qoldan.controller.organization;

import com.diploma.qoldan.dto.donation.DonationAnnouncementDto;
import com.diploma.qoldan.dto.organization.OrganizationDto;
import com.diploma.qoldan.exception.image.ImageNotFoundException;
import com.diploma.qoldan.exception.organization.OrganizationNotFoundException;
import com.diploma.qoldan.exception.user.UserHasNoAccessException;
import com.diploma.qoldan.model.donation.Donation;
import com.diploma.qoldan.service.donation.DonationAnnouncementService;
import com.diploma.qoldan.service.organization.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/organization")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService service;

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationDto> getOrganization(@PathVariable Long id)
            throws OrganizationNotFoundException {
        OrganizationDto dto = service.getOrganization(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<Long> createOrganization(@RequestBody OrganizationDto dto)
            throws RoleNotFoundException, ImageNotFoundException {
        Long id = service.createOrganization(dto);
        return ResponseEntity.ok(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrganization(@PathVariable Long id,
                                                     @RequestBody OrganizationDto dto,
                                                     Authentication auth)
            throws OrganizationNotFoundException, UserHasNoAccessException, ImageNotFoundException {
        dto.setId(id);
        service.updateOrganization(dto, auth.getName());
        return ResponseEntity.ok("Organization info was successfully updated");
    }

}
