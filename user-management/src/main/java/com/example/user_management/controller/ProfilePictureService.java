package com.example.user_management.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.user_management.App;
import com.example.user_management.bean.UserProfile;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.ReadChannel;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;
import com.google.gson.Gson;
@RestController
public class ProfilePictureService {

	/*@SuppressWarnings("unused")
	@RequestMapping(value = "/profile/picture/{uid}", method = RequestMethod.POST)
	public ResponseEntity<String> getUserProfile(@PathVariable String uid, @RequestParam("file") MultipartFile  file) throws InterruptedException, ExecutionException, IOException {

		CollectionReference ref = App.db.collection("userProfile");
		Query query = ref.whereEqualTo("uid", uid);
		ApiFuture<QuerySnapshot> querySnapshot = query.get();

		for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
			
			BlobId blobId = BlobId.of("test", uid);
			BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
			App.storage.create(blobInfo, file.getBytes());
			 
            
			
			return new ResponseEntity<String>(new Gson().toJson("OKAY"), HttpStatus.OK);
		}

		return new ResponseEntity<String>(new Gson().toJson("userProfile not found"), HttpStatus.NOT_FOUND);
	}
	*/
}
