package com.example.scciapp.Models;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Map;

public class WeeklySession {
	private Long sessionID;
	private String sessionDate;
	private String sessionType;
	private String sessionWorkshop;
	
	public WeeklySession(){
		
	}
	
	public WeeklySession(Long sessionID, String sessionDate, String sessionType, String sessionWorkshop) {
		this.sessionID = sessionID;
		this.sessionDate = sessionDate;
		this.sessionType = sessionType;
		this.sessionWorkshop = sessionWorkshop;
	}
	
	public WeeklySession(@NonNull Map<String, Object> weeklySessionMap) {
		this.sessionID = (Long) weeklySessionMap.get("sessionID");
		this.sessionDate = (String) weeklySessionMap.get("sessionDate");
		this.sessionType = (String) weeklySessionMap.get("sessionType");
		this.sessionWorkshop = (String) weeklySessionMap.get("sessionWorkshop");
	}
	
	@NonNull
	public static ArrayList<WeeklySession> getWeeklySessionsList(@NonNull ArrayList<Map<String, Object>> array){
		ArrayList<WeeklySession> sessions = new ArrayList<>();
		for (int i = 0; i < array.size(); i++){
			sessions.add(new WeeklySession(array.get(i)));
		}
		return sessions;
	}
	
	public Long getSessionID() {
		return sessionID;
	}
	
	public void setSessionID(Long sessionID) {
		this.sessionID = sessionID;
	}
	
	public String getSessionDate() {
		return sessionDate;
	}
	
	public void setSessionDate(String sessionDate) {
		this.sessionDate = sessionDate;
	}
	
	public String getSessionType() {
		return sessionType;
	}
	
	public void setSessionType(String sessionType) {
		this.sessionType = sessionType;
	}
	
	public String getSessionWorkshop() {
		return sessionWorkshop;
	}
	
	public void setSessionWorkshop(String sessionWorkshop) {
		this.sessionWorkshop = sessionWorkshop;
	}
	
	@Override
	public String toString() {
		return "WeeklySession{" +
			"sessionID=" + sessionID +
			", sessionDate='" + sessionDate + '\'' +
			", sessionType='" + sessionType + '\'' +
			", sessionWorkshop='" + sessionWorkshop + '\'' +
			'}';
	}
}
