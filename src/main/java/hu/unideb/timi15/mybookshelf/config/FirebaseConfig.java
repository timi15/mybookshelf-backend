//package hu.unideb.timi15.mybookshelf.config;
//
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;
//import org.springframework.core.io.Resource;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import lombok.extern.slf4j.Slf4j;
//
//import javax.annotation.PostConstruct;
//
//import java.io.IOException;
//
//@Service
//@Slf4j
//public class FirebaseConfig {
//
//    @Value("${firebase.config.file}")
//    private Resource serviceAccount;
//
//    @PostConstruct
//    public void initializeFirebase() throws IOException {
//
//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
//                .build();
//
//        if (FirebaseApp.getApps().isEmpty()) {
//            FirebaseApp.initializeApp(options);
//        }
//
//        log.info("Firebase config initialized");
//    }
//
//}