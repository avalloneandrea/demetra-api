package demetra.plan;

import demetra.domain.Recipe;
import io.jenetics.EnumGene;
import io.jenetics.Phenotype;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.Limits;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@ApplicationScoped
public class PlanService {

    public List<Recipe> getPlan(int days) {
        List<Recipe> recipes = Recipe.listAll();
        PlanProblem problem = PlanProblem.of(recipes, days);
        Engine<EnumGene<Recipe>, Double> engine = Engine.builder(problem)
                .executor(Executors.newSingleThreadExecutor())
                .minimizing()
                .build();
        Phenotype<EnumGene<Recipe>, Double> result = engine.stream()
                .limit(Limits.bySteadyFitness(3))
                .limit(Limits.byFixedGeneration(10))
                .collect(EvolutionResult.toBestPhenotype());
        return result.genotype().chromosome().stream()
                .map(EnumGene::allele)
                .collect(Collectors.toList());
    }

}
