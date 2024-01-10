package dataManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransitivityCheck {

	public static void main(String[] args) {
		// Ejemplo de uso
        List<Relation> relaciones = new ArrayList<>();
        relaciones.add(new Relation("x", "y"));
        relaciones.add(new Relation("x", "a"));
        relaciones.add(new Relation("x", "b")); // En este caso esta aparece de nuevo, como transitiva
        relaciones.add(new Relation("y", "z"));
        relaciones.add(new Relation("z", "w"));
        relaciones.add(new Relation("w", "v"));
        relaciones.add(new Relation("v", "p"));
        relaciones.add(new Relation("p", "g"));
        relaciones.add(new Relation("a", "b")); // Por este motivo
        relaciones.add(new Relation("b", "c"));

        List<Relation> result = getTransitivityRelations(relaciones);

        // Imprimir el resultado
        for (Relation relacion : result) {
            System.out.println(relacion.getFirstString() + " -> " + relacion.getSecondString());
        }
	}
	
	public static List<Relation> getTransitivityRelations(List<Relation> existentRelations) {
        List<Relation> result = new ArrayList<>();

        // Mapa para almacenar las relaciones directas
        Map<String, String> directRelationsMap = new HashMap<>();

        // Construir el mapa de relaciones directas
        for (Relation r : existentRelations) {
            directRelationsMap.put(r.getFirstString(), r.getSecondString());
        }

        // Verificar y construir transitividad indirecta
        for (Relation r : existentRelations) {
            String firstString = r.getFirstString();
            String secondString = r.getSecondString();

            // Verificar si hay una relación transitiva indirecta
            if (directRelationsMap.containsKey(secondString)) {
                String thirdString = directRelationsMap.get(secondString);
                Relation newRelation = new Relation(firstString, thirdString);
                if(isNewRelation(existentRelations, result, newRelation)) {
                	result.add(newRelation);
                }
                getTransitivityRelationsAux(result, existentRelations, firstString, thirdString, directRelationsMap);
            }
        }

        return result;
    }

    private static void getTransitivityRelationsAux(List<Relation> result, List<Relation> existentRelations, String firstString, String secondString, Map<String, String> directRelationsMap) {
        if (directRelationsMap.containsKey(secondString)) {
            String thirdString = directRelationsMap.get(secondString);
            Relation newRelation = new Relation(firstString, thirdString);
            if(isNewRelation(existentRelations, result, newRelation)) {
            	result.add(newRelation);
            }
            getTransitivityRelationsAux(result, existentRelations, firstString, thirdString, directRelationsMap);
        }
    }

    private static boolean isNewRelation(List<Relation> existentRelations, List<Relation> transitiveRelations, Relation newRelation) {
    	for(Relation r: existentRelations) {
    		if(r.getFirstString().equals(newRelation.getFirstString()) && r.getSecondString().equals(newRelation.getSecondString())) {
    			return false;
    		}
    	}
    	
    	for(Relation r: transitiveRelations) {
    		if(r.getFirstString().equals(newRelation.getFirstString()) && r.getSecondString().equals(newRelation.getSecondString())) {
    			return false;
    		}
    	}
    	
    	return true;
    }
    
}
