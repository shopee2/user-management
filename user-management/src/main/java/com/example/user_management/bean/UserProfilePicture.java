package com.example.user_management.bean;

import javax.validation.constraints.NotNull;

public class UserProfilePicture {
	@NotNull(message = "uid should not be null")
	private String uid;

	@NotNull(message = "imageUrl should not be null")
	private String imageUrl;

	public UserProfilePicture(String uid, String imageUrl) {
		this.uid = uid;
		this.imageUrl = imageUrl;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
