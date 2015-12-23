package mx.shell.java.genetics;

import mx.shell.java.utils.MathUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class GeneticAlgorithm<A> {

    private List<Chromosome> population;
    private int numGens;
    private A[] genValues;
    private int numPopulation;
    public boolean endCriteria = false;

    public void setUpGenData(int numGens, A[] genValues, int numPopulation) {
        this.numGens = numGens;
        this.genValues = genValues;
        this.numPopulation = numPopulation;
    }

    public void inizialitePopulation() {
        population = new ArrayList<>();

        for(int i = 0; i < numPopulation; i++) {
            Chromosome chromosome = new Chromosome();
            chromosome.setGens(createChromosome());
            population.add(chromosome);
        }
    }

    public A[] evaluate() {
        A[] res = getInstance(numGens);
        do {
            evaluateProcess();
            selectionProcess();
            crossoverProcess();
            mutationProcess();
        } while (endCriteria == false);

        return res;
    }

    protected abstract void mutationProcess();

    private void selectionProcess() {
        int i = 0;
        population = population.subList(0, population.size()/2);
    }

    protected abstract A[] getInstance(int numGens);

    protected abstract void evaluateProcess();

    protected abstract void crossoverProcess();

    private A[] createChromosome() {
        A[] chromosome = getInstance(numGens);
        for (int i = 0; i < numGens; i++) {
            int selected = MathUtils.getRandom(genValues.length);
            chromosome[i] = genValues[selected];
        }
        return chromosome;
    }


    public List<Chromosome> getPopulation() {
        return population;
    }

    public void setPopulation(List<Chromosome> population) {
        this.population = population;
    }




}
