package com.example.user_management;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;

@SuppressWarnings("deprecation")
@SpringBootApplication
@EnableDiscoveryClient
public class App {

	public static Firestore db;
	public static Bucket storage;

	public static void main(String[] args) {
		try {
			initDB();
			SpringApplication.run(App.class, args);
		} catch (IOException error) {
			System.out.println(error);
		}
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE");
			}
		};
	}

	public static void initDB() throws IOException {

		ClassPathResource resource = new ClassPathResource("firebase-adminsdk.json");
		InputStream serviceAccount = resource.getInputStream();
		GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
		FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(credentials)
				.setStorageBucket("sop-user-management.appspot.com").build();
		FirebaseApp.initializeApp(options);
		db = FirestoreClient.getFirestore();
		storage = StorageClient.getInstance().bucket();
	}
}
