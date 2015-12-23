package mx.shell.java.genetics;

import java.io.Serializable;

public class Chromosome<A> implements Comparable<Chromosome<A>> {

    private A[] gens;
    private long value;

    public Chromosome() {
    }

    public Chromosome(A[] a) {
        this.gens = a;
    }

    public A[] getGens() {
        return gens;
    }

    public void setGens(A[] list) {
        this.gens = list;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();

        string.append("{");
        for (int i = 0; i < gens.length; i++) {
            string.append(gens[i].toString());
            if ((i < gens.length - 1)) {
                string.append(",");
            }
        }
        string.append("}");

        return string.toString();
    }


    @Override
    public int compareTo(Chromosome<A> o) {
        return (int) (this.getValue() - o.getValue());
    }
}
