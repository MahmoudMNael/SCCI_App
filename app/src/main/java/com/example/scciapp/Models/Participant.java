package com.example.scciapp.Models;

import java.util.ArrayList;
import java.util.Map;

public class Participant extends User{
	private Boolean isAttended;
	
	public Participant(Boolean isAttended) {
		this.isAttended = isAttended;
	}
	
	public Participant(Long userID, String userFullName, String userEmail, String userType, String userWorkshop, Boolean isAttended) {
		super(userID, userFullName, userEmail, userType, userWorkshop);
		if (isAttended == null){
			this.isAttended = false;
		} else {
			this.isAttended = isAttended;
		}
	}
	
	public Participant(Map<String, Object> participantMap) {
		super((Long) participantMap.get("userID"), (String) participantMap.get("userFullName"), (String) participantMap.get("userEmail"), (String) participantMap.get("userType"), (String) participantMap.get("userWorkshop"));
		if (participantMap.get("attended") == null){
			this.isAttended = false;
		} else {
			this.isAttended = (Long) participantMap.get("attended") == 1;
		}
	}
	
	public static ArrayList<Participant> getParticipantsList(ArrayList<Map<String, Object>> array){
		ArrayList<Participant> participants = new ArrayList<>();
		for (int i = 0; i < array.size(); i++){
			participants.add(new Participant(array.get(i)));
		}
		return participants;
	}
	
	public Boolean getAttended() {
		return isAttended;
	}
	
	@Override
	public String toString() {
		return "Participant{" +
			"isAttended=" + isAttended +
			"} " + super.toString();
	}
	
	public void setAttended(Boolean attended) {
		isAttended = attended;
	}
}
