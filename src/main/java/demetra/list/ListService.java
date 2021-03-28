package demetra.list;

import demetra.domain.ListIngredient;
import demetra.domain.Recipe;

import javax.enterprise.context.ApplicationScoped;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ListService {

    public List<ListIngredient> getList(List<Recipe> recipes) {
        return recipes.stream()
                .map(recipe -> Recipe.<Recipe>findById(recipe.id))
                .map(recipe -> recipe.recipeIngredients)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(
                        ri -> ri.ingredient,
                        Collectors.summingInt(ri -> ri.quantity)))
                .entrySet().stream()
                .map(ListIngredient::from)
                .collect(Collectors.toList());
    }

}
