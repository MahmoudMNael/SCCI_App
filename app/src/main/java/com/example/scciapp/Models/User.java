package com.example.scciapp.Models;

import java.util.ArrayList;
import java.util.Map;

public class User {
	private Long userID;
	private String userFullName;
	private String userEmail;
	private String userType;
	private String userWorkshop;
	
	public Long getUserID() {
		return userID;
	}
	
	public User() {
	}
	
	public User(Long userID, String userFullName, String userEmail, String userType, String userWorkshop) {
		this.userID = userID;
		this.userFullName = userFullName;
		this.userEmail = userEmail;
		this.userType = userType;
		this.userWorkshop = userWorkshop;
	}
	
	public User(Map<String, Object> userMap) {
		this.userID = (Long) userMap.get("userID");
		this.userFullName = (String) userMap.get("userFullName");
		this.userEmail = (String) userMap.get("userEmail");
		this.userType = (String) userMap.get("userType");
		this.userWorkshop = (String) userMap.get("userWorkshop");
	}
	
	public static ArrayList<User> getUsersList(ArrayList<Map<String, Object>> array){
		ArrayList<User> users = new ArrayList<>();
		for (int i = 0; i < array.size(); i++){
			users.add(new User(array.get(i)));
		}
		return users;
	}
	
	public void setUserID(Long userID) {
		this.userID = userID;
	}
	
	public String getUserFullName() {
		return userFullName;
	}
	
	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}
	
	public String getUserEmail() {
		return userEmail;
	}
	
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	public String getUserType() {
		return userType;
	}
	
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public String getUserWorkshop() {
		return userWorkshop;
	}
	
	public void setUserWorkshop(String userWorkshop) {
		this.userWorkshop = userWorkshop;
	}
	
	@Override
	public String toString() {
		return "User{" +
				"userID=" + userID +
				", userFullName='" + userFullName + '\'' +
				", userEmail='" + userEmail + '\'' +
				", userType='" + userType + '\'' +
				", userWorkshop='" + userWorkshop + '\'' +
				'}';
	}
}
