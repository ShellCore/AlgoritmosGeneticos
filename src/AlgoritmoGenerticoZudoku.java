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
        // TODO Falta implementar
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
        long res = 0;
        for (int row = 0; row < 9; row++) {
            List<Integer> evaluados = new ArrayList<>();
            for (int column = 0; column < 9; column++) {
                if ((casoAProbar[(row * 9) + column]) != 0) {
                    evaluados.add(casoAProbar[(row * 9) + column]);
                }
            }
            for (int column = 0; column < 9; column++) {
                int celda = chromosome.getGens()[(row * 9) + column];
                int casoPrueba = casoAProbar[(row * 9) + column];
                if (evaluados.contains(celda) || (casoPrueba != 0 && casoPrueba != celda)) {
                    res++;
                } else {
                    evaluados.add(chromosome.getGens()[(row * 9) + column]);
                }
            }
        }

        return res;
    }

    private long evaluateColumn(Integer[] casoAProbar, Chromosome<Integer> chromosome) {
        long res = 0;
        for (int col = 0; col < 9; col++) {
            List<Integer> evaluados = new ArrayList<>();
            for (int row = 0; row < 9; row++) {
                if ((casoAProbar[col + (row * 9)]) != 0) {
                    evaluados.add(casoAProbar[col+(row * 9)]);
                }
            }
            for (int row = 0; row < 9; row++) {
                int celda = chromosome.getGens()[col + (row * 9)];
                int casoPrueba = casoAProbar[col + (row * 9)];
                if (evaluados.contains(celda) || (casoPrueba != 0 && casoPrueba != celda)) {
                    res++;
                } else {
                    evaluados.add(chromosome.getGens()[col + (row * 9)]);
                }
            }
        }

        return res;
    }

    private long evaluateBlock(Integer[] casoAProbar, Chromosome<Integer> chromosome) {
        long res = 0;
        for (int bloque = 0; bloque < 9; bloque++) {
            List<Integer> evaluados = new ArrayList<>();
            for (int col = 0; col < 3; col++) {
                for (int fila = 0; fila < 3; fila++) {
                    int celda = casoAProbar[(bloque*3) + (col * 9) + fila];
                    if ((celda) != 0) {
                        evaluados.add(celda);
                    }
                }
            }
            for (int col = 0; col < 3; col++) {
                for (int fila = 0; fila < 3; fila++) {
                    int celda = chromosome.getGens()[(bloque*3) + (col * 9) + fila];
                    int casoPrueba = casoAProbar[(bloque*3) + (col * 9) + fila];
                    if (evaluados.contains(celda) || (casoPrueba != 0 && casoPrueba != celda)) {
                        res++;
                    } else {
                        evaluados.add(celda);
                    }
                }
            }
        }

        return res;
    }

    private long evaluateDiagonal(Integer[] casoAProbar, Chromosome<Integer> chromosome) {
        long res = 0;

        res += evaluaDiagonalDescendente(casoAProbar, chromosome);
        res += evaluaDiagonalAscendente(casoAProbar, chromosome);

        return res;
    }

    private long evaluaDiagonalAscendente(Integer[] casoAProbar, Chromosome<Integer> chromosome) {
        long res = 0;

        List<Integer> evaluados = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            int num = (9 * (i + 1)) - i;
            int casoPrueba = casoAProbar[num];
            if (casoPrueba != 0) {
                evaluados.add(casoPrueba);
            }
        }
        for (int i = 0; i < 9; i++) {
            int num = (9 * (i + 1)) - i;
            int celda = chromosome.getGens()[num];
            int casoPrueba = casoAProbar[num];
            if (evaluados.contains(celda) || (casoPrueba != 0 && casoPrueba != celda)) {
                res++;
            } else {
                evaluados.add(celda);
            }
        }

        return res;
    }

    private long evaluaDiagonalDescendente(Integer[] casoAProbar, Chromosome<Integer> chromosome) {
        long res = 0;

        List<Integer> evaluados = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            int casoPrueba = casoAProbar[i + (9 * i)];
            if (casoPrueba != 0) {
                evaluados.add(casoPrueba);
            }
        }
        for (int i = 0; i < 9; i++) {
            int celda = chromosome.getGens()[i + (9 * i)];
            int casoPrueba = casoAProbar[i + (9 * i)];
            if (evaluados.contains(celda) || (casoPrueba != 0 && casoPrueba != celda)) {
                res++;
            } else {
                evaluados.add(celda);
            }
        }

        return res;
    }


    public Integer[] getCasoAProbar() {
        return casoAProbar;
    }

    public void setCasoAProbar(Integer[] casoAProbar) {
        this.casoAProbar = casoAProbar;
    }
}
