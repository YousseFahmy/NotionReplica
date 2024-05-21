package com.notionreplica.notesApp.services;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.firebase.FirebaseApp;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FireBaseStorageService {
    @Autowired
    private final Storage storage;
    private List<String> ALLOWED_MIME_TYPES = Arrays.asList(
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_GIF_VALUE,
            "image/svg+xml"
    );
    private List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpeg", "jpg", "png", "gif", "svg");
    @Value("${firebase.storage.bucket}")
    private String storageBucket;
    @Value("${firebase.service.account.path}")
    private String serviceAccountPath;

    public String uploadFile(MultipartFile file) throws IOException {
        validateFile(file);

        String fileName = (UUID.randomUUID().toString() + "-" + file.getOriginalFilename().replace('-','_'));
        BlobInfo blobInfo = BlobInfo.newBuilder(storageBucket,fileName).build();

        storage.create(blobInfo, file.getBytes());
        return "https://storage.googleapis.com/"+storageBucket+"/" + fileName;
    }
    public byte[] getFile(String fileName) throws IOException {
        BlobId blobId = BlobId.of(storageBucket, fileName);
        System.out.println(blobId.toString());
        Blob blob = storage.get(blobId);
        if (blob == null || !blob.exists()) {
            throw new IOException("File not found: " + fileName);
        }
        return blob.getContent();
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

