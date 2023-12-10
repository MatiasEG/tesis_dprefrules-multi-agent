package dataManager;

import java.util.ArrayList;
import java.util.List;

import criteria.Criteria;
import evidence.Alternative;
import evidence.ParticipantsPriority;

public class DataManager {

	protected List<Criteria> criterias;
	protected List<String> participants;
	protected List<ParticipantsPriority> participantsPriority;
	protected List<Alternative> alternatives;
	
	public DataManager() {
		criterias = new ArrayList<Criteria>();
		participants = new ArrayList<String>();
		participantsPriority = new ArrayList<ParticipantsPriority>();
		alternatives = new ArrayList<Alternative>();
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
	
	public String[] getParticipantsArrayString() {
		String[] arrayParticipants = new String[participants.size()];
		for(int i=0; i<participants.size(); i++) {
			arrayParticipants[i] = participants.get(i);
		}
		return arrayParticipants;
	}
	
	public void removeParticipant(String participant) {
		for(int i=0; i<participants.size(); i++) {
			if(participants.get(i).equals(participant)) {
				participants.remove(i);
				break;
			}
		}
		
		System.out.println("Agente a eliminar: "+participant);
		for(int i=participantsPriority.size()-1; i>-1; i--) {
			if(participantsPriority.get(i).getMorePriority().equals(participant) || participantsPriority.get(i).getLessPriority().equals(participant)) {
				participantsPriority.remove(i);
			}
		}
	}
	
	public void addParticipantsPriority(ParticipantsPriority newParticipantsPriority) {
		participantsPriority.add(newParticipantsPriority);
	}
	
	public List<ParticipantsPriority> getParticipantsPriority(){
		return participantsPriority;
	}
	
	
	public void updateData(DataManager newData) {
		criterias = new ArrayList<Criteria>();
		participants = new ArrayList<String>();
		participantsPriority = new ArrayList<ParticipantsPriority>();
		
		criterias = newData.getCriterias();
		participants = newData.getParticipants();
		participantsPriority = newData.getParticipantsPriority();
	}
	
	public void addAlternative(Alternative newAlternative) {
		alternatives.add(newAlternative);
	}
	
	public List<Alternative> getAlternatives(){
		return alternatives;
	}
	
	public List<String> getAlternativesNames(){
		List<String> names = new ArrayList<String>();
		for(Alternative alt: alternatives) {
			names.add(alt.getName());
		}
		return names;
	}
	
	public void removeAlternative(String alternative) {
		for(int i=0; i<alternatives.size(); i++) {
			if(alternatives.get(i).getName().equals(alternative)) {
				alternatives.remove(i);
				break;
			}
		}
	}
}
