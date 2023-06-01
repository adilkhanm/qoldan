package com.diploma.qoldan.service.donation;

import com.diploma.qoldan.exception.donation.DonationAnnouncementNotFoundException;
import com.diploma.qoldan.model.donation.DonationAnnouncement;
import com.diploma.qoldan.repository.donation.DonationAnnouncementRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DonationAnnouncementSimpleService {

    private final DonationAnnouncementRepo repo;

    public DonationAnnouncement findById(Long id) throws DonationAnnouncementNotFoundException {
        DonationAnnouncement announcement = repo.findById(id);
        if (announcement == null)
            throw new DonationAnnouncementNotFoundException("ID: " + id);
        return announcement;
    }

    public void addDonationsToAnnouncements(DonationAnnouncement announcement, Integer quantity) {
        announcement.setQuantityCollected(announcement.getQuantityCollected() + quantity);
        repo.save(announcement);
    }
}
