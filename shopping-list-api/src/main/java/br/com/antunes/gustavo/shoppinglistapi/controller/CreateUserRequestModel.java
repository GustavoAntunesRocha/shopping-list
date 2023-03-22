package br.com.antunes.gustavo.shoppinglistapi.controller;

import br.com.antunes.gustavo.shoppinglistapi.entity.Role;

public class CreateUserRequestModel {

	private String email;
	
	private Role role;

	private String password;

	public CreateUserRequestModel() {}

	public CreateUserRequestModel(String email, Role role, String password) {
		super();
		this.email = email;
		this.role = role;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
