package com.example.user_management;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;

@SpringBootApplication
@EnableDiscoveryClient
public class App {
	
	public static Firestore db;
	//public static Storage storage;
	
    public static void main( String[] args ) {
    	
    	try {
			initDB();
		} catch (IOException error) {
			System.out.println(error);
		}
    	
        SpringApplication.run(App.class, args);
    }
    
    @SuppressWarnings("deprecation")
	public static void initDB() throws IOException {
		ClassLoader classLoader = App.class.getClassLoader();
		File configFile = new File(classLoader.getResource("firebase-adminsdk.json").getFile());
		InputStream serviceAccount = new FileInputStream(configFile);

		GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
		FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(credentials).setStorageBucket("sop-user-management.appspot.com").build();

		FirebaseApp.initializeApp(options);
		db = FirestoreClient.getFirestore();
		//storage = StorageClient.getInstance().bucket().getStorage();
	}
}
