package com.diploma.qoldan.repository.image;

import com.diploma.qoldan.exception.image.ImageNotFoundException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Repository
public class FileSystemRepository {

    private String RESOURCES_DIR = FileSystemRepository.class.getResource("/").getPath();

    public String save(byte[] content, String imageName) throws IOException {
        Path newFile = Paths.get(RESOURCES_DIR + new Date().getTime() + "-" + imageName);
        Files.createDirectories(newFile.getParent());

        Files.write(newFile, content);

        return newFile.toAbsolutePath().toString();
    }

    public FileSystemResource findInFileSystem(String location) throws ImageNotFoundException {
        try {
            return new FileSystemResource(Paths.get(location));
        } catch (Exception e) {
            throw new ImageNotFoundException("");
        }
    }
}
