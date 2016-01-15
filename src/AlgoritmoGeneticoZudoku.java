import mx.shell.java.genetics.Chromosome;
import mx.shell.java.genetics.GeneticAlgorithm;
import mx.shell.java.utils.MathUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlgoritmoGeneticoZudoku extends GeneticAlgorithm<Integer> {

    private Integer[] casoAProbar;


    public AlgoritmoGeneticoZudoku() {
    }

    @Override
    protected void mutationProcess() {
        int mutationPorcentage = 3;

        int numChromosomesToEdit = ((mutationPorcentage * getPopulation().size()) / 100);
        List<Integer> populateToEdit = new ArrayList<>();
        for (int i = 0; i < numChromosomesToEdit; i++) {
            int positionToEdit;
            do {
                positionToEdit = MathUtils.getRandom(getPopulation().size());
            } while (populateToEdit.contains(positionToEdit));
            populateToEdit.add(positionToEdit);
        }

        for (int i = 0; i < numChromosomesToEdit; i++) {
            int genPositionToEdit = MathUtils.getRandom(81);
            getPopulation().get(populateToEdit.get(i)).getGens()[genPositionToEdit] = new Integer(MathUtils.getRandom(10));
        }
    }

    @Override
    protected Integer[] getInstance(int numGens) {
        return new Integer[numGens];
    }

    @Override
    protected Integer[] evaluateProcess() {
        for (Chromosome chromosome : getPopulation()) {
            long value = 0;
            value += evaluateRow(casoAProbar, chromosome);
            value += evaluateColumn(casoAProbar, chromosome);
            value += evaluateBlock(casoAProbar, chromosome);
            value += evaluateDiagonal(casoAProbar, chromosome);
            chromosome.setValue(value);
            if (value == 0) {
                return (Integer[]) chromosome.getGens();
            }
        }
        return null;
    }

    @Override
    protected void crossoverProcess() {
        List<Chromosome> hijos = new ArrayList<>();
        int numPadres = getPopulation().size();
//        for(int i = 0; i < (numPadres / 2); i++) {
//            hijos.addAll(apareate(getPopulation().get(i), getPopulation().get((numPadres) - i - 1)));
//        }
        for(int i = 0; i < (numPadres); i+=2) {
            hijos.addAll(apareate(getPopulation().get(i), getPopulation().get(i+1), getNumParts()));
        }

        List<Chromosome> newPopulation = new ArrayList<>();
        newPopulation.addAll(getPopulation());
        newPopulation.addAll(hijos);

        setPopulation(newPopulation);
    }

    @Override
    protected List<Chromosome> apareate(Chromosome father, Chromosome mother, int numParts) {
        int chromosomeLenght = father.getGens().length;

        List<Chromosome> listaHijos = new ArrayList<>();

        Integer[] cromosomaPrimerHijo = new Integer[father.getGens().length];
        Integer[] cromosomaSegundoHijo = new Integer[father.getGens().length];



        for (int i = 0; i < numParts; i++) {
            int begin = i * chromosomeLenght / numParts;
            int end = (i + 1) * chromosomeLenght / numParts;
            Integer[] partePadre = Arrays.copyOfRange((Integer[]) father.getGens(), begin, end);
            Integer[] parteMadre = Arrays.copyOfRange((Integer[]) mother.getGens(), begin, end);
            if (i%2 == 0) {
                System.arraycopy(partePadre, 0, cromosomaPrimerHijo, begin, partePadre.length);
                System.arraycopy(parteMadre, 0, cromosomaSegundoHijo, begin, parteMadre.length);
            } else {
                System.arraycopy(parteMadre, 0, cromosomaPrimerHijo, begin, parteMadre.length);
                System.arraycopy(partePadre, 0, cromosomaSegundoHijo, begin, partePadre.length);
            }
        }

        listaHijos.add(new Chromosome<>(cromosomaPrimerHijo));
        listaHijos.add(new Chromosome<>(cromosomaSegundoHijo));

        return listaHijos;
    }

    private long evaluateRow(Integer[] casoAProbar, Chromosome chromosome) {
        long res = 0;
        for (int row = 0; row < 9; row++) {
            List<Integer> evaluados = new ArrayList<>();
            for (int column = 0; column < 9; column++) {
                if ((casoAProbar[(row * 9) + column]) != 0) {
                    evaluados.add(casoAProbar[(row * 9) + column]);
                }
            }
            for (int column = 0; column < 9; column++) {
                int celda = (int) chromosome.getGens()[(row * 9) + column];
                int casoPrueba = casoAProbar[(row * 9) + column];
                if (evaluados.contains(celda) || (casoPrueba != 0 && casoPrueba != celda)) {
                    res+=10;
                } else {
                    evaluados.add((Integer) chromosome.getGens()[(row * 9) + column]);
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
                    res+=10;
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
                        res+=10;
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
                res+=10;
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
                res+=10;
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
