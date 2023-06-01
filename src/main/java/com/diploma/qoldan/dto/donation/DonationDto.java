package com.diploma.qoldan.dto.donation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DonationDto {
    private Long id;
    private String status;
    private Integer quantity;

    private String username;
    private String userMobile;
    private Long announcementId;
    private String announcementTitle;

    private String itemTitle;
    private String itemSummary;
    private Long itemImageId;
}
