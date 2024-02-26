package dataManager;

import java.util.List;

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
	
	// others data managers --------------------------------------------------------------------------------------------------------------
	public DataManagerParticipant getDataManagerParticipant() {
		return dataParticipants;
	}
	
	public DataManagerCriteria getDataManagerCriteria() {
		return dataCriterias;
	}
	
	public DataManagerEvidence getDataManagerEvidence() {
		return dataEvidence;
	}
	
	public DataManagerRule getDataManagerRule() {
		return dataRules;
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
		
		this.dataParticipants.updateData(newData);
		this.dataCriterias.updateData(newData);
		this.dataEvidence.updateData(newData);
		this.dataRules.updateData(newData);
	}
	
	public DataManager clone() {
		DataManager dataClone = new DataManager(this.projectName, this.folderPath);
		dataClone.updateData(this);
		return dataClone;
	}
	
	// check --------------------------------------------------------------------------------------------------------------
	public static String validateStringWithOnlyLetters(String s) {
		if(s == null || s.equals("")) return "Cadena de caracteres vacia";
		
		for(int i = 0; i < s.length(); i++) {
			if(i == 0) {
				if(!Character.isLetter(s.charAt(i))) {
					return "Caracter invalido ("+s.charAt(i)+") en "+s+".";
				}
			}else if(!Character.isLetterOrDigit(s.charAt(i))){
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
