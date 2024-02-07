package IOmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import criteria.Criteria;
import dataManager.DataManager;
import prefRules.Rule;
import premises.BPremise;
import premises.EPremise;
import premises.Premise;
import premises.WPremise;

public class RuleParser {
	
	private static final String bPremisePattern = "(better)\\(X,Y,(\\w+)\\)";
	private static final String bPremiseMinDistPattern = "(min_dist)\\(X,Y,(\\w+),(\\d+)\\)";
	
	private static final String wPremisePattern = "(worse)\\(X,Y,(\\w+)\\)";
	private static final String wPremiseMaxDistPattern = "(max_dist)\\(X,Y,(\\w+),(\\d+)\\)";
	
	private static final String ePremisePattern = "(equal)\\(X,Y,(\\w+)\\)";
	
	private static final String premiseMinPattern = "(min)\\(X,(\\w+),(\\w+)\\)";
	private static final String premiseMaxPattern = "(max)\\(Y,(\\w+),(\\w+)\\)";
	
	public static boolean analizeRule(String ruleData, Rule rule, DataManager data) {
		System.out.println(ruleData);
		String[] conditions = splitRuleData(ruleData);
		System.out.println("1 --> "+rule.toString());
		int index = 0;
		for(String condition : conditions) {
			System.out.println(condition);
		}
		System.out.println("");
		
	    if (conditions[index].matches(bPremisePattern)) {
	    	return matchBPremise(conditions, index, rule, data);
	    }else if(conditions[index].matches(wPremisePattern)) {
	    	return matchWPremise(conditions, index, rule, data);
	    }else if(conditions[index].matches(ePremisePattern)){
	    	return matchEPremise(conditions, index, rule, data);
	    }else {
        	JOptionPane.showMessageDialog(null, "Error: Al leer la regla de preferencia "+rule.getName()+" desde su archivo, se detecto algun tipo de error en sus datos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        	return false;
	    }
	}
	
	private static String[] splitRuleData(String ruleData) {
        String regex = "(\\w+\\([^\\)]+\\))";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ruleData);
        List<String> conditionsList = new ArrayList<String>();
        
        while (matcher.find()) {
            String elemento = matcher.group(1);
            conditionsList.add(elemento);
        }
        
        String[] conditions = new String[conditionsList.size()];
        for(int i=0; i<conditionsList.size(); i++) {
        	conditions[i] = conditionsList.get(i);
        }
        
        for (int i = 0; i < conditions.length; i++) {
            System.out.println(conditions[i]);
        }
        return conditions;
	}
	
