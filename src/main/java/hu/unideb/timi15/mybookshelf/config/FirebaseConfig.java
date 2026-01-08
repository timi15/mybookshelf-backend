package hu.unideb.timi15.mybookshelf.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class FirebaseConfig {

//    @Value("${firebase.config.file}")
//    private Resource serviceAccount;

    @PostConstruct
    public void initializeFirebase() throws IOException {

        String firebaseCredentialsJson = System.getenv("FIREBASE_CREDENTIALS_JSON");

        InputStream credentialsStream = new ByteArrayInputStream(
                firebaseCredentialsJson.getBytes(StandardCharsets.UTF_8)
        );

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(credentialsStream))
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }

        log.info("Firebase config initialized");
    }

}
