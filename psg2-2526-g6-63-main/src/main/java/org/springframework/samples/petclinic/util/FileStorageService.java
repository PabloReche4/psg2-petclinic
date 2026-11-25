package org.springframework.samples.petclinic.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.samples.petclinic.exceptions.InvalidImageException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    private final Path rootLocation;
    private final String uploadUrl;

    public FileStorageService(
        @Value("${petclinic.upload.dir:uploads}") String uploadDir,
        @Value("${petclinic.upload.url:/uploads}") String uploadUrl
    ) {
        String baseDir = StringUtils.hasText(uploadDir) ? uploadDir : "uploads";
        Path basePath = Paths.get(baseDir);
        if (!basePath.isAbsolute()) {
            basePath = Paths.get(System.getProperty("user.dir")).resolve(basePath);
        }
        this.rootLocation = basePath.normalize();
        this.uploadUrl = uploadUrl.endsWith("/") ? uploadUrl.substring(0, uploadUrl.length() - 1) : uploadUrl;
        try {
            Files.createDirectories(this.rootLocation);
        } catch (IOException ex) {
            throw new InvalidImageException("Unable to initialize upload folder.");
        }
    }

    public String store(MultipartFile file) {
        String extension = getExtension(file.getOriginalFilename(), file.getContentType());
        String filename = UUID.randomUUID().toString().replace("-", "") + extension;
        Path destination = rootLocation.resolve(filename).normalize();
        try {
            Files.copy(file.getInputStream(), destination);
        } catch (IOException ex) {
            throw new InvalidImageException("Failed to store image.");
        }
        return uploadUrl + "/" + filename;
    }

    public void deleteByUrl(String url) {
        if (url == null || !url.startsWith(uploadUrl + "/")) {
            return;
        }
        String fileName = url.substring(uploadUrl.length() + 1);
        Path target = rootLocation.resolve(fileName).normalize();
        try {
            Files.deleteIfExists(target);
        } catch (IOException ex) {
            // ignore cleanup failures
        }
    }

    public Path getRootLocation() {
        return rootLocation;
    }

    public String getUploadUrl() {
        return uploadUrl;
    }

    private String getExtension(String originalName, String contentType) {
        if (originalName != null && originalName.contains(".")) {
            return originalName.substring(originalName.lastIndexOf('.'));
        }
        if (contentType == null) {
            return ".img";
        }
        switch (contentType) {
        case "image/jpeg":
            return ".jpg";
        case "image/png":
            return ".png";
        case "image/webp":
            return ".webp";
        default:
            return ".img";
        }
    }
}
