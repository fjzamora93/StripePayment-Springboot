package org.stopmultas.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initializeFirebase() {
        try {
            InputStream serviceAccount = getClass()
                    .getClassLoader()
                    .getResourceAsStream("firebase/stopmultas-63d55-firebase-adminsdk-fbsvc-e518070907.json");

            if (serviceAccount == null) {
                throw new IllegalStateException("No se encontró el archivo firebase/stopmultas-63d55-firebase-adminsdk-fbsvc-e518070907.json");
            }

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println("✅ Firebase inicializado");
            }
        } catch (IOException e) {
            throw new RuntimeException("❌ Error al inicializar Firebase", e);
        }
    }
}
