package participant;

import java.util.ArrayList;
import java.util.List;

import dataManager.Priority;
import dataManager.Relation;
import dataManager.TransitivityCheck;

public class Participant {

	protected String name;
	protected List<Priority> rulePriority;
	protected List<Priority> rulePriorityTransitive;
	
	public Participant(String name) {
		this.name = name;
		this.rulePriority = new ArrayList<Priority>();
		this.rulePriorityTransitive = new ArrayList<Priority>();
	}

	public List<Priority> getRulePriorityTransitive() {
		return rulePriorityTransitive;
	}

	public void setRulePriorityTransitive(List<Priority> rulePriorityTransitive) {
		this.rulePriorityTransitive = rulePriorityTransitive;
	}
	
	public void setRulePriority(List<Priority> rulePriority) {
		this.rulePriority = rulePriority;
	}
	
	public void addRulePriorityTransitive(Priority prior) {
		rulePriorityTransitive.add(prior);
	}
	
	public void checkRulePriorityTransitivity() {
		rulePriorityTransitive = new ArrayList<Priority>();
		List<Relation> relations = new ArrayList<>();
		for(Priority prior: rulePriority) {
			relations.add(new Relation(prior.getMorePriority(), prior.getLessPriority()));
		}
		List<Relation> transitiveRelations = TransitivityCheck.getTransitivityRelations(relations);
		for(Relation r: transitiveRelations) {
			rulePriorityTransitive.add(new Priority(r.getFirstString(), r.getSecondString()));
		}
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
	
	public List<Priority> getPreferencesTransitive(){
		return rulePriorityTransitive;
	}
	
	public Participant clone() {
		Participant pClone = new Participant(name);
		for(Priority p : rulePriority) {
			pClone.addPreference(p);
		}
		for(Priority p : rulePriorityTransitive) {
			pClone.addRulePriorityTransitive(p);
		}
		return pClone;
	}
}
