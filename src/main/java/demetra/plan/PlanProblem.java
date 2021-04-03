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

    private final ISeq<Recipe> population;
    private final int size;

    public static PlanProblem of(final List<Recipe> population, final int size) {
        return new PlanProblem(ISeq.of(population), size);
    }

    public PlanProblem(final ISeq<Recipe> population, final int size) {
        this.population = population;
        this.size = size;
    }

    @Override
    public Codec<ISeq<Recipe>, EnumGene<Recipe>> codec() {
        return Codecs.ofSubSet(population, 2 * size);
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

        return offset(occurrences.getOrDefault(Cereals, 0L), ofDaily(1, size))
                + offset(occurrences.getOrDefault(Potatoes, 0L), ofWeekly(2, size))
                + offset(Stream.of(RedFruits, OrangeFruits, WhiteFruits, GreenFruits, PurpleFruits)
                .map(fruit -> occurrences.getOrDefault(fruit, 0L))
                .reduce(0L, Long::sum), ofDaily(2.5, size))
                + offset(Stream.of(RedVegetables, OrangeVegetables, WhiteVegetables, GreenVegetables, PurpleVegetables)
                .map(vegetable -> occurrences.getOrDefault(vegetable, 0L))
                .reduce(0L, Long::sum), ofDaily(2.5, size))
                + offset(occurrences.getOrDefault(WhiteMeat, 0L), ofWeekly(2, size))
                + offset(occurrences.getOrDefault(RedMeat, 0L), ofWeekly(1, size))
                + offset(occurrences.getOrDefault(ProcessedMeat, 0L), ofWeekly(0.5, size))
                + offset(occurrences.getOrDefault(Seafood, 0L), ofWeekly(3, size))
                + offset(occurrences.getOrDefault(Eggs, 0L), ofWeekly(1.5, size))
                + offset(occurrences.getOrDefault(Legumes, 0L), ofWeekly(2, size))
                + offset(occurrences.getOrDefault(Dairy, 0L), ofWeekly(1, size));

    }

    private double offset(double amount, double target) {
        return Math.abs(amount - target);
    }

}
