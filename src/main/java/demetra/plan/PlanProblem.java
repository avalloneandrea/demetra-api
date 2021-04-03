package demetra.plan;

import demetra.domain.Recipe;
import demetra.domain.Tag;
import io.jenetics.EnumGene;
import io.jenetics.engine.Codec;
import io.jenetics.engine.Codecs;
import io.jenetics.engine.Problem;
import io.jenetics.util.ISeq;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static demetra.domain.Frequency.ofDaily;
import static demetra.domain.Frequency.ofWeekly;
import static demetra.domain.Tag.*;

public class PlanProblem implements Problem<ISeq<Recipe>, EnumGene<Recipe>, Double> {

    private static final int RecipesPerDay = 2;

    private final ISeq<Recipe> recipes;
    private final int days;

    public static PlanProblem of(final List<Recipe> recipes, final int days) {
        return new PlanProblem(ISeq.of(recipes), days);
    }

    public PlanProblem(final ISeq<Recipe> recipes, final int days) {
        this.recipes = recipes;
        this.days = days;
    }

    @Override
    public Codec<ISeq<Recipe>, EnumGene<Recipe>> codec() {
        return Codecs.ofSubSet(recipes, days * RecipesPerDay);
    }

    @Override
    public Function<ISeq<Recipe>, Double> fitness() {
        return this::fitness;
    }

    @Override
    public Double fitness(ISeq<Recipe> recipes) {

        if (Set.copyOf(recipes.asList()).size() != recipes.size())
            return 0D;

        Map<Tag, Long> occurrences = recipes.stream()
                .map(recipe -> recipe.tags)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()));

        return offset(occurrences.getOrDefault(Cereals, 0L), ofDaily(1, days))
                + offset(occurrences.getOrDefault(Potatoes, 0L), ofWeekly(2, days))
                + offset(Stream.of(RedFruits, OrangeFruits, WhiteFruits, GreenFruits, PurpleFruits)
                .map(fruit -> occurrences.getOrDefault(fruit, 0L))
                .reduce(0L, Long::sum), ofDaily(2.5, days))
                + offset(Stream.of(RedVegetables, OrangeVegetables, WhiteVegetables, GreenVegetables, PurpleVegetables)
                .map(vegetable -> occurrences.getOrDefault(vegetable, 0L))
                .reduce(0L, Long::sum), ofDaily(2.5, days))
                + offset(occurrences.getOrDefault(WhiteMeat, 0L), ofWeekly(2, days))
                + offset(occurrences.getOrDefault(RedMeat, 0L), ofWeekly(1, days))
                + offset(occurrences.getOrDefault(ProcessedMeat, 0L), ofWeekly(1, days))
                + offset(occurrences.getOrDefault(Seafood, 0L), ofWeekly(3, days))
                + offset(occurrences.getOrDefault(Eggs, 0L), ofWeekly(3, days))
                + offset(occurrences.getOrDefault(Legumes, 0L), ofWeekly(2, days))
                + offset(occurrences.getOrDefault(Dairy, 0L), ofWeekly(1, days));

    }

    private double offset(double amount, double target) {
        return Math.abs(amount - target);
    }

}
