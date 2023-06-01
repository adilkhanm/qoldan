package com.diploma.qoldan.mapper.organization;

import com.diploma.qoldan.dto.organization.OrganizationDto;
import com.diploma.qoldan.model.organization.Organization;
import org.springframework.stereotype.Component;

@Component
public class OrganizationMapper {
    public OrganizationDto mapOrganizationToDto(Organization organization) {
        return OrganizationDto.builder()
                .id(organization.getId())
                .name(organization.getName())
                .description(organization.getDescription())
                .imageId(organization.getImage().getId())
                .build();
    }
}
