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
        return Codecs.ofSubSet(population, size);
    }

    @Override
    public Function<ISeq<Recipe>, Double> fitness() {
        return this::fitness;
    }

    @Override
    public Double fitness(ISeq<Recipe> recipes) {

        if (Set.copyOf(recipes.asList()).size() != size)
            return 0D;

        Map<Tag, Long> occurrences = recipes.stream()
                .map(Recipe::getTags)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()));

        double fitness = 0D;
        fitness += percentage(occurrences.getOrDefault(Cereals, 0L), 7);
        fitness += percentage(occurrences.getOrDefault(Potatoes, 0L), 2);
        fitness += percentage(Stream.of(RedFruits, OrangeFruits, WhiteFruits, GreenFruits, PurpleFruits)
                .map(fruit -> occurrences.getOrDefault(fruit, 0L))
                .reduce(0L, Long::sum), 14);
        fitness += percentage(Stream.of(RedVegetables, OrangeVegetables, WhiteVegetables, GreenVegetables, PurpleVegetables)
                .map(fruit -> occurrences.getOrDefault(fruit, 0L))
                .reduce(0L, Long::sum), 21);
        fitness += percentage(occurrences.getOrDefault(WhiteMeat, 0L), 1);
        fitness += percentage(occurrences.getOrDefault(RedMeat, 0L), 1);
        fitness += percentage(occurrences.getOrDefault(ProcessedMeat, 0L), 1);
        fitness += percentage(occurrences.getOrDefault(Seafood, 0L), 3);
        fitness += percentage(occurrences.getOrDefault(Eggs, 0L), 2);
        fitness += percentage(occurrences.getOrDefault(Legumes, 0L), 2);
        fitness += percentage(occurrences.getOrDefault(Dairy, 0L), 1);
        return fitness;

    }

    private double percentage(long x, int max) {
        if (x <= max)
            return ((double) x) / ((double) max);
        else
            return ((double) max - x) / ((double) max);
    }

}
