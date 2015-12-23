import mx.shell.java.genetics.Chromosome;

public class Main {

    public static void main(String[] args) {
        AlgoritmoGenerticoZudoku algoritmoZudoku = new AlgoritmoGenerticoZudoku();

        Integer[] valoresPermitidos = {0,1,2,3,4,5,6,7,8,9};
        int numGenes = 81;
        int numPoblacion = 100;

        Integer[] casoPrueba = {
                5,3,0, 0,7,0, 0,0,0,
                6,0,0, 1,9,5, 0,0,0,
                0,9,8, 0,0,0, 0,6,0,

                8,0,0, 0,6,0, 0,0,3,
                4,0,0, 8,0,3, 0,0,1,
                7,0,0, 0,2,0, 0,0,6,

                0,6,0, 0,0,0, 2,8,0,
                0,0,0, 4,1,9, 0,0,5,
                0,0,0, 0,8,0, 0,7,9
        };

        algoritmoZudoku.setUpGenData(numGenes, valoresPermitidos, numPoblacion);
        algoritmoZudoku.setCasoAProbar(casoPrueba);

        algoritmoZudoku.inizialitePopulation();

        Integer[] resultado = algoritmoZudoku.evaluate();
    }
}
