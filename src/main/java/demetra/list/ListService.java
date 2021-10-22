package demetra.list;

import demetra.domain.Recipe;
import demetra.domain.RecipeIngredient;

import javax.enterprise.context.ApplicationScoped;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class ListService {

    public List<RecipeIngredient> getList(List<Long> recipes) {
        return recipes.stream()
                .map(id -> Recipe.<Recipe>findById(id))
                .filter(Objects::nonNull)
                .map(recipe -> recipe.ingredients)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(
                        ri -> ri.ingredient,
                        Collectors.summingInt(ri -> ri.quantity)))
                .entrySet().stream()
                .map(RecipeIngredient::from)
                .collect(Collectors.toList());
    }

}
