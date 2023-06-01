package com.diploma.qoldan.repository.donation;

import com.diploma.qoldan.model.donation.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepo extends JpaRepository<Donation, Integer> {

    @Query(
            value = "select * from donation " +
                    "where donation.id in " +
                    "(select donation.id from donation " +
                    "left join donation_announcement on donation.announcement_id = donation_announcement.id " +
                    "where donation_announcement.organization_id = ?1)",
            nativeQuery = true
    )
    List<Donation> findAllByOrganization(Long organizationId);

    Donation findById(Long id);
}
