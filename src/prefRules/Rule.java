package prefRules;

import java.util.ArrayList;
import java.util.List;

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
}
