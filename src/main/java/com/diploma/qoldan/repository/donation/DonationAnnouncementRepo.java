package com.diploma.qoldan.repository.donation;

import com.diploma.qoldan.model.donation.DonationAnnouncement;
import com.diploma.qoldan.model.organization.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationAnnouncementRepo extends JpaRepository<DonationAnnouncement, Integer> {
    DonationAnnouncement findById(Long id);
    List<DonationAnnouncement> findAllByOrganization(Organization organization);
}
