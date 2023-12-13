package agent;

import java.util.List;

import dataManager.DataManager;

public class AgentPriority {

	protected String morePriority;
	protected String lessPriority;
	
	public AgentPriority(String morePriority, String lessPriority) {
		this.lessPriority = lessPriority;
		this.morePriority = morePriority;
	}

	public String getMorePriority() {
		return morePriority;
	}

	public void setMorePriority(String morePriority) {
		this.morePriority = morePriority;
	}

	public String getLessPriority() {
		return lessPriority;
	}

	public void setLessPriority(String lessPriority) {
		this.lessPriority = lessPriority;
	}
	
	public String getPriorityFormatted() {
		return morePriority + " > " + lessPriority;
	}
	
	public String isValid(DataManager data) {
		List<AgentPriority> participants = data.getParticipantsPriority();
		for(AgentPriority pprior: participants) {
			if(pprior.getLessPriority().equals(lessPriority) && pprior.getMorePriority().equals(morePriority)) {
				return "Ya existe una regla de prioridad entre participantes que contempla ( "+morePriority+" > "+lessPriority+" ).";
			}
			if(pprior.getLessPriority().equals(morePriority) && pprior.getMorePriority().equals(lessPriority)) {
				return "No puede existir una prioridad entre participantes simetrica, usted define ( "+morePriority+" > "+lessPriority+" ), pero ya existe ( "+lessPriority+" > "+morePriority+" ).";
			}
		}
		
		return "OK";
	}
}
