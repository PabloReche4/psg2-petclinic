package org.springframework.samples.petclinic.util;

import java.util.Set;

import org.springframework.samples.petclinic.exceptions.InvalidImageException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageValidationService {

    private static final long MAX_UPLOAD_BYTES = 2 * 1024 * 1024;
    private static final Set<String> ALLOWED_MIME_TYPES = Set.of(
        "image/jpeg",
        "image/png",
        "image/webp"
    );

    public void validateImageUrl(String url) {
        if (url == null || url.isBlank()) {
            throw new InvalidImageException("Image is required.");
        }

        if (url.startsWith("data:image/")) {
            validateDataUrl(url);
            return;
        }

        if (url.startsWith("http://") || url.startsWith("https://")) {
            if (url.length() > 2048) {
                throw new InvalidImageException("Image URL is too long.");
            }
            return;
        }

        throw new InvalidImageException("Unsupported image format.");
    }

    public void validateImageFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new InvalidImageException("Image file is required.");
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_MIME_TYPES.contains(contentType)) {
            throw new InvalidImageException("Unsupported image type: " + contentType);
        }

        if (file.getSize() > MAX_UPLOAD_BYTES) {
            throw new InvalidImageException("Image exceeds 2MB limit.");
        }
    }

    private void validateDataUrl(String url) {
        int commaIndex = url.indexOf(',');
        if (commaIndex <= 0) {
            throw new InvalidImageException("Invalid data URL format.");
        }

        String meta = url.substring(5, commaIndex);
        String[] metaParts = meta.split(";");
        if (metaParts.length < 2 || !metaParts[1].equals("base64")) {
            throw new InvalidImageException("Image must be base64 encoded.");
        }

        String mimeType = metaParts[0];
        if (!ALLOWED_MIME_TYPES.contains(mimeType)) {
            throw new InvalidImageException("Unsupported image type: " + mimeType);
        }

        String base64 = url.substring(commaIndex + 1);
        if (base64.isBlank()) {
            throw new InvalidImageException("Image data is empty.");
        }

        long size = estimateDecodedSize(base64);
        if (size > MAX_UPLOAD_BYTES) {
            throw new InvalidImageException("Image exceeds 2MB limit.");
        }
    }

    private long estimateDecodedSize(String base64) {
        int padding = 0;
        int length = base64.length();
        if (length >= 2 && base64.endsWith("==")) {
            padding = 2;
        } else if (length >= 1 && base64.endsWith("=")) {
            padding = 1;
        }
        return (long) length * 3 / 4 - padding;
    }
}
