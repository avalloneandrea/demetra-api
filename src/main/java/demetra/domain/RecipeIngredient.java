package demetra.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "recipe_ingredient")
public class RecipeIngredient extends PanacheEntity {

    @ManyToOne(optional = false)
    private Recipe recipe;

    @ManyToOne(optional = false)
    private Ingredient ingredient;

    @Column(nullable = false)
    private int quantity;

}
