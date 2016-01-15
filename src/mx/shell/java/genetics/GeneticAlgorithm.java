package mx.shell.java.genetics;

import mx.shell.java.utils.MathUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class GeneticAlgorithm<A> {

    private List<Chromosome> population;
    private int numGens;
    private A[] genValues;
    private int numPopulation;
    public int endCriteria = -1;
    private int numParts;
    private int numIteraciones;

    public void setUpGenData(int numGens, A[] genValues, int numPopulation, int numParts, int numIteraciones) {
        this.numGens = numGens;
        this.genValues = genValues;
        this.numPopulation = numPopulation;
        this.numParts = numParts;
        this.numIteraciones = numIteraciones;
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
        A[] res = null;
        int iteration = 0;
        System.out.println("0 - - - - - - - - 100");
        do {
            if (((double)((double)iteration*100)/(double)numIteraciones)%10 == 0) {
                System.out.print("XX");
            }
            res = evaluateProcess();
            selectionProcess();
            Collections.sort(getPopulation());
            crossoverProcess();
            mutationProcess();
            iteration++;
        } while (iteration < numIteraciones && res == null);

        System.out.println("");
        return res;
    }

    protected abstract void mutationProcess();

    private void selectionProcess() {
        int i = 0;
        population = population.subList(0, population.size()/2);
    }

    protected abstract A[] getInstance(int numGens);

    protected abstract A[] evaluateProcess();

    protected abstract void crossoverProcess();

    protected abstract List<Chromosome> apareate(Chromosome father, Chromosome mother, int numParts);

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

    public int getNumParts() {
        return numParts;
    }

    public void setNumParts(int numParts) {
        this.numParts = numParts;
    }
}
