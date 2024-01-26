package dataManager;

import java.util.List;
import alternative.Alternative;
import prefRules.Rule;

public class DataManager {
	
	protected DataManagerParticipant dataParticipants;
	protected DataManagerCriteria dataCriterias;
	protected DataManagerEvidence dataEvidence;
	protected DataManagerRule dataRules;
	
	protected String projectName;
	protected String folderPath;
	
	protected boolean dataValidated;
	
	public DataManager(String projectName, String folderPath) {
		this.projectName = projectName;
		this.folderPath = folderPath;
		
		dataValidated = false;
		
		dataParticipants = new DataManagerParticipant(this);
		dataCriterias = new DataManagerCriteria(this);
		dataEvidence = new DataManagerEvidence(this);
		dataRules = new DataManagerRule(this);
	}
	
	// participants data manager --------------------------------------------------------------------------------------------------------------
	public DataManagerParticipant getDataManagerParticipant() {
		return dataParticipants;
	}
	
	// criteria data manager --------------------------------------------------------------------------------------------------------------
	public DataManagerCriteria getDataManagerCriteria() {
		return dataCriterias;
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
	
	// data validated --------------------------------------------------------------------------------------------------------------
	public void setDataValidated() {
		dataValidated = true;
	}
	
	public boolean getDataValidated() {
		return dataValidated;
	}
	
	// update data --------------------------------------------------------------------------------------------------------------
	public void updateData(DataManager newData) {
		this.projectName = newData.getProjectName();
		this.folderPath = newData.getSaveFolder();
		
		this.dataParticipants.updateData(newData.getDataManagerParticipant());
		this.dataCriterias.updateData(newData.getDataManagerCriteria());
		this.dataEvidence.updateData(newData);
		this.dataRules.updateData(newData);
	}
	
	public DataManager clone() {
		DataManager dataClone = new DataManager(this.projectName, this.folderPath);
		dataClone.updateData(this);
		return dataClone;
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
