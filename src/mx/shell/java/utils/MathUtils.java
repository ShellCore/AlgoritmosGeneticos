package mx.shell.java.utils;

import java.util.Random;

public class MathUtils {
    public static int getRandom(int sup) {
        Random r = new Random();
        return r.nextInt(sup);

    }
}
