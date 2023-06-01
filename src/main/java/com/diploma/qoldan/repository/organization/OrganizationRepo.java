package com.diploma.qoldan.repository.organization;

import com.diploma.qoldan.model.organization.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepo extends JpaRepository<Organization, Integer> {

    Organization findById(Long id);
    Organization findByName(String name);

}
