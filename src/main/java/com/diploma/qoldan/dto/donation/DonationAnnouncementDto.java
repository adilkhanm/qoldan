package com.diploma.qoldan.dto.donation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DonationAnnouncementDto {
    private Long id;
    private String title;
    private String description;
    private Integer quantityNeeded;
    private Integer quantityCollected;
    private String status;
    private String category;
    private String organization;
    private Long organizationImageId;
}
