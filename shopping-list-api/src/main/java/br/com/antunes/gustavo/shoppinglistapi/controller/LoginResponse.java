package br.com.antunes.gustavo.shoppinglistapi.controller;

import lombok.Builder;

@Builder
public class LoginResponse {

	private String accesToken;

	public LoginResponse(String accesToken) {
		super();
		this.accesToken = accesToken;
	}

	public void setAccesToken(String accesToken) {
		this.accesToken = accesToken;
	}

	public String getAccesToken() {
		return accesToken;
	}

}
