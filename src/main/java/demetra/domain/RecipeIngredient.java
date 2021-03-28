package demetra.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "recipe_ingredient")
public class RecipeIngredient extends PanacheEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    private Ingredient ingredient;

    @Column
    private int quantity;

}
