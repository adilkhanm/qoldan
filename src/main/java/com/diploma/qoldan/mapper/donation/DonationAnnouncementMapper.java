package com.diploma.qoldan.mapper.donation;

import com.diploma.qoldan.dto.donation.DonationAnnouncementDto;
import com.diploma.qoldan.model.donation.DonationAnnouncement;
import org.springframework.stereotype.Component;

@Component
public class DonationAnnouncementMapper {

    public DonationAnnouncementDto mapAnnouncementToDto(DonationAnnouncement donationAnnouncement) {
        return DonationAnnouncementDto.builder()
                .id(donationAnnouncement.getId())
                .title(donationAnnouncement.getTitle())
                .description(donationAnnouncement.getDescription())
                .quantityNeeded(donationAnnouncement.getQuantityNeeded())
                .quantityCollected(donationAnnouncement.getQuantityCollected())
                .status(donationAnnouncement.getStatus())
                .category(donationAnnouncement.getCategory().getTitle())
                .organization(donationAnnouncement.getOrganization().getName())
                .organizationImageId(donationAnnouncement.getOrganization().getImage().getId())
                .build();
    }
}
