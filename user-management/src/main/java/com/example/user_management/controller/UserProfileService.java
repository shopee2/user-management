package com.example.user_management.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.user_management.App;
import com.example.user_management.bean.UserProfile;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.Gson;

@RestController
public class UserProfileService {
	
	@RequestMapping(value = "/profile/all", method = RequestMethod.GET)
	public ResponseEntity<String> getAllUserProfile() throws InterruptedException, ExecutionException {

		CollectionReference ref = App.db.collection("userProfile");
		ApiFuture<QuerySnapshot> querySnapshot = ref.get();
		
		List<UserProfile> arr = new ArrayList<UserProfile>();

		for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
			UserProfile profile = new UserProfile();
			profile.setUid(document.get("uid").toString());
			profile.setFirstName(document.get("firstName").toString());
			profile.setLastName(document.get("lastName").toString());
			profile.setAddress(document.get("address").toString());
			profile.setAge(document.get("age").toString());
			profile.setGender(document.get("gender").toString());
			profile.setPhoneNumber(document.get("phoneNumber").toString());
			
			arr.add(profile);
			
		}

		return new ResponseEntity<String>(new Gson().toJson(arr), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/profile/{uid}", method = RequestMethod.GET)
	public ResponseEntity<String> getUserProfile(@PathVariable String uid) throws InterruptedException, ExecutionException {

		CollectionReference ref = App.db.collection("userProfile");
		Query query = ref.whereEqualTo("uid", uid);
		ApiFuture<QuerySnapshot> querySnapshot = query.get();

		for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
			UserProfile profile = new UserProfile();
			profile.setUid(document.get("uid").toString());
			profile.setFirstName(document.get("firstName").toString());
			profile.setLastName(document.get("lastName").toString());
			profile.setAddress(document.get("address").toString());
			profile.setAge(document.get("age").toString());
			profile.setGender(document.get("gender").toString());
			profile.setPhoneNumber(document.get("phoneNumber").toString());
			
			return new ResponseEntity<String>(new Gson().toJson(profile), HttpStatus.OK);
		}

		return new ResponseEntity<String>(new Gson().toJson("userProfile not found"), HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/profile/{uid}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUserProfile(@PathVariable String uid) throws InterruptedException, ExecutionException {
		
		CollectionReference ref = App.db.collection("userProfile");
		Query query = ref.whereEqualTo("uid", uid);
		ApiFuture<QuerySnapshot> querySnapshot = query.get();

		for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
			App.db.collection("userProfile").document(document.getId()).delete();
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		
		
		
		return new ResponseEntity<String>(new Gson().toJson("userProfile not found"), HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/profile/create", method = RequestMethod.POST)
	public ResponseEntity<String> createUserProfile(@Valid @RequestBody UserProfile userProfile,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			List<String> errors = new ArrayList<String>();

			for (ObjectError error : bindingResult.getAllErrors()) {
				errors.add(error.getDefaultMessage());
			}

			return new ResponseEntity<String>(new Gson().toJson(errors), HttpStatus.BAD_REQUEST);
		}

		CollectionReference ref = App.db.collection("userProfile");
		ref.add(userProfile);
		return new ResponseEntity<String>(new Gson().toJson(userProfile), HttpStatus.CREATED);
	}

	
	@RequestMapping(value = "/profile/edit", method = RequestMethod.PATCH)
	public ResponseEntity<String> editUserProfile(@Valid @RequestBody UserProfile userProfile,
			BindingResult bindingResult) throws InterruptedException, ExecutionException {
		
		if (bindingResult.hasErrors()) {
			List<String> errors = new ArrayList<String>();

			for (ObjectError error : bindingResult.getAllErrors()) {
				errors.add(error.getDefaultMessage());
			}

			return new ResponseEntity<String>(new Gson().toJson(errors), HttpStatus.BAD_REQUEST);
		}
		
		CollectionReference ref = App.db.collection("userProfile");
		Query query = ref.whereEqualTo("uid", userProfile.getUid());
		ApiFuture<QuerySnapshot> querySnapshot = query.get();
		
		if (querySnapshot.get().getDocuments().size() == 1) {
			
			ref.document(querySnapshot.get().getDocuments().get(0).getId()).set(userProfile);
			return new ResponseEntity<String>(new Gson().toJson(userProfile), HttpStatus.OK);
		}


		return new ResponseEntity<String>(new Gson().toJson("userProfile not found"), HttpStatus.NOT_FOUND);
	}

}
