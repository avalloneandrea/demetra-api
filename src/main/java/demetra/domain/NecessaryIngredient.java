package demetra.domain;

import java.util.Map;

public class NecessaryIngredient {

    public static NecessaryIngredient from(Map.Entry<Ingredient, Integer> entry) {
        NecessaryIngredient ni = new NecessaryIngredient();
        ni.name = entry.getKey().name;
        ni.unit = entry.getKey().unit;
        ni.quantity = entry.getValue();
        return ni;
    }

    public String name;
    public String unit;
    public int quantity;

}
