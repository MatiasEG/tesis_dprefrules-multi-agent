package dataManager;

import java.util.ArrayList;
import java.util.List;

import participant.Participant;
import prefRules.Rule;

public class DataManagerRule {

	protected List<Rule> rules;

	protected DataManager data;
	
	public DataManagerRule(DataManager data) {
		this.data = data;
		
		rules = new ArrayList<Rule>();
	}
	
	// add --------------------------------------------------------------------------------------------------------------
	public void addRule(Rule rule) {
		rules.add(rule);
	}
	
	// set --------------------------------------------------------------------------------------------------------------
	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}
	
	// get --------------------------------------------------------------------------------------------------------------
	public List<Rule> getRules(){
		return rules;
	}
	
	public Rule getRule(String ruleName) {
		for(Rule rule : rules) {
			if(rule.getName().equals(ruleName)) return rule;
		}
		return null;
	}
	
	public String[] getRulesNames() {
		String[] arrayRulesNames = new String[rules.size()];
		for(int i=0; i<rules.size(); i++) {
			arrayRulesNames[i] = rules.get(i).getName();
		}
		return arrayRulesNames;
	}
	
	public List<String> getRuleNames() {
		List<String> names = new ArrayList<String>();
		for(Rule rule : rules) {
			names.add(rule.getName());
		}
		return names;
	}
	
	// delete --------------------------------------------------------------------------------------------------------------
	public void removeRule(String ruleName) {
		for(int i=0; i<rules.size(); i++) {
    		if(rules.get(i).getName().equals(ruleName)) {
    			rules.remove(i);
    			break;
    		}
    	}
		
		for(Participant participant : data.getDataManagerParticipant().getParticipants()) {
			for(int i=0; i<participant.getPreferences().size(); i++) {
				if(participant.getPreferences().get(i).getMorePriority().equals(ruleName) || participant.getPreferences().get(i).getLessPriority().equals(ruleName)) {
					participant.getPreferences().remove(i);
				}
			}
			participant.checkRulePriorityTransitivity();
		}
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
	
	// check --------------------------------------------------------------------------------------------------------------
	
	// update --------------------------------------------------------------------------------------------------------------
	public void updateData(DataManager newData) {
		this.data = newData;
		
		rules = new ArrayList<Rule>();
		
		for(Rule r : newData.getDataManagerRule().getRules()) {
			rules.add(r.clone());
		}
	}
}
