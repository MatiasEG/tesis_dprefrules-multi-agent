package dataManager;

import java.util.List;

public class Priority {

	protected String morePriority;
	protected String lessPriority;
	
	public Priority(String morePriority, String lessPriority) {
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
		List<Priority> participants = data.getParticipantsPriority();
		for(Priority pprior: participants) {
			if(pprior.getLessPriority().equals(lessPriority) && pprior.getMorePriority().equals(morePriority)) {
				return "Ya existe una regla de prioridad que contempla ( "+morePriority+" > "+lessPriority+" ).";
			}
			if(pprior.getLessPriority().equals(morePriority) && pprior.getMorePriority().equals(lessPriority)) {
				return "No puede existir una prioridad simetrica, usted define ( "+morePriority+" > "+lessPriority+" ), pero ya existe ( "+lessPriority+" > "+morePriority+" ).";
			}
		}
		
		List<Priority> participantsTransitive = data.getParticipantsPriorityTransitive();
		for(Priority pprior: participantsTransitive) {
			if(pprior.getLessPriority().equals(lessPriority) && pprior.getMorePriority().equals(morePriority)) {
				return "Ya existe una regla de prioridad que contempla ( "+morePriority+" > "+lessPriority+" ).";
			}
			if(pprior.getLessPriority().equals(morePriority) && pprior.getMorePriority().equals(lessPriority)) {
				return "No puede existir una prioridad simetrica, usted define ( "+morePriority+" > "+lessPriority+" ), pero ya existe ( "+lessPriority+" > "+morePriority+" ).";
			}
		}
		
		return "OK";
	}
}
