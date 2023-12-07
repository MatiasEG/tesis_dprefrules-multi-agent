package dataManager;

import java.util.ArrayList;
import java.util.List;

import criteria.Criteria;

public class DataManager {

	private List<Criteria> criterias;
	private List<String> participants;
	
	public DataManager() {
		criterias = new ArrayList<Criteria>();
		participants = new ArrayList<String>();
	}
	
	public void addCriteria(Criteria criteria) {
		criterias.add(criteria);
	}
	
	public List<Criteria> getCriterias(){
		return criterias;
	}
	
	public Criteria getCriteria(String name) {
		for(Criteria cr: criterias) {
			if(cr.getName().equals(name)) {
				return cr;
			}
		}
		return null;
	}
	
	public void addParticipant(String newParticipant) {
		participants.add(newParticipant);
	}
	
	public List<String> getParticipants(){
		return participants;
	}
	
	public void removeParticipant(String participant) {
		for(int i=1; i<participants.size(); i++) {
			if(participants.get(i).equals(participant)) {
				participants.remove(i);
				break;
			}
		}
	}
}
