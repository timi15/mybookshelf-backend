package hu.unideb.timi15.mybookshelf.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration
@Slf4j
public class FirebaseConfig {

    @Bean
    public FirebaseApp createFireBaseApp() throws IOException {

        String firebaseCredentialsJson = System.getenv("FIREBASE_CREDENTIALS_JSON");

        if (firebaseCredentialsJson == null || firebaseCredentialsJson.trim().isEmpty()) {
            String base64Credentials = System.getenv("FIREBASE_CREDENTIALS_BASE64");
            if (base64Credentials != null && !base64Credentials.trim().isEmpty()) {
                byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
                firebaseCredentialsJson = new String(decodedBytes, StandardCharsets.UTF_8);
            } else {
                throw new IOException("Firebase credentials not found in environment variables. ");
            }
        }

        InputStream credentialsStream = new ByteArrayInputStream(
                firebaseCredentialsJson.getBytes(StandardCharsets.UTF_8)
        );

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(credentialsStream))
                .build();

        log.info("Firebase config initialized from environment variables");

        return FirebaseApp.initializeApp(options);
    }

    @Bean
    @DependsOn(value = "createFireBaseApp")
    public Firestore createFirestoreClient() {
        return FirestoreClient.getFirestore();
    }

    @Bean
    @DependsOn(value = "createFireBaseApp")
    public StorageClient createFirebaseStorage() {
        return StorageClient.getInstance();
    }

    @Bean
    @DependsOn(value = "createFireBaseApp")
    public FirebaseAuth createFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Bean
    @DependsOn(value = "createFireBaseApp")
    public FirebaseMessaging createFirebaseMessaging() {
        return FirebaseMessaging.getInstance();
    }

}
