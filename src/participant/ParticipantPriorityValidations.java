package participant;

import dataManager.DataManager;
import dataManager.DataValidations;

public class ParticipantPriorityValidations {

	public static String validateAgentName(String name, DataManager data) {
		if (name != null && !name.trim().isEmpty()) {
        	if(DataValidations.validateStringWithOnlyLettersAndNumbers(name)==null) {
        		// 
        	}else {
        		return "Error, el nombre \""+name+"\" contiene caracteres no validos.";
        	}
        }else {
        	return "Por favor, ingrese un nombre para el nuevo participante en el campo de texto";
        }
		
		return "OK";
	}
	
	public static void ifNotExistAddNewAgent(String agent, DataManager data) {
		boolean exist = false;
		for(Participant ag: data.getParticipants()) {
			if(ag.getName().equals(agent)) {
				exist = true;
				break;
			}
		}
		if(!exist) data.addParticipant(new Participant(agent));
	}
}
