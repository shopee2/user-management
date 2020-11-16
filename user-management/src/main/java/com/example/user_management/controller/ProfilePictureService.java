package com.example.user_management.controller;

import java.io.IOException;
import java.util.List;
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
import com.example.user_management.bean.ProfilePicture;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.storage.Blob;
import com.google.gson.Gson;

@RestController
public class ProfilePictureService {

	@RequestMapping(value = "/profile/picture/{uid}", method = RequestMethod.POST)
	public ResponseEntity<String> createOrEditUserProfilePicture(@PathVariable String uid,
			@RequestParam("file") MultipartFile file) throws InterruptedException, ExecutionException, IOException {

		CollectionReference ref = App.db.collection("userProfile");
		Query query = ref.whereEqualTo("uid", uid);
		ApiFuture<QuerySnapshot> querySnapshot = query.get();

		List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

		if (documents.size() == 1) {
			
			App.storage.create(uid, file.getBytes(), file.getContentType());

			ProfilePicture profilePicture = new ProfilePicture(uid,
					"https://storage.cloud.google.com/sop-user-management.appspot.com/" + uid);
			return new ResponseEntity<String>(new Gson().toJson(profilePicture), HttpStatus.CREATED);
		}

		return new ResponseEntity<String>(new Gson().toJson("userProfile not found"), HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/profile/picture/{uid}", method = RequestMethod.GET)
	public ResponseEntity<String> getUserProfilePicture(@PathVariable String uid) throws InterruptedException, ExecutionException  {

		CollectionReference ref = App.db.collection("userProfile");
		Query query = ref.whereEqualTo("uid", uid);
		ApiFuture<QuerySnapshot> querySnapshot = query.get();

		List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

		if (documents.size() == 1) {
			
			Blob picture = App.storage.get(uid);
			
			System.out.println(picture);
			
		
			
			/*if (picture.equals(null)) {
				return new ResponseEntity<String>(new Gson().toJson("The user does not have a profile picture."), HttpStatus.NOT_FOUND);
			}*/
			
			
			ProfilePicture profilePicture = new ProfilePicture(uid,
					"https://storage.cloud.google.com/sop-user-management.appspot.com/" + uid);
			return new ResponseEntity<String>(new Gson().toJson(profilePicture), HttpStatus.OK);
		}

		return new ResponseEntity<String>(new Gson().toJson("userProfile not found"), HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/profile/picture/{uid}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUserProfile(@PathVariable String uid) throws InterruptedException, ExecutionException {

		CollectionReference ref = App.db.collection("userProfile");
		Query query = ref.whereEqualTo("uid", uid);
		ApiFuture<QuerySnapshot> querySnapshot = query.get();

		List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

		if (documents.size() == 1) {
			
			
			Blob picture = App.storage.get(uid);
			
			if (!picture.exists()) {
				return new ResponseEntity<String>(new Gson().toJson("The user does not have a profile picture."), HttpStatus.NOT_FOUND);
			}
		
			
			App.storage.getStorage().delete(picture.getBlobId());
			
			
			return new ResponseEntity<String>(new Gson().toJson("successful operation"), HttpStatus.OK);
		}

		return new ResponseEntity<String>(new Gson().toJson("userProfile not found"), HttpStatus.NOT_FOUND);
	}
	
	

}
