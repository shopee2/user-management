package com.example.user_management.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.example.user_management.bean.DateOfBirth;
import com.example.user_management.bean.UserProfile;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.gson.Gson;

@RestController
public class UserProfileService {

	private CollectionReference ref;

	public UserProfileService() {
		this.ref = App.db.collection("userProfile");
	}

	@RequestMapping(value = "/profile/all", method = RequestMethod.GET)
	public ResponseEntity<String> getAllUserProfile() {

		ApiFuture<QuerySnapshot> querySnapshot = this.ref.get();

		List<UserProfile> arr = new ArrayList<UserProfile>();

		List<QueryDocumentSnapshot> documents;
		try {
			documents = querySnapshot.get().getDocuments();
		} catch (Exception error) {
			return new ResponseEntity<String>(error.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (documents.size() == 1) {
			DocumentSnapshot document = documents.get(0);
			String uid = document.get("uid").toString();
			String firstName = document.get("firstName").toString();
			String lastName = document.get("lastName").toString();
			String address = document.get("address").toString();
			String phoneNumber = document.get("phoneNumber").toString();
			String gender = document.get("gender").toString();
			DateOfBirth dateOfBirth = new Gson().fromJson(document.get("dateOfBirth").toString(), DateOfBirth.class);

			UserProfile profile = new UserProfile(uid, firstName, lastName, address, phoneNumber, gender, dateOfBirth);

			arr.add(profile);

		}

		return new ResponseEntity<String>(new Gson().toJson(arr), HttpStatus.OK);
	}

	@RequestMapping(value = "/profile/{uid}", method = RequestMethod.GET)
	public ResponseEntity<String> getUserProfile(@PathVariable String uid) {

		Query query = this.ref.whereEqualTo("uid", uid);
		ApiFuture<QuerySnapshot> querySnapshot = query.get();

		List<QueryDocumentSnapshot> documents;
		try {
			documents = querySnapshot.get().getDocuments();
		} catch (Exception error) {
			return new ResponseEntity<String>(error.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (documents.size() == 1) {
			DocumentSnapshot document = documents.get(0);
			String firstName = document.get("firstName").toString();
			String lastName = document.get("lastName").toString();
			String address = document.get("address").toString();
			String phoneNumber = document.get("phoneNumber").toString();
			String gender = document.get("gender").toString();
			DateOfBirth dateOfBirth = new Gson().fromJson(document.get("dateOfBirth").toString(), DateOfBirth.class);

			UserProfile profile = new UserProfile(uid, firstName, lastName, address, phoneNumber, gender, dateOfBirth);

			return new ResponseEntity<String>(new Gson().toJson(profile), HttpStatus.OK);

		}

		return new ResponseEntity<String>(new Gson().toJson("can't find user profile - " + uid), HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/profile/{uid}", method = RequestMethod.DELETE)
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
			App.db.collection("userProfile").document(documents.get(0).getId()).delete();
			return new ResponseEntity<String>(HttpStatus.OK);

		}

		return new ResponseEntity<String>(new Gson().toJson("can't find user profile - " + uid), HttpStatus.NOT_FOUND);
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

		this.ref.add(userProfile);
		return new ResponseEntity<String>(new Gson().toJson(userProfile), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/profile/edit", method = RequestMethod.PATCH)
	public ResponseEntity<String> editUserProfile(@Valid @RequestBody UserProfile userProfile,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			List<String> errors = new ArrayList<String>();

			for (ObjectError error : bindingResult.getAllErrors()) {
				errors.add(error.getDefaultMessage());
			}

			return new ResponseEntity<String>(new Gson().toJson(errors), HttpStatus.BAD_REQUEST);
		}
		
		String uid = userProfile.getUid();
		Query query = this.ref.whereEqualTo("uid", uid);
		ApiFuture<QuerySnapshot> querySnapshot = query.get();

		List<QueryDocumentSnapshot> documents;
		try {
			documents = querySnapshot.get().getDocuments();
		} catch (Exception error) {
			return new ResponseEntity<String>(error.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (documents.size() == 1) {

			this.ref.document(documents.get(0).getId()).set(userProfile);
			return new ResponseEntity<String>(new Gson().toJson(userProfile), HttpStatus.OK);
		}

		return new ResponseEntity<String>(new Gson().toJson("can't find user profile - " + uid), HttpStatus.NOT_FOUND);
	}

}
