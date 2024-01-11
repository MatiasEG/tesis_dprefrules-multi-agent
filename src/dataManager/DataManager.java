package dataManager;

import java.util.ArrayList;
import java.util.List;

import agent.AgentPriority;
import alternative.Alternative;
import criteria.Criteria;

public class DataManager {

	protected List<Criteria> criterias;
	protected List<String> participants;
	protected List<AgentPriority> participantsPriority;
	protected List<AgentPriority> participantsPriorityTransitive;
	protected List<Alternative> alternatives;
	
	protected String projectName;
	protected String folderPath;
	
	public DataManager(String projectName, String folderPath) {
		this.projectName = projectName;
		this.folderPath = folderPath;
		
		criterias = new ArrayList<Criteria>();
		participants = new ArrayList<String>();
		participantsPriority = new ArrayList<AgentPriority>();
		participantsPriorityTransitive = new ArrayList<AgentPriority>();
		alternatives = new ArrayList<Alternative>();
	}
	
	public void setSaveFolder(String newSaveFolder) {
		this.folderPath = newSaveFolder;
	}
	
	public void setProjectName(String newProjectName) {
		this.projectName = newProjectName;
	}
	
	public String getSaveFolder() {
		return this.folderPath;
	}
	
	public String getProjectName() {
		return this.projectName;
	}
	
	public void addCriteria(Criteria criteria) {
		criterias.add(criteria);
	}
	
	public List<Criteria> getCriterias(){
		return criterias;
	}
	
	public void setCriterias(List<Criteria> newCriterias) {
		criterias = newCriterias;
	}
	
	public Criteria getCriteria(String name) {
		for(Criteria cr: criterias) {
			if(cr.getName().equals(name)) {
				return cr;
			}
		}
		return null;
	}
	
	public void removeCriteria(String name) {
		for(int i=0; i<criterias.size(); i++) {
			if(criterias.get(i).getName().equals(name)) {
				criterias.remove(i);
				break;
			}
		}
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
	
	public void addParticipantsPriority(AgentPriority newParticipantsPriority) {
		participantsPriority.add(newParticipantsPriority);
	}
	
	public List<AgentPriority> getParticipantsPriority(){
		return participantsPriority;
	}
	
	public void addParticipantsPriorityTransitive(AgentPriority newParticipantsPriority) {
		participantsPriorityTransitive.add(newParticipantsPriority);
	}
	
	public List<AgentPriority> getParticipantsPriorityTransitive(){
		return participantsPriorityTransitive;
	}
	
	public void checkParticipantsPriorityTransitivity() {
		participantsPriorityTransitive = new ArrayList<AgentPriority>();
		List<Relation> relations = new ArrayList<>();
		for(AgentPriority pprior: participantsPriority) {
			relations.add(new Relation(pprior.getMorePriority(), pprior.getLessPriority()));
		}
		List<Relation> transitiveRelations = TransitivityCheck.getTransitivityRelations(relations);
		for(Relation r: transitiveRelations) {
			participantsPriorityTransitive.add(new AgentPriority(r.getFirstString(), r.getSecondString()));
		}
	}
	
	public void updateData(DataManager newData) {
		criterias = new ArrayList<Criteria>();
		participants = new ArrayList<String>();
		participantsPriority = new ArrayList<AgentPriority>();
		alternatives = new ArrayList<Alternative>();

		criterias = newData.getCriterias();
		participants = newData.getParticipants();
		participantsPriority = newData.getParticipantsPriority();
		alternatives = newData.getAlternatives();
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
	
	public void setParticipants(List<String> participants) {
		this.participants = participants;
	}
	
	public void setParticipantsPriority(List<AgentPriority> participantsPriority) {
		this.participantsPriority = participantsPriority;
	}
	
	public void setAlternatives(List<Alternative> alternatives) {
		this.alternatives = alternatives;
	}
}
