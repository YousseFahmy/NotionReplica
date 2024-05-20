package com.notionreplica.notesApp.services;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class FireBaseStorageService {

    private final Storage storage;
    private static final List<String> ALLOWED_MIME_TYPES = Arrays.asList(
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_GIF_VALUE,
            "image/svg+xml"
    );
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpeg", "jpg", "png", "gif", "svg");
    @Value("${firebase.storage.bucket}")
    private String storageBucket;
    public FireBaseStorageService() {
        this.storage = StorageOptions.getDefaultInstance().getService();
    }
    public String uploadFile(MultipartFile file) throws IOException {
        validateFile(file);

        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        BlobInfo blobInfo = BlobInfo.newBuilder(storageBucket, fileName).build();
        storage.create(blobInfo, file.getBytes());
        return "https://storage.googleapis.com/"+storageBucket+"/" + fileName;
    }
    private void validateFile(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_MIME_TYPES.contains(contentType)) {
            throw new UnsupportedMediaTypeStatusException("File type not supported. Allowed types: " + ALLOWED_MIME_TYPES);
        }

        String extension = getFileExtension(file.getOriginalFilename());
        if (extension == null || !ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {
            throw new UnsupportedMediaTypeStatusException("File extension not supported. Allowed extensions: " + ALLOWED_EXTENSIONS);
        }
    }
    private String getFileExtension(String fileName) {
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf('.') + 1);
        }
        return null;
    }
}

