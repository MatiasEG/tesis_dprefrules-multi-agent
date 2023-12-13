package alternative;

import java.util.ArrayList;
import java.util.List;

public class Alternative {

	protected String name;
	protected List<String> values;
	
	public Alternative(String name) {
		this.name = name;
		values = new ArrayList<String>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addValue(String value) {
		values.add(value);
	}
	
	public void removeValue(int index) {
		values.remove(index);
	}
	
	public List<String> getValues(){
		return values;
	}
	
	public String toString() {
		String toString = name;
		for(String s: values) {
			toString += ";"+s;
		}
		
		return toString;
	}
	
	public void resetValues() {
		values = new ArrayList<String>();
	}
	
}
