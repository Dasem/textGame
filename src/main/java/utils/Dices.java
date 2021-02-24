package utils;

import java.util.Random;

public class Dices {

    public static int diceD4() {
        int min = 1;
        int max = 4;
        return randomInRange(min, max);
    }

    public static int diceD6() {
        int min = 1;
        int max = 6;
        return randomInRange(min, max);
    }

    public static int diceD8() {
        int min = 1;
        int max = 8;
        return randomInRange(min, max);
    }

    public static int diceD10() {
        int min = 1;
        int max = 10;
        return randomInRange(min, max);
    }

    public static int diceD12() {
        int min = 1;
        int max = 12;
        return randomInRange(min, max);
    }

    public static int diceD20() {
        int min = 1;
        int max = 20;
        return randomInRange(min, max);
    }

    public static int diceD100() {
        int min = 1;
        int max = 100;
        return randomInRange(min, max);
    }

    public static int randomInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt(max + 1 - min) + min;
    }
}
