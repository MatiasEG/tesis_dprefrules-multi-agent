package dataManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TransitivityCheck {

    public static List<Relation> getTransitivityRelations(List<Relation> existentsRelations) {
        List<Relation> transitivityRelations = new ArrayList<>();

        // Mapa para almacenar las relaciones existentes
        Map<String, String> relationsMap = new HashMap<>();

        // Construir el mapa de relaciones
        for (Relation r : existentsRelations) {
        	relationsMap.put(r.firstString, r.secondString);
        }

        // Verificar y construir transitividad
        for (Relation r : existentsRelations) {
            String firstStringAux = r.firstString;
            String secondStringAux = r.secondString;

            // Verificar si hay una relación transitiva
            if (relationsMap.containsKey(secondStringAux)) {
                String thirdStringAux = relationsMap.get(secondStringAux);
                transitivityRelations.add(new Relation(firstStringAux, thirdStringAux));
            }
        }

        return transitivityRelations;
    }

}
