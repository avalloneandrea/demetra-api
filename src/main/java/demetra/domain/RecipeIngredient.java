package demetra.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Map;

@Entity
public class RecipeIngredient extends PanacheEntity {

    public static RecipeIngredient from(Map.Entry<Ingredient, Integer> entry) {
        RecipeIngredient ri = new RecipeIngredient();
        ri.ingredient = entry.getKey();
        ri.quantity = entry.getValue();
        return ri;
    }

    @JsonIgnore
    @ManyToOne(optional = false)
    public Recipe recipe;

    @ManyToOne(optional = false)
    public Ingredient ingredient;

    @Column(nullable = false)
    public int quantity;

}
