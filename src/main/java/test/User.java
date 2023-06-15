package test;

import java.sql.Date;

public class User {
	private String username;
	private String password;
	private String email;
	private Date date;

	public User() {
		//default constructor
	}

	public User(String username, String password, String email, Date date) {

		this.username = username;
		this.password = password;
		this.email = email;
		this.date = date;

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
