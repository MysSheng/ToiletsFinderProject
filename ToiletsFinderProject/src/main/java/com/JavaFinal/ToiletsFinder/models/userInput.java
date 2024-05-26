package com.JavaFinal.ToiletsFinder.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;


public class userInput {
	
	@NotEmpty(message = "this is required.")
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
