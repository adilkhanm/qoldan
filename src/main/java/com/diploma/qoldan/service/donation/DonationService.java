package com.diploma.qoldan.service.donation;

import com.diploma.qoldan.dto.donation.DonationDto;
import com.diploma.qoldan.enums.DonationStatusEnum;
import com.diploma.qoldan.exception.donation.DonationAnnouncementNotFoundException;
import com.diploma.qoldan.exception.donation.DonationNotFoundException;
import com.diploma.qoldan.exception.donation.DonationStatusNotFound;
import com.diploma.qoldan.exception.image.ImageNotFoundException;
import com.diploma.qoldan.exception.user.UserHasNoAccessException;
import com.diploma.qoldan.mapper.donation.DonationMapper;
import com.diploma.qoldan.model.donation.Donation;
import com.diploma.qoldan.model.donation.DonationAnnouncement;
import com.diploma.qoldan.model.image.Image;
import com.diploma.qoldan.model.item.Item;
import com.diploma.qoldan.model.organization.Organization;
import com.diploma.qoldan.model.user.User;
import com.diploma.qoldan.repository.donation.DonationRepo;
import com.diploma.qoldan.service.image.ImageService;
import com.diploma.qoldan.service.user.UserSimpleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DonationService {

    private final DonationRepo repo;

    private final DonationMapper mapper;

    private final ImageService imageService;
    private final UserSimpleService userService;
    private final DonationAnnouncementSimpleService announcementService;

    public List<DonationDto> getMyDonations(String username) {
        User user = userService.findUserByUsername(username);
        List<Donation> donations = user.getDonations();
        return donations
                .stream()
                .map(mapper::mapDonationToDto)
                .toList();
    }

    public List<DonationDto> getDonationsToConfirm(String username) {
        User user = userService.findUserByUsername(username);
        Organization organization = user.getOrganization();

        List<Donation> donations = repo.findAllByOrganization(organization.getId());
        return donations
                .stream()
                .map(mapper::mapDonationToDto)
                .toList();
    }

    @Transactional
    public Long createDonation(DonationDto dto, String username)
            throws DonationAnnouncementNotFoundException, ImageNotFoundException {
        User user = userService.findUserByUsername(username);

        DonationAnnouncement announcement = announcementService.findById(dto.getAnnouncementId());
        Image image = null;
        if (dto.getItemImageId() != null)
            image = imageService.findById(dto.getItemImageId());

        Item item = Item.builder()
                .title(dto.getItemTitle())
                .summary(dto.getItemSummary())
                .mainImage(image)
                .category(announcement.getCategory())
                .user(user)
                .build();

        Donation donation = Donation.builder()
                .status(DonationStatusEnum.PENDING.toString())
                .quantity(dto.getQuantity())
                .item(item)
                .user(user)
                .announcement(announcement)
                .build();
        repo.save(donation);

        return donation.getId();
    }

    @Transactional
    public void processDonation(Long id, String status, String username)
            throws DonationNotFoundException, UserHasNoAccessException, DonationStatusNotFound {
        Donation donation = findById(id);
        if (!donation.getAnnouncement().getOrganization().getUser().getEmail().equals(username))
            throw new UserHasNoAccessException("");

        if (!donation.getStatus().equals(DonationStatusEnum.PENDING.toString()))
            throw new UserHasNoAccessException("");

        try {
            donation.setStatus(DonationStatusEnum.valueOf(status).toString());
        } catch (IllegalArgumentException e) {
            throw new DonationStatusNotFound("");
        }

        if (donation.getStatus().equals(DonationStatusEnum.CONFIRMED.toString())) {
            DonationAnnouncement announcement = donation.getAnnouncement();
            announcementService.addDonationsToAnnouncements(announcement, donation.getQuantity());
        }

        repo.save(donation);
    }

    public Donation findById(Long id) throws DonationNotFoundException {
        Donation donation = repo.findById(id);
        if (donation == null)
            throw new DonationNotFoundException("ID: " + id);
        return donation;
    }
}
