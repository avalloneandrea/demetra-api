package demetra.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Recipe extends PanacheEntity {

    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    public String reference;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<RecipeIngredient> ingredients = List.of();

    public Set<Category> getCategories() {
        return ingredients.stream()
                .filter(ri -> !(ri.quantity < 50 && ri.ingredient.unit == Unit.g))
                .map(ri -> ri.ingredient)
                .map(ingredient -> ingredient.category)
                .filter(category -> !Set.of(Category.Seeds, Category.Spices, Category.Misc).contains(category))
                .collect(Collectors.toSet());
    }

}
