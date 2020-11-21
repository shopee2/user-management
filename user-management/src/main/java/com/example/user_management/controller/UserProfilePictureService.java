package com.example.user_management.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.user_management.App;
import com.example.user_management.bean.UserProfilePicture;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.storage.Blob;
import com.google.gson.Gson;

@RestController
public class UserProfilePictureService {

	private CollectionReference ref;
	private String storageURL = "https://storage.googleapis.com/sop-user-management.appspot.com/";

	public UserProfilePictureService() {
		this.ref = App.db.collection("userProfile");
	}

	@RequestMapping(value = "/profile/picture/{uid}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createOrEditUserProfilePicture(@PathVariable String uid,
			@RequestParam("file") MultipartFile file) {

		Query query = this.ref.whereEqualTo("uid", uid);
		ApiFuture<QuerySnapshot> querySnapshot = query.get();

		List<QueryDocumentSnapshot> documents;
		try {
			documents = querySnapshot.get().getDocuments();
		} catch (Exception error) {
			return new ResponseEntity<String>(error.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (documents.size() == 1) {

			try {
				App.storage.create(uid, file.getBytes(), file.getContentType());
			} catch (Exception error) {
				return new ResponseEntity<String>(error.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
			}

			UserProfilePicture profilePicture = new UserProfilePicture(uid, this.storageURL + uid);
			return new ResponseEntity<String>(new Gson().toJson(profilePicture), HttpStatus.CREATED);
		}

		return new ResponseEntity<String>(new Gson().toJson("can't find user profile - " + uid), HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/profile/picture/{uid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getUserProfilePicture(@PathVariable String uid) {

		Query query = this.ref.whereEqualTo("uid", uid);
		ApiFuture<QuerySnapshot> querySnapshot = query.get();

		List<QueryDocumentSnapshot> documents;
		try {
			documents = querySnapshot.get().getDocuments();
		} catch (Exception error) {
			return new ResponseEntity<String>(error.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (documents.size() == 1) {
			
			Blob picture = App.storage.get(uid);

			if (picture == null) {
				return new ResponseEntity<String>(new Gson().toJson("This user(" + uid + ") doesn't have a profile picture."),
						HttpStatus.NOT_FOUND);
			}
			
			UserProfilePicture profilePicture = new UserProfilePicture(uid, this.storageURL + uid);
			return new ResponseEntity<String>(new Gson().toJson(profilePicture), HttpStatus.OK);
		}

		return new ResponseEntity<String>(new Gson().toJson("can't find user profile - " + uid), HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/profile/picture/{uid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteUserProfile(@PathVariable String uid) {

		Query query = this.ref.whereEqualTo("uid", uid);
		ApiFuture<QuerySnapshot> querySnapshot = query.get();

		List<QueryDocumentSnapshot> documents;
		try {
			documents = querySnapshot.get().getDocuments();
		} catch (Exception error) {
			return new ResponseEntity<String>(error.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (documents.size() == 1) {

			Blob picture = App.storage.get(uid);

			if (picture == null) {
				return new ResponseEntity<String>(new Gson().toJson("This user(" + uid + ") doesn't have a profile picture."),
						HttpStatus.NOT_FOUND);
			}

			App.storage.getStorage().delete(picture.getBlobId());

			return new ResponseEntity<String>(new Gson().toJson("successful operation"), HttpStatus.OK);
		}

		return new ResponseEntity<String>(new Gson().toJson("can't find user profile - " + uid), HttpStatus.NOT_FOUND);
	}
}
