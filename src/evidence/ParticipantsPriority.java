package evidence;

public class ParticipantsPriority {

	protected String morePriority;
	protected String lessPriority;
	
	public ParticipantsPriority(String morePriority, String lessPriority) {
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
	
}
