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
	private List<WPremise> worstP;
	private List<EPremise> equalP;
	
	public Rule(String name) {
		this.name = name;
		this.betterP = new ArrayList<BPremise>();
		this.worstP = new ArrayList<WPremise>();
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
		return worstP;
	}

	public void setWorstP(List<WPremise> worstP) {
		this.worstP = worstP;
	}
	
	public void addWorstP(WPremise wPremise) {
		this.worstP.add(wPremise);
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
	
	public String[] getAvailableCriterias(DataManager data){
		List<String> availableCriterias = new ArrayList<String>();
		availableCriterias.add("-");
		
		boolean available = true;
		for(Criteria criteria : data.getCriterias()) {
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
				for(WPremise wPremise : worstP) {
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
			System.out.print(availableCriterias.get(i)+" - ");
		}
		System.out.println("");
		System.out.println(" - - - - - - - - ");
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
			for(int i=0; i<worstP.size(); i++) {
				if(worstP.get(i).getCriteria().getName().equals(criteriaName)) {
					worstP.remove(i);
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
}
