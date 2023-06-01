package com.diploma.qoldan.service.donation;

import com.diploma.qoldan.dto.donation.DonationAnnouncementDto;
import com.diploma.qoldan.enums.DonationAnnouncementStatusEnum;
import com.diploma.qoldan.exception.category.CategoryNotFoundException;
import com.diploma.qoldan.exception.donation.AnnouncementIsNotActiveException;
import com.diploma.qoldan.exception.donation.DonationAnnouncementNotFoundException;
import com.diploma.qoldan.exception.organization.OrganizationNotFoundException;
import com.diploma.qoldan.exception.user.UserHasNoAccessException;
import com.diploma.qoldan.mapper.donation.DonationAnnouncementMapper;
import com.diploma.qoldan.model.category.Category;
import com.diploma.qoldan.model.donation.DonationAnnouncement;
import com.diploma.qoldan.model.organization.Organization;
import com.diploma.qoldan.model.user.User;
import com.diploma.qoldan.repository.donation.DonationAnnouncementRepo;
import com.diploma.qoldan.service.category.CategorySimpleService;
import com.diploma.qoldan.service.organization.OrganizationSimpleService;
import com.diploma.qoldan.service.user.UserSimpleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DonationAnnouncementService {

    private final DonationAnnouncementRepo repo;

    private final DonationAnnouncementMapper mapper;

    private final DonationAnnouncementSimpleService service;
    private final UserSimpleService userService;
    private final CategorySimpleService categoryService;

    public List<DonationAnnouncementDto> getAnnouncements(String organizationName, String status) {
        List<DonationAnnouncement> announcementList = repo.findAll();

        Stream<DonationAnnouncement> announcementStream = announcementList.stream().filter(donationAnnouncement -> {
            boolean okay = true;
            if (organizationName != null)
                okay &= donationAnnouncement.getOrganization().getName().equals(organizationName);
            if (status != null)
                okay &= donationAnnouncement.getStatus().equals(status);
            return okay;
        });

        return announcementStream
                .map(mapper::mapAnnouncementToDto)
                .toList();
    }

    public List<DonationAnnouncementDto> getMyAnnouncements(String username, String status) {
        User user = userService.findUserByUsername(username);
        Organization organization = user.getOrganization();
        List<DonationAnnouncement> announcementList = organization.getAnnouncements();
        Stream<DonationAnnouncement> announcementStream = announcementList.stream();
        if (status != null)
            announcementStream = announcementStream
                    .filter(donationAnnouncement -> donationAnnouncement.getStatus().equals(status));
        return announcementStream
                .map(mapper::mapAnnouncementToDto)
                .toList();
    }

    public DonationAnnouncementDto getAnnouncement(Long id)
            throws DonationAnnouncementNotFoundException {
        DonationAnnouncement announcement = service.findById(id);
        return mapper.mapAnnouncementToDto(announcement);
    }

    @Transactional
    public void postAnnouncement(DonationAnnouncementDto dto, String username)
            throws CategoryNotFoundException {
        User user = userService.findUserByUsername(username);
        Organization organization = user.getOrganization();
        Category category = categoryService.findCategoryByTitle(dto.getCategory());

        DonationAnnouncement announcement = DonationAnnouncement.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .quantityNeeded(dto.getQuantityNeeded())
                .quantityCollected(0)
                .category(category)
                .organization(organization)
                .status(DonationAnnouncementStatusEnum.ACTIVE.toString())
                .build();
        repo.save(announcement);
    }

    @Transactional
    public void updateAnnouncement(DonationAnnouncementDto dto, String username)
            throws CategoryNotFoundException, DonationAnnouncementNotFoundException, UserHasNoAccessException {

        DonationAnnouncement announcement = service.findById(dto.getId());
        if (!announcement.getOrganization().getUser().getEmail().equals(username))
            throw new UserHasNoAccessException("");

        Category category = categoryService.findCategoryByTitle(dto.getCategory());

        announcement.setTitle(dto.getTitle());
        announcement.setDescription(dto.getDescription());
        announcement.setQuantityNeeded(dto.getQuantityNeeded());
        announcement.setCategory(category);
        repo.save(announcement);
    }

    @Transactional
    public void completeAnnouncement(Long id, String username)
            throws UserHasNoAccessException, DonationAnnouncementNotFoundException, AnnouncementIsNotActiveException {
        DonationAnnouncement announcement = service.findById(id);
        if (!announcement.getOrganization().getUser().getEmail().equals(username))
            throw new UserHasNoAccessException("");
        if (!announcement.getStatus().equals(DonationAnnouncementStatusEnum.ACTIVE.toString()))
            throw new AnnouncementIsNotActiveException("ID: " + id);

        announcement.setStatus(DonationAnnouncementStatusEnum.COMPLETED.toString());
        repo.save(announcement);
    }
}
