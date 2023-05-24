package com.diploma.qoldan.service.image;

import com.diploma.qoldan.exception.image.ImageNotFoundException;
import com.diploma.qoldan.model.image.Image;
import com.diploma.qoldan.repository.image.FileSystemRepository;
import com.diploma.qoldan.repository.image.ImageRepo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepo repo;
    private final FileSystemRepository fileSystemRepo;

    public Long save(MultipartFile file) throws IOException {
        String location = fileSystemRepo.save(file.getBytes(), file.getOriginalFilename());

        Image image = Image.builder()
                .name(file.getOriginalFilename())
                .location(location)
                .build();
        return repo.save(image).getId();
    }

    public FileSystemResource find(Long imageId) throws ImageNotFoundException {
        Image image = findById(imageId);
        return fileSystemRepo.findInFileSystem(image.getLocation());
    }

    public Image findById(Long imageId) throws ImageNotFoundException {
        Image image = repo.findById(imageId);
        if (image == null)
            throw new ImageNotFoundException("");
        return image;
    }
}
