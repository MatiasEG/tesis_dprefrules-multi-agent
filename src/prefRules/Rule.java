package prefRules;

import java.util.ArrayList;
import java.util.List;

import criteria.Criteria;
import dataManager.DataManager;
import premises.BPremise;
import premises.EPremise;
import premises.WPremise;

public class Rule {

	private String name;
	private List<BPremise> betterP;
	private List<WPremise> worseP;
	private List<EPremise> equalP;
	
	public Rule(String name) {
		this.name = name;
		this.betterP = new ArrayList<BPremise>();
		this.worseP = new ArrayList<WPremise>();
		this.equalP = new ArrayList<EPremise>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<BPremise> getBetterP() {
		return betterP;
	}

	public void setBetterP(List<BPremise> betterP) {
		this.betterP = betterP;
	}
	
	public void addBetterP(BPremise bPremise) {
		this.betterP.add(bPremise);
	}

	public List<WPremise> getWorstP() {
		return worseP;
	}

	public void setWorstP(List<WPremise> worstP) {
		this.worseP = worstP;
	}
	
	public void addWorseP(WPremise wPremise) {
		this.worseP.add(wPremise);
	}

	public List<EPremise> getEqualP() {
		return equalP;
	}

	public void setEqualP(List<EPremise> equalP) {
		this.equalP = equalP;
	}
	
	public void addEqualP(EPremise ePremise) {
		this.equalP.add(ePremise);
	}
	
	public boolean availableCriteria(Criteria criteria) {
		for(BPremise bPremise : betterP) {
			if(bPremise.getCriteria().getName().equals(criteria.getName())) {
				return false;
			}
		}
		for(WPremise wPremise : worseP) {
			if(wPremise.getCriteria().getName().equals(criteria.getName())) {
				return false;
			}
		}
		for(EPremise ePremise : equalP) {
			if(ePremise.getCriteria().getName().equals(criteria.getName())) {
				return false;
			}
		}
		return true;
	}
	
	public String[] getAvailableCriterias(DataManager data){
		List<String> availableCriterias = new ArrayList<String>();
		availableCriterias.add("-");
		
		boolean available = true;
		for(Criteria criteria : data.getDataManagerCriteria().getCriterias()) {
			String name = criteria.getName();
			if(available) {
				for(BPremise bPremise : betterP) {
					if(bPremise.getCriteria().getName().equals(name)) {
						available = false;
						break;
					}
				}
			}
			if(available) {
				for(WPremise wPremise : worseP) {
					if(wPremise.getCriteria().getName().equals(name)) {
						available = false;
						break;
					}
				}
			}
			if(available) {
				for(EPremise ePremise : equalP) {
					if(ePremise.getCriteria().getName().equals(name)) {
						available = false;
						break;
					}
				}
			}
			if(available) availableCriterias.add(criteria.getName());
			available = true;
		}
		
		String[] availableCriteriasToReturn = new String[availableCriterias.size()];
		for(int i=0; i<availableCriterias.size(); i++) {
			availableCriteriasToReturn[i] = availableCriterias.get(i);
		}
		return availableCriteriasToReturn;
	}
	
	public void removeCondition(String criteriaName) {
		boolean ready = false;
		if(!ready) {
			for(int i=0; i<betterP.size(); i++) {
				if(betterP.get(i).getCriteria().getName().equals(criteriaName)) {
					betterP.remove(i);
					ready = true;
					break;
				}
			}
		}
		if(!ready) {
			for(int i=0; i<worseP.size(); i++) {
				if(worseP.get(i).getCriteria().getName().equals(criteriaName)) {
					worseP.remove(i);
					ready = true;
					break;
				}
			}
		}
		if(!ready) {
			for(int i=0; i<equalP.size(); i++) {
				if(equalP.get(i).getCriteria().getName().equals(criteriaName)) {
					equalP.remove(i);
					ready = true;
					break;
				}
			}
		}
	}
	
	public boolean isUsingCriteria(String criteriaName) {
		for(int i=0; i<betterP.size(); i++) {
			if(betterP.get(i).getCriteria().getName().equals(criteriaName)) {
				return true;
			}
		}
		for(int i=0; i<worseP.size(); i++) {
			if(worseP.get(i).getCriteria().getName().equals(criteriaName)) {
				return true;
			}
		}
		for(int i=0; i<equalP.size(); i++) {
			if(equalP.get(i).getCriteria().getName().equals(criteriaName)) {
				return true;
			}
		}
		return false;
	}
	
	public String getRuleDescription() {
		String description = "Para preferir la alternativa X por sobre la alternativa Y, ";
		
		for(int i=0; i<betterP.size(); i++) {
			description +=  betterP.get(i).getDescription();
			if(i<betterP.size()-1) description += ", ademas ";
		}
		for(int i=0; i<equalP.size(); i++) {
			if(i==0) description += ". Por otro lado, ";
			description += equalP.get(i).getDescription();
			if(i<equalP.size()-1) description += ", tambien ";
		}
		for(int i=0; i<worseP.size(); i++) {
			if(i==0) description += ". Por ultimo, estoy dispuesto a sacrificar ";
			description += worseP.get(i).getDescription();
			if(i<worseP.size()-1) description += ", ademas puedo sacrificar ";
		}
		return description;
	}
	
	public String toString() {
		String toString = name+";";
		for(int i=0; i<betterP.size(); i++) {
			toString +=  betterP.get(i).getPremise();
			if(i<betterP.size()-1) toString += ",";
		}
		for(int i=0; i<equalP.size(); i++) {
			if(i==0) toString += ",";
			toString += equalP.get(i).getPremise();
			if(i<equalP.size()-1) toString += ",";
		}
		for(int i=0; i<worseP.size(); i++) {
			if(i==0) toString += ",";
			toString += worseP.get(i).getPremise();
			if(i<worseP.size()-1) toString += ",";
		}
		toString += " ==> pref(X,Y)";
		return toString;
	}
	
	public void update(Rule newData) {
		this.name = newData.getName();
		this.betterP = newData.getBetterP();
		this.worseP = newData.getWorstP();
		this.equalP = newData.getEqualP();
	}
	
	public Rule clone() {
		Rule ruleClone = new Rule(name);
		for(BPremise bp : betterP) {
			ruleClone.addBetterP(bp.clone());
		}
		for(EPremise ep : equalP) {
			ruleClone.addEqualP(ep.clone());
		}
		for(WPremise wp : worseP) {
			ruleClone.addWorseP(wp.clone());
		}
		return ruleClone;
	}
}
