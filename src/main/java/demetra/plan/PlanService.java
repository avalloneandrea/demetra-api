package demetra.plan;

import demetra.domain.Recipe;
import io.jenetics.EnumGene;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.Limits;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PlanService {

    @Transactional
    public List<Recipe> getPlan() {
        List<Recipe> recipes = Recipe.listAll();
        PlanProblem problem = PlanProblem.of(recipes, 14);
        return Engine.builder(problem).build().stream()
                .limit(Limits.bySteadyFitness(10))
                .limit(Limits.byFixedGeneration(100))
                .collect(EvolutionResult.toBestPhenotype())
                .genotype().chromosome().stream()
                .map(EnumGene::allele)
                .collect(Collectors.toList());
    }

}
