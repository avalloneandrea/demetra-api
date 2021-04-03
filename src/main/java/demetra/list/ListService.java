package demetra.list;

import demetra.domain.NecessaryIngredient;
import demetra.domain.Recipe;

import javax.enterprise.context.ApplicationScoped;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ListService {

    public List<NecessaryIngredient> getList(List<Long> recipes) {
        return recipes.stream()
                .map(id -> Recipe.<Recipe>findById(id))
                .map(recipe -> recipe.recipeIngredients)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(
                        ri -> ri.ingredient,
                        Collectors.summingInt(ri -> ri.quantity)))
                .entrySet().stream()
                .map(NecessaryIngredient::from)
                .collect(Collectors.toList());
    }

}
