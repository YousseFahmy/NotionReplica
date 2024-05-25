package com.notionreplica.notesApp.config;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

@Configuration
@PropertySource("classpath:application.yml")
public class FireBaseConfig {

    @Value("${firebase.service.account.path}")
    private String serviceAccountPath;

    @Value("${firebase.storage.bucket}")
    private String storageBucket;

    @Bean
    public Storage initializeFirebaseApp() throws IOException {
        System.out.println(serviceAccountPath);
        System.out.println(storageBucket);
        FileInputStream serviceAccount = new FileInputStream(serviceAccountPath);

        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .setStorageBucket(storageBucket)
                .build();

        FirebaseApp x = FirebaseApp.initializeApp(options);
        Storage storage = StorageOptions.newBuilder()
                .setCredentials(credentials)
                .build()
                .getService();
        return storage;
    }
}
