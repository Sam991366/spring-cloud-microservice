package com.commonlib.request;

import java.net.Authenticator.RequestorType;

public class ComplainDto {

	private String complainId;
	private String description;
	public String getComplainId() {
		return complainId;
	}
	public void setComplainId(String complainId) {
		this.complainId = complainId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
