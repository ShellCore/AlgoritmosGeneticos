import mx.shell.java.genetics.Chromosome;
import mx.shell.java.genetics.GeneticAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class AlgoritmoGenerticoZudoku extends GeneticAlgorithm<Integer> {

    private Integer[] casoAProbar;


    public AlgoritmoGenerticoZudoku() {
    }

    @Override
    protected void mutationProcess() {

    }

    @Override
    protected Integer[] getInstance(int numGens) {
        return new Integer[numGens];
    }

    @Override
    protected void evaluateProcess() {
        for (Chromosome<Integer> chromosome : getPopulation()) {
            long value = 0;
            value += evaluateRow(casoAProbar, chromosome);
            value += evaluateColumn(casoAProbar, chromosome);
            value += evaluateBlock(casoAProbar, chromosome);
            value += evaluateDiagonal(casoAProbar, chromosome);
            chromosome.setValue(value);
        }
    }

    @Override
    protected void crossoverProcess() {
        List<Chromosome<Integer>> hijos = new ArrayList<>();
        int numPadres = getPopulation().size();
        for(int i = 0; i < (getPopulation().size() / 2); i += 2) {
            hijos.add(getPopulation().get(i));
            hijos.add(getPopulation().get(numPadres - i - 1));
        }
    }

    private long evaluateRow(Integer[] casoAProbar, Chromosome<Integer> chromosome) {
        return 0;
    }

    private long evaluateColumn(Integer[] casoAProbar, Chromosome<Integer> chromosome) {
        return 0;
    }

    private long evaluateBlock(Integer[] casoAProbar, Chromosome<Integer> chromosome) {
        return 0;
    }

    private long evaluateDiagonal(Integer[] casoAProbar, Chromosome<Integer> chromosome) {
        return 0;
    }


    public Integer[] getCasoAProbar() {
        return casoAProbar;
    }

    public void setCasoAProbar(Integer[] casoAProbar) {
        this.casoAProbar = casoAProbar;
    }
}
