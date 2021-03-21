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
                .map(Recipe::getTags)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()));

        double fitness = 0D;
        fitness += cusp(occurrences.getOrDefault(Cereals, 0L), ofDaily(1, size));
        fitness += cusp(occurrences.getOrDefault(Potatoes, 0L), ofWeekly(2, size));
        fitness += cusp(Stream.of(RedFruits, OrangeFruits, WhiteFruits, GreenFruits, PurpleFruits)
                .map(fruit -> occurrences.getOrDefault(fruit, 0L))
                .reduce(0L, Long::sum), ofDaily(2.5, size));
        fitness += cusp(Stream.of(RedVegetables, OrangeVegetables, WhiteVegetables, GreenVegetables, PurpleVegetables)
                .map(fruit -> occurrences.getOrDefault(fruit, 0L))
                .reduce(0L, Long::sum), ofDaily(2.5, size));
        fitness += cusp(occurrences.getOrDefault(WhiteMeat, 0L), ofWeekly(2, size));
        fitness += cusp(occurrences.getOrDefault(RedMeat, 0L), ofWeekly(1, size));
        fitness += cusp(occurrences.getOrDefault(ProcessedMeat, 0L), ofWeekly(1, size));
        fitness += cusp(occurrences.getOrDefault(Seafood, 0L), ofWeekly(3, size));
        fitness += cusp(occurrences.getOrDefault(Eggs, 0L), ofWeekly(1.5, size));
        fitness += cusp(occurrences.getOrDefault(Legumes, 0L), ofWeekly(2, size));
        fitness += cusp(occurrences.getOrDefault(Dairy, 0L), ofWeekly(1, size));
        return fitness;

    }

    private double cusp(double amount, double target) {
        if (amount <= target)
            return amount / target;
        else if (amount >= 2 * target)
            return 0d;
        return 1 - amount % target / target;
    }

}
