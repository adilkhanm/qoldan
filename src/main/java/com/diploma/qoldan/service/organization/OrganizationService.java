package com.diploma.qoldan.service.organization;

import com.diploma.qoldan.dto.organization.OrganizationDto;
import com.diploma.qoldan.enums.RoleEnum;
import com.diploma.qoldan.exception.image.ImageNotFoundException;
import com.diploma.qoldan.exception.organization.OrganizationNotFoundException;
import com.diploma.qoldan.exception.user.UserHasNoAccessException;
import com.diploma.qoldan.mapper.organization.OrganizationMapper;
import com.diploma.qoldan.model.address.Address;
import com.diploma.qoldan.model.image.Image;
import com.diploma.qoldan.model.organization.Organization;
import com.diploma.qoldan.model.user.Role;
import com.diploma.qoldan.model.user.User;
import com.diploma.qoldan.repository.organization.OrganizationRepo;
import com.diploma.qoldan.service.address.AddressSimpleService;
import com.diploma.qoldan.service.image.ImageService;
import com.diploma.qoldan.service.user.UserSimpleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.RoleNotFoundException;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepo repo;

    private final OrganizationMapper mapper;

    private final OrganizationSimpleService service;
    private final ImageService imageService;
    private final UserSimpleService userService;
    private final AddressSimpleService addressService;

    public OrganizationDto getOrganization(Long id)
            throws OrganizationNotFoundException {
        Organization organization = service.findOrganizationById(id);
        return mapper.mapOrganizationToDto(organization);
    }

    @Transactional
    public void updateOrganization(OrganizationDto dto, String username)
            throws OrganizationNotFoundException, UserHasNoAccessException, ImageNotFoundException {
        Organization organization = service.findOrganizationById(dto.getId());
        if (!organization.getUser().getEmail().equals(username))
            throw new UserHasNoAccessException("User " + username + " cannot update organization info");

        Image image = imageService.findById(dto.getImageId());

        organization.setName(dto.getName());
        organization.setDescription(dto.getDescription());
        organization.setImage(image);
        repo.save(organization);
    }

    public Long createOrganization(OrganizationDto dto)
            throws ImageNotFoundException, RoleNotFoundException {
        Image image = imageService.findById(dto.getImageId());
        User user = userService.findUserById(dto.getUserId());
        Address address = addressService.createAddressForNewUser();

        Role role = userService.findRoleByName(RoleEnum.ROLE_ORGANIZATION.toString());

        if (!user.getRoles().contains(role))
            throw new IllegalArgumentException("The user cannot have organization. Incorrect user role");

        Organization organization = Organization.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .image(image)
                .user(user)
                .address(address)
                .build();
        repo.save(organization);

        return organization.getId();
    }
}
