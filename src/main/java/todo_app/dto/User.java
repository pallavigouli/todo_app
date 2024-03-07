package todo_app.dto;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import todo_app.helper.AES;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //id will be generated by system
	 int id;
	 private String name;
	 private String email;
	 private String password;
	 private String gender;
	 private long phone;
	 private String dob;
	 
	public String getDob() {
		return dob;
	}
	public void setDob(String localDate) {
		this.dob = localDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return AES.decrypt(password, "123");
	}
	public void setPassword(String password) {
		this.password = AES.encrypt(password, "123");
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
}