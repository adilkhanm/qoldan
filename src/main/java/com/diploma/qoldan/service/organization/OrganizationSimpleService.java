package com.diploma.qoldan.service.organization;

import com.diploma.qoldan.exception.organization.OrganizationNotFoundException;
import com.diploma.qoldan.model.organization.Organization;
import com.diploma.qoldan.repository.organization.OrganizationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationSimpleService {

    private final OrganizationRepo repo;

    public Organization findOrganizationById(Long id) throws OrganizationNotFoundException {
        Organization organization = repo.findById(id);
        if (organization == null)
            throw new OrganizationNotFoundException("id: " + id);
        return organization;
    }

    public Organization findOrganizationByName(String name) throws OrganizationNotFoundException {
        Organization organization = repo.findByName(name);
        if (organization == null)
            throw new OrganizationNotFoundException("name:  " + name);
        return organization;
    }
}
