package com.example.user_management.bean;

import javax.validation.constraints.NotNull;

public class UserProfile {
	
	@NotNull(message = "uid should not be null")
	private String uid;
	
	@NotNull(message = "firstName should not be null")
	private String firstName;
	
	@NotNull(message = "lastName should not be null")
	private String lastName;
	
	@NotNull(message = "address should not be null")
	private String address;
	
	@NotNull(message = "phoneNumber should not be null")
	private String phoneNumber;
	
	@NotNull(message = "gender should not be null")
	private String gender;
	
	@NotNull(message = "age should not be null")
	private String age;

	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
}
