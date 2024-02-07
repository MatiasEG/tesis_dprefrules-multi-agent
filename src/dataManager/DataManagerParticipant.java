package dataManager;

import java.util.ArrayList;
import java.util.List;

import participant.Participant;

public class DataManagerParticipant {

	protected List<Participant> participants;
	protected List<Priority> participantsPriority;
	protected List<Priority> participantsPriorityTransitive;
	
	protected DataManager data;
	
	public DataManagerParticipant(DataManager data) {
		this.data = data;
		
		participants = new ArrayList<Participant>();
		participantsPriority = new ArrayList<Priority>();
		participantsPriorityTransitive = new ArrayList<Priority>();
	}
	
	// add --------------------------------------------------------------------------------------------------------------
	public void addParticipant(Participant newParticipant) {
		participants.add(newParticipant);
	}
	
	public void addParticipantsPriority(Priority newParticipantsPriority) {
		participantsPriority.add(newParticipantsPriority);
	}
	
	public void addParticipantsPriorityTransitive(Priority newParticipantsPriority) {
		participantsPriorityTransitive.add(newParticipantsPriority);
	}
	
	// set --------------------------------------------------------------------------------------------------------------
	public void setParticipantsPriorityTransitive(List<Priority> participantsPriorityTransitive){
		this.participantsPriorityTransitive = participantsPriorityTransitive;
	}
	
	public void setParticipants(List<Participant> participants) {
		this.participants = participants;
	}
	
	public void setParticipantsPriority(List<Priority> participantsPriority) {
		this.participantsPriority = participantsPriority;
	}
	
	// get --------------------------------------------------------------------------------------------------------------
	public Participant getParticipantByName(String name) {
		for(Participant agent : participants) {
			if(agent.getName().equals(name)) {
				return agent;
			}
		}
		return null;
	}
	
	public List<Participant> getParticipants(){
		return participants;
	}
	
	public List<String> getParticipantsNames() {
		List<String> names = new ArrayList<String>();
		for(Participant participant : participants) {
			names.add(participant.getName());
		}
		return names;
	}
	
	public String[] getParticipantsArrayString() {
		String[] arrayParticipants = new String[participants.size()];
		for(int i=0; i<participants.size(); i++) {
			arrayParticipants[i] = participants.get(i).getName();
		}
		return arrayParticipants;
	}
	
	public List<Priority> getParticipantsPriority(){
		return participantsPriority;
	}
	
	public List<Priority> getParticipantsPriorityTransitive(){
		return participantsPriorityTransitive;
	}
	
	// delete --------------------------------------------------------------------------------------------------------------
	public void removeParticipant(String participant) {
		for(int i=0; i<participants.size(); i++) {
			if(participants.get(i).getName().equals(participant)) {
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
		checkParticipantsPriorityTransitivity();
	}
	
	public void removeParticipantPreferencesByCriteria(String criteriaName) {
		for(Participant participant : participants) {
			for(int i=participant.getPreferences().size()-1; i>-1; i--) {
				if(participant.getPreferences().get(i).getMorePriority().equals(criteriaName) || participant.getPreferences().get(i).getLessPriority().equals(criteriaName)) {
					participant.getPreferences().remove(i);
				}
			}
		}
	}
	
	// check --------------------------------------------------------------------------------------------------------------
	public boolean validParticipantName(String name) {
		name = name.toLowerCase();
		if(DataManager.validateStringWithOnlyLetters(name)!=null) return false;
		if ((name==null) || name.trim().isEmpty()) return false;
		for(Participant participant : participants) {
			if(participant.getName().equals(name)) return false;
		}
		return true;
	}
	
	public void checkParticipantsPriorityTransitivity() {
		participantsPriorityTransitive = new ArrayList<Priority>();
		List<Relation> relations = new ArrayList<>();
		for(Priority pprior: participantsPriority) {
			relations.add(new Relation(pprior.getMorePriority(), pprior.getLessPriority()));
		}
		List<Relation> transitiveRelations = TransitivityCheck.getTransitivityRelations(relations);
		for(Relation r: transitiveRelations) {
			participantsPriorityTransitive.add(new Priority(r.getFirstString(), r.getSecondString()));
		}
	}
	
	// update --------------------------------------------------------------------------------------------------------------
	public void updateData(DataManager newData) {
		this.data = newData;
		
		participants = new ArrayList<Participant>();
		participantsPriority = new ArrayList<Priority>();
		participantsPriorityTransitive = new ArrayList<Priority>();
		
		for(Participant p : newData.getDataManagerParticipant().getParticipants()) {
			participants.add(p.clone());
		}
		for(Priority p : newData.getDataManagerParticipant().getParticipantsPriority()) {
			participantsPriority.add(p);
		}
		for(Priority p : newData.getDataManagerParticipant().getParticipantsPriorityTransitive()) {
			participantsPriorityTransitive.add(p);
		}
	}
}