	private static boolean matchBPremise(String[] conditions, int index, Rule rule, DataManager data) {
		Pattern pattern = Pattern.compile(bPremisePattern);
		Matcher matcher = pattern.matcher(conditions[index]);
		System.out.println("2 --> "+rule.toString());
		matcher.find();
        String criteriaName = matcher.group(2).toLowerCase();
        
        Criteria criteria = data.getDataManagerCriteria().getCriteria(criteriaName);
        if(criteria!=null) {
        	BPremise bPremise = new BPremise(criteria);
        	if(rule.availableCriteria(criteria)) {
        		rule.addBetterP(bPremise);
            	index++;
            	if(index==conditions.length) {
            		return true;
            	}else if (conditions[index].matches(bPremiseMinDistPattern)) {
            		return matchBPremiseMinDist(conditions, index, rule, data, bPremise);
            	}else if(conditions[index].matches(premiseMinPattern)){
            		return matchPremiseMin(conditions, index, rule, data, bPremise);
            	}else if(conditions[index].matches(premiseMaxPattern)) {
            		return matchPremiseMax(conditions, index, rule, data, bPremise);
            	}else if(conditions[index].matches(bPremisePattern)) {
            		return matchBPremise(conditions, index, rule, data);
            	}else if(conditions[index].matches(wPremisePattern)) {
            		return matchWPremise(conditions, index, rule, data);
            	}else if(conditions[index].matches(ePremisePattern)) {
            		return matchEPremise(conditions, index, rule, data);
            	}else {
            		JOptionPane.showMessageDialog(null, "Error: La regla "+rule.getName()+" no posee una sintaxis correcta.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            		return false;
            	}
        	}else {
            	JOptionPane.showMessageDialog(null, "Error: Uno de los criterios utilizados en la regla "+rule.getName()+" esta siendo evaluado dos veces.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            	return false;
        	}
        }else {
        	JOptionPane.showMessageDialog(null, "Error: Uno de los criterios utilizados en la regla "+rule.getName()+" no existe.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        	return false;
        }
	}
	
	private static boolean matchBPremiseMinDist(String[] conditions, int index, Rule rule, DataManager data, BPremise bPremise) {
		Pattern pattern = Pattern.compile(bPremiseMinDistPattern);
		Matcher matcher = pattern.matcher(conditions[index]);
		System.out.println("3 --> "+rule.toString());
		matcher.find();
        String minDistCriteriaName = matcher.group(2).toLowerCase();
        String minDistValueString = matcher.group(3).toLowerCase();
        
        if(minDistCriteriaName.equals(bPremise.getCriteria().getName())) {
        	if(bPremise.validMinDistValue(minDistValueString)) {
        		bPremise.setMinDist(minDistValueString);
        		index++;
        		if(index==conditions.length) {
            		return true;
            	}else if(conditions[index].matches(premiseMinPattern)){
            		return matchPremiseMin(conditions, index, rule, data, bPremise);
            	}else if(conditions[index].matches(premiseMaxPattern)) {
            		return matchPremiseMax(conditions, index, rule, data, bPremise);
            	}else if(conditions[index].matches(bPremisePattern)) {
            		return matchBPremise(conditions, index, rule, data);
            	}else if(conditions[index].matches(wPremisePattern)) {
            		return matchWPremise(conditions, index, rule, data);
            	}else if(conditions[index].matches(ePremisePattern)) {
            		return matchEPremise(conditions, index, rule, data);
            	}else {
                	JOptionPane.showMessageDialog(null, "Error: La regla "+rule.getName()+" no posee una sintaxis correcta.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                	return false;
            	}
        	}else {
            	JOptionPane.showMessageDialog(null, "Error: Valor invalido en la regla "+rule.getName()+" no respeta el rango asignado para el criterio.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            	return false;
        	}
        }else {
        	JOptionPane.showMessageDialog(null, "Error: Uno de los criterios utilizados en la regla "+rule.getName()+" no coincide.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        	return false;
        }
	}
	
	private static boolean matchPremiseMin(String[] conditions, int index, Rule rule, DataManager data, Premise premise) {
		Pattern pattern = Pattern.compile(premiseMinPattern);
		Matcher matcher = pattern.matcher(conditions[index]);
		System.out.println("4 --> "+rule.toString());
		matcher.find();
        String minCriteriaName = matcher.group(2).toLowerCase();
        String minValueString = matcher.group(3).toLowerCase();
        
        if(minCriteriaName.equals(premise.getCriteria().getName())) {
        	if(premise.validMinXValue(minValueString)) {
        		premise.setMinValueForX(minValueString);
        		index++;
        		if(index==conditions.length) {
            		return true;
            	}else if(conditions[index].matches(premiseMaxPattern)) {
        			return matchPremiseMax(conditions, index, rule, data, premise);
        		}else if(conditions[index].matches(bPremisePattern)) {
            		return matchBPremise(conditions, index, rule, data);
            	}else if(conditions[index].matches(wPremisePattern)) {
            		return matchWPremise(conditions, index, rule, data);
            	}else if(conditions[index].matches(ePremisePattern)) {
            		return matchEPremise(conditions, index, rule, data);
            	}else {
                	JOptionPane.showMessageDialog(null, "Error: La regla "+rule.getName()+" no posee una sintaxis correcta.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                	return false;
            	}
        	}else {
            	JOptionPane.showMessageDialog(null, "Error: Valor invalido en la regla "+rule.getName()+" no respeta el rango asignado para el criterio.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            	return false;
        	}
        }else {
        	JOptionPane.showMessageDialog(null, "Error: Uno de los criterios utilizados en la regla "+rule.getName()+" no coincide.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        	return false;
        }
	}
	
	private static boolean matchPremiseMax(String[] conditions, int index, Rule rule, DataManager data, Premise premise) {
		Pattern pattern = Pattern.compile(premiseMinPattern);
		Matcher matcher = pattern.matcher(conditions[index]);
		System.out.println("5 --> "+rule.toString());
		matcher.find();
        String maxCriteriaName = matcher.group(2).toLowerCase();
        String maxValueString = matcher.group(3).toLowerCase();
        
        if(maxCriteriaName.equals(premise.getCriteria().getName())) {
        	if(premise.validMaxYValue(maxValueString)) {
        		premise.setMaxValueForY(maxValueString);
        		index++;
        		if(index==conditions.length) {
            		return true;
            	}else if(conditions[index].matches(bPremisePattern)) {
            		return matchBPremise(conditions, index, rule, data);
            	}else if(conditions[index].matches(wPremisePattern)) {
            		return matchWPremise(conditions, index, rule, data);
            	}else if(conditions[index].matches(ePremisePattern)) {
            		return matchEPremise(conditions, index, rule, data);
            	}else {
                	JOptionPane.showMessageDialog(null, "Error: La regla "+rule.getName()+" no posee una sintaxis correcta.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                	return false;
            	}
        	}else {
            	JOptionPane.showMessageDialog(null, "Error: Valor invalido en la regla "+rule.getName()+" no respeta el rango asignado para el criterio.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            	return false;
        	}
        }else {
        	JOptionPane.showMessageDialog(null, "Error: Uno de los criterios utilizados en la regla "+rule.getName()+" no coincide.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        	return false;
        }
	}
	
	private static boolean matchWPremise(String[] conditions, int index, Rule rule, DataManager data) {
		Pattern pattern = Pattern.compile(wPremisePattern);
		Matcher matcher = pattern.matcher(conditions[index]);
		System.out.println("6 --> "+rule.toString());
		matcher.find();
        String criteriaName = matcher.group(2).toLowerCase();
        
        Criteria criteria = data.getDataManagerCriteria().getCriteria(criteriaName);
        if(criteria!=null) {
        	WPremise wPremise = new WPremise(criteria);
        	if(rule.availableCriteria(criteria)) {
        		rule.addWorseP(wPremise);
            	index++;
            	if(index==conditions.length) {
            		return true;
            	}else if (conditions[index].matches(wPremiseMaxDistPattern)) {
            		return matchWPremiseMaxDist(conditions, index, rule, data, wPremise);
            	}else if(conditions[index].matches(premiseMinPattern)){
            		return matchPremiseMin(conditions, index, rule, data, wPremise);
            	}else if(conditions[index].matches(premiseMaxPattern)) {
            		return matchPremiseMax(conditions, index, rule, data, wPremise);
            	}else if(conditions[index].matches(bPremisePattern)) {
            		return matchBPremise(conditions, index, rule, data);
            	}else if(conditions[index].matches(wPremisePattern)) {
            		return matchWPremise(conditions, index, rule, data);
            	}else if(conditions[index].matches(ePremisePattern)) {
            		return matchEPremise(conditions, index, rule, data);
            	}else {
            		JOptionPane.showMessageDialog(null, "Error: La regla "+rule.getName()+" no posee una sintaxis correcta.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            		return false;
            	}
        	}else {
            	JOptionPane.showMessageDialog(null, "Error: Uno de los criterios utilizados en la regla "+rule.getName()+" esta siendo evaluado dos veces.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            	return false;
        	}
        }else {
        	JOptionPane.showMessageDialog(null, "Error: Uno de los criterios utilizados en la regla "+rule.getName()+" no existe.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        	return false;
        }
	}
	
	private static boolean matchWPremiseMaxDist(String[] conditions, int index, Rule rule, DataManager data, WPremise wPremise) {
		Pattern pattern = Pattern.compile(wPremiseMaxDistPattern);
		Matcher matcher = pattern.matcher(conditions[index]);
		System.out.println("7 --> "+rule.toString());
		matcher.find();
        String maxDistCriteriaName = matcher.group(2).toLowerCase();
        String maxDistValueString = matcher.group(3).toLowerCase();
        
        if(maxDistCriteriaName.equals(wPremise.getCriteria().getName())) {
        	if(wPremise.validMaxDistValue(maxDistValueString)) {
        		wPremise.setMaxDist(maxDistValueString);
        		index++;
        		if(index==conditions.length) {
            		return true;
            	}else if(conditions[index].matches(premiseMinPattern)){
            		return matchPremiseMin(conditions, index, rule, data, wPremise);
            	}else if(conditions[index].matches(premiseMaxPattern)) {
            		return matchPremiseMax(conditions, index, rule, data, wPremise);
            	}else if(conditions[index].matches(bPremisePattern)) {
            		return matchBPremise(conditions, index, rule, data);
            	}else if(conditions[index].matches(wPremisePattern)) {
            		return matchWPremise(conditions, index, rule, data);
            	}else if(conditions[index].matches(ePremisePattern)) {
            		return matchEPremise(conditions, index, rule, data);
            	}else {
                	JOptionPane.showMessageDialog(null, "Error: La regla "+rule.getName()+" no posee una sintaxis correcta.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                	return false;
            	}
        	}else {
            	JOptionPane.showMessageDialog(null, "Error: Valor invalido en la regla "+rule.getName()+" no respeta el rango asignado para el criterio.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            	return false;
        	}
        }else {
        	JOptionPane.showMessageDialog(null, "Error: Uno de los criterios utilizados en la regla "+rule.getName()+" no coincide.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        	return false;
        }
	}
	
	private static boolean matchEPremise(String[] conditions, int index, Rule rule, DataManager data) {
		Pattern pattern = Pattern.compile(ePremisePattern);
		Matcher matcher = pattern.matcher(conditions[index]);
		System.out.println("8 --> "+rule.toString());
		matcher.find();
        String criteriaName = matcher.group(2).toLowerCase();
        
        Criteria criteria = data.getDataManagerCriteria().getCriteria(criteriaName);
        if(criteria!=null) {
        	EPremise ePremise = new EPremise(criteria);
        	if(rule.availableCriteria(criteria)) {
        		rule.addEqualP(ePremise);
            	index++;
            	if(index==conditions.length) {
            		return true;
            	}else if(conditions[index].matches(premiseMinPattern)){
            		return matchPremiseMin(conditions, index, rule, data, ePremise);
            	}else if(conditions[index].matches(premiseMaxPattern)) {
            		return matchPremiseMax(conditions, index, rule, data, ePremise);
            	}else if(conditions[index].matches(bPremisePattern)) {
            		return matchBPremise(conditions, index, rule, data);
            	}else if(conditions[index].matches(wPremisePattern)) {
            		return matchWPremise(conditions, index, rule, data);
            	}else if(conditions[index].matches(ePremisePattern)) {
            		return matchEPremise(conditions, index, rule, data);
            	}else {
            		JOptionPane.showMessageDialog(null, "Error: La regla "+rule.getName()+" no posee una sintaxis correcta.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            		return false;
            	}
        	}else {
            	JOptionPane.showMessageDialog(null, "Error: Uno de los criterios utilizados en la regla "+rule.getName()+" esta siendo evaluado dos veces.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            	return false;
        	}
        }else {
        	JOptionPane.showMessageDialog(null, "Error: Uno de los criterios utilizados en la regla "+rule.getName()+" no existe.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        	return false;
        }
	}
	
    public static void main(String[] args) {
    	//String regla = "worse(X,Y,days), max_dist(X,Y,days,4), min(X,days,14), max(Y,days,6), worse(X,Y,clima123), max_dist(X,Y,clima123,4), better(X,Y,stars), min_dist(X,Y,stars,1)";
    	//analizeRule(regla, new Rule("reglatest"), new DataManager("",""));
    	
    	String cond = "better(X,Y,days)";
    	
    	if (cond.matches(bPremisePattern)) {
    		System.out.println("nice");
    	}else {
    		System.out.println("not nice");
    	}
    }
}
