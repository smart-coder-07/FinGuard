package com.debt.dto;

public class User {
	 private int userId;
	    public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setUsername(String username) {
		this.username = username;
	}

		private String username;
	    private String email;
	    private String role;
	    
	    
		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

		public String getUsername() {
			return username;
		}

		public void setName(String name) {
			this.username = name;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}


}
