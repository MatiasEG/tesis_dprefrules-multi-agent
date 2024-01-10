package dataManager;

public class Relation {
    String firstString;
    String secondString;

    public Relation(String firstString, String secondString) {
        this.firstString = firstString;
        this.secondString = secondString;
    }

	public String getFirstString() {
		return firstString;
	}

	public void setFirstString(String firstString) {
		this.firstString = firstString;
	}

	public String getSecondString() {
		return secondString;
	}

	public void setSecondString(String secondString) {
		this.secondString = secondString;
	}
    
}
