package dataManager;

import java.util.List;
import alternative.Alternative;
import criteria.Criteria;
import participant.Participant;
import prefRules.Rule;

public class DataManager {
	
	protected DataManagerParticipant dataParticipants;
	protected DataManagerCriteria dataCriterias;
	protected DataManagerEvidence dataEvidence;
	protected DataManagerRule dataRules;
	
	protected String projectName;
	protected String folderPath;
	
	public DataManager(String projectName, String folderPath) {
		this.projectName = projectName;
		this.folderPath = folderPath;
		
		dataParticipants = new DataManagerParticipant(this);
		dataCriterias = new DataManagerCriteria(this);
		dataEvidence = new DataManagerEvidence(this);
		dataRules = new DataManagerRule(this);
	}
	
	// participants data manager --------------------------------------------------------------------------------------------------------------
	public void addParticipant(Participant newParticipant) {
		dataParticipants.addParticipant(newParticipant);
	}
	
	public void addParticipantsPriority(Priority newParticipantsPriority) {
		dataParticipants.addParticipantsPriority(newParticipantsPriority);
	}
	
	public void addParticipantsPriorityTransitive(Priority newParticipantsPriority) {
		dataParticipants.addParticipantsPriorityTransitive(newParticipantsPriority);
	}
	
	public void setParticipantsPriorityTransitive(List<Priority> participantsPriorityTransitive){
		dataParticipants.setParticipantsPriorityTransitive(participantsPriorityTransitive);
	}
	
	public void setParticipants(List<Participant> participants) {
		dataParticipants.setParticipants(participants);
	}
	
	public void setParticipantsPriority(List<Priority> participantsPriority) {
		dataParticipants.setParticipantsPriority(participantsPriority);
	}
	
	public Participant getParticipantByName(String name) {
		return dataParticipants.getParticipantByName(name);
	}
	
	public List<Participant> getParticipants(){
		return dataParticipants.getParticipants();
	}
	
	public List<String> getParticipantsNames() {
		return dataParticipants.getParticipantsNames();
	}
	
	public String[] getParticipantsArrayString() {
		return dataParticipants.getParticipantsArrayString();
	}
	
	public List<Priority> getParticipantsPriority(){
		return dataParticipants.getParticipantsPriority();
	}
	
	public List<Priority> getParticipantsPriorityTransitive(){
		return dataParticipants.getParticipantsPriorityTransitive();
	}
	
	public void removeParticipant(String participant) {
		dataParticipants.removeParticipant(participant);
	}
	
	public boolean validParticipantName(String name) {
		return dataParticipants.validParticipantName(name);
	}
	
	public void checkParticipantsPriorityTransitivity() {
		dataParticipants.checkParticipantsPriorityTransitivity();
	}
	
	// criteria data manager --------------------------------------------------------------------------------------------------------------
	public void addCriteria(Criteria criteria) {
		dataCriterias.addCriteria(criteria);
	}
	
	public void setCriterias(List<Criteria> newCriterias) {
		dataCriterias.setCriterias(newCriterias);
	}
	
	public List<Criteria> getCriterias(){
		return dataCriterias.getCriterias();
	}
	
	public Criteria getCriteria(String name) {
		return dataCriterias.getCriteria(name);
	}
	
	public void removeCriteria(String name) {
		dataCriterias.removeCriteria(name);
	}
	
	public boolean validCriteriaName(String name) {
		return dataCriterias.validCriteriaName(name);
	}
	
	public boolean validCriteriaValues(String[] values, boolean isNumeric) {
		return dataCriterias.validCriteriaValues(values, isNumeric);
	}
	
	// evidence data manager --------------------------------------------------------------------------------------------------------------
	public void addAlternative(Alternative newAlternative) {
		dataEvidence.addAlternative(newAlternative);
	}
	
	public void setAlternatives(List<Alternative> alternatives) {
		dataEvidence.setAlternatives(alternatives);
	}
	
	public List<Alternative> getAlternatives(){
		return dataEvidence.getAlternatives();
	}
	
	public List<String> getAlternativesNames(){
		return dataEvidence.getAlternativesNames();
	}
	
	public void removeAlternative(String alternative) {
		dataEvidence.removeAlternative(alternative);
	}
	
	// rule data manager --------------------------------------------------------------------------------------------------------------
	public void addRule(Rule rule) {
		dataRules.addRule(rule);
	}
	
	public void setRules(List<Rule> rules) {
		dataRules.setRules(rules);
	}
	
	public List<Rule> getRules(){
		return dataRules.getRules();
	}
	
	public Rule getRule(String ruleName) {
		return dataRules.getRule(ruleName);
	}
	
	public String[] getRulesNames() {
		return dataRules.getRulesNames();
	}
	
	public List<String> getRuleNames() {
		return dataRules.getRuleNames();
	}
	
	public void removeRule(String ruleName) {
		dataRules.removeRule(ruleName);
	}
	
	public void removeRules(String criteriaName) {
		dataRules.removeRules(criteriaName);
	}
	
	// project name and folder manager --------------------------------------------------------------------------------------------------------------
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
	
	// update data --------------------------------------------------------------------------------------------------------------
	public void updateData(DataManager newData) {
		this.dataParticipants.updateData(newData);
		this.dataCriterias.updateData(newData);
		this.dataEvidence.updateData(newData);
		this.dataRules.updateData(newData);
	}
	
	// check --------------------------------------------------------------------------------------------------------------
	public static String validateStringWithOnlyLettersAndNumbers(String s) {
		if(s == null || s.equals("")) return "Cadena de caracteres vacia";
		
		for(int i = 0; i < s.length(); i++) {
			if(!Character.isLetterOrDigit(s.charAt(i))) {
				return "Caracter invalido ("+s.charAt(i)+") en "+s+".";
			}
		}
		return null;
	}
	
	public static boolean validateStringListNotContainNewElement(List<String> list, String newElement) {
		for(String str: list) {
			if(str.equals(newElement)) {
				return false;
			}
		}
		return true;
	}
}
