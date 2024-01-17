package agent;

import java.util.ArrayList;
import java.util.List;

import dataManager.Priority;

public class Agent {

	protected String name;
	protected List<Priority> rulePriority;
	
	public Agent(String name) {
		this.name = name;
		this.rulePriority = new ArrayList<Priority>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addPreference(Priority preference) {
		rulePriority.add(preference);
	}
	
	public List<Priority> getPreferences(){
		return rulePriority;
	}
}
