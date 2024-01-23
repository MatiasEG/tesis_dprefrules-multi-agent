package dataManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import alternative.Alternative;
import criteria.Criteria;
import participant.Participant;
import prefRules.Rule;

public class DataManager {

	protected List<Criteria> criterias;
	protected List<Participant> participants;
	protected List<Priority> participantsPriority;
	protected List<Priority> participantsPriorityTransitive;
	protected List<Alternative> alternatives;
	protected List<Rule> rules;
	
	protected String projectName;
	protected String folderPath;
	
	public DataManager(String projectName, String folderPath) {
		this.projectName = projectName;
		this.folderPath = folderPath;
		
		criterias = new ArrayList<Criteria>();
		participants = new ArrayList<Participant>();
		participantsPriority = new ArrayList<Priority>();
		participantsPriorityTransitive = new ArrayList<Priority>();
		alternatives = new ArrayList<Alternative>();
		rules = new ArrayList<Rule>();
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
	
	public void addRule(Rule rule) {
		rules.add(rule);
	}
	
	public void removeRule(String ruleName) {
		for(int i=0; i<rules.size(); i++) {
    		if(rules.get(i).getName().equals(ruleName)) {
    			rules.remove(i);
    			break;
    		}
    	}
		
		for(Participant participant : participants) {
			for(int i=0; i<participant.getPreferences().size(); i++) {
				if(participant.getPreferences().get(i).getMorePriority().equals(ruleName) || participant.getPreferences().get(i).getLessPriority().equals(ruleName)) {
					participant.getPreferences().remove(i);
				}
			}
			participant.checkRulePriorityTransitivity();
		}
	}
	
	public List<Rule> getRules(){
		return rules;
	}
	
	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}
	
	public String[] getRulesNames() {
		String[] arrayRulesNames = new String[rules.size()];
		for(int i=0; i<rules.size(); i++) {
			arrayRulesNames[i] = rules.get(i).getName();
		}
		return arrayRulesNames;
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
	
	public boolean validCriteriaName(String name) {
		if(name == null || name.equals("")) return false;
		
		for(int i = 0; i < name.length(); i++) {
			if(!Character.isLetterOrDigit(name.charAt(i))) {
				return false;
			}
		}
		
		for(Criteria criteria : criterias) {
			if(criteria.getName().equals(name)) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean validCriteriaValues(String[] values, boolean isNumeric) {
		if(!isNumeric) {
			//Create Set with all the elements in the array
	        Set<String> set = new HashSet<String>(Arrays.asList(values));

	        // since Set cannot contain duplicates, so if array size and
	        // HashSet size then it can be concluded that array has all
	        // distinct or unique elements otherwise its not
	        if(!(values.length==set.size())){
	        	// Given array does not contains all unique elements, and contains duplicate elements 
	            return false;
	        }
	        
	        for(String myStr: values) {
	        	String validation = StringValidations.validateStringWithOnlyLettersAndNumbers(myStr);
	        	if(validation!=null) return false;
		    }
		}else {
			try {
				if(values.length == 2) {
					Integer.parseInt(values[0]);
					Integer.parseInt(values[1]);
				}else {
					return false;
				}
			}catch (NumberFormatException e) {
				return false;
			}
		}
		return true;
	}
	
	public void addParticipant(Participant newParticipant) {
		participants.add(newParticipant);
	}
	
	public Participant getParticipant(String name) {
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
	
	public boolean validParticipantName(String name) {
		if(StringValidations.validateStringWithOnlyLettersAndNumbers(name)!=null) return false;
		if ((name==null) || name.trim().isEmpty()) return false;
		for(Participant participant : participants) {
			if(participant.getName().equals(name)) return false;
		}
		return true;
	}
	
	public List<String> getRuleNames() {
		List<String> names = new ArrayList<String>();
		for(Rule rule : rules) {
			names.add(rule.getName());
		}
		return names;
	}
	
	public Rule getRule(String ruleName) {
		for(Rule rule : rules) {
			if(rule.getName().equals(ruleName)) return rule;
		}
		return null;
	}
	
	public void removeRules(String criteriaName) {
		List<Rule> validRules = new ArrayList<Rule>();
		for(int i=0; i<rules.size(); i++) {
			if(!rules.get(i).isUsingCriteria(criteriaName)) validRules.add(rules.get(i));
		}
		rules = new ArrayList<Rule>();
		for(Rule rule : validRules) {
			rules.add(rule);
		}
	}
	
	public String[] getParticipantsArrayString() {
		String[] arrayParticipants = new String[participants.size()];
		for(int i=0; i<participants.size(); i++) {
			arrayParticipants[i] = participants.get(i).getName();
		}
		return arrayParticipants;
	}
	
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
	
	public void addParticipantsPriority(Priority newParticipantsPriority) {
		participantsPriority.add(newParticipantsPriority);
	}
	
	public List<Priority> getParticipantsPriority(){
		return participantsPriority;
	}
	
	public void addParticipantsPriorityTransitive(Priority newParticipantsPriority) {
		participantsPriorityTransitive.add(newParticipantsPriority);
	}
	
	public List<Priority> getParticipantsPriorityTransitive(){
		return participantsPriorityTransitive;
	}
	
	public void setParticipantsPriorityTransitive(List<Priority> participantsPriorityTransitive){
		this.participantsPriorityTransitive = participantsPriorityTransitive;
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
	
	public void updateData(DataManager newData) {
		criterias = new ArrayList<Criteria>();
		participants = new ArrayList<Participant>();
		participantsPriority = new ArrayList<Priority>();
		participantsPriorityTransitive = new ArrayList<Priority>();
		alternatives = new ArrayList<Alternative>();
		rules = new ArrayList<Rule>();

		criterias = newData.getCriterias();
		participants = newData.getParticipants();
		participantsPriority = newData.getParticipantsPriority();
		participantsPriorityTransitive = newData.getParticipantsPriorityTransitive();
		alternatives = newData.getAlternatives();
		rules = newData.getRules();
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
	
	public void setParticipants(List<Participant> participants) {
		this.participants = participants;
	}
	
	public void setParticipantsPriority(List<Priority> participantsPriority) {
		this.participantsPriority = participantsPriority;
	}
	
	public void setAlternatives(List<Alternative> alternatives) {
		this.alternatives = alternatives;
	}
}
