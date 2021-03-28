package demetra.domain;

import java.util.Map;

public class ListIngredient {

    public static ListIngredient from(Map.Entry<Ingredient, Integer> entry) {
        ListIngredient li = new ListIngredient();
        li.name = entry.getKey().name;
        li.unit = entry.getKey().unit;
        li.quantity = entry.getValue();
        return li;
    }

    public String name;
    public String unit;
    public int quantity;

}
