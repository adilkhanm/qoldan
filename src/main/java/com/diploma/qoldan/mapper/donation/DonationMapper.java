package com.diploma.qoldan.mapper.donation;

import com.diploma.qoldan.dto.donation.DonationDto;
import com.diploma.qoldan.model.donation.Donation;
import org.springframework.stereotype.Component;

@Component
public class DonationMapper {
    public DonationDto mapDonationToDto(Donation donation) {
        return DonationDto.builder()
                .id(donation.getId())
                .status(donation.getStatus())
                .quantity(donation.getQuantity())
                .username(donation.getUser().getEmail())
                .userMobile(donation.getUser().getMobile())
                .announcementId(donation.getAnnouncement().getId())
                .announcementTitle(donation.getAnnouncement().getTitle())
                .itemTitle(donation.getItem().getTitle())
                .itemSummary(donation.getItem().getSummary())
                .itemImageId(donation.getItem().getMainImage().getId())
                .build();
    }
}
