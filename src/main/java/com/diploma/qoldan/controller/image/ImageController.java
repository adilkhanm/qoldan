package com.diploma.qoldan.controller.image;

import com.diploma.qoldan.exception.image.ImageNotFoundException;
import com.diploma.qoldan.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService service;

    @PostMapping
    public ResponseEntity<Long> uploadImage(@RequestParam(value = "image") MultipartFile image)
            throws IOException {
        Long id = service.save(image);
        return ResponseEntity.ok(id);
    }

    @GetMapping(value = "/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<FileSystemResource> downloadImage(@PathVariable Long imageId)
            throws ImageNotFoundException {
        FileSystemResource resource = service.find(imageId);
        return ResponseEntity.ok(resource);
    }
}
