package utils;

import java.util.Random;

public class dices {

    public int diceD4() {
            int min=1;
            int max=4;
            Random random = new Random();
            return random.nextInt(max + 1 - min) + min;
    }
    public int diceD6() {
        int min=1;
        int max=6;
        Random random = new Random();
        return random.nextInt(max + 1 - min) + min;
    }
    public int diceD8() {
        int min=1;
        int max=8;
        Random random = new Random();
        return random.nextInt(max + 1 - min) + min;
    }
    public int diceD10() {
        int min=1;
        int max=10;
        Random random = new Random();
        return random.nextInt(max + 1 - min) + min;
    }
    public int diceD12() {
        int min=1;
        int max=12;
        Random random = new Random();
        return random.nextInt(max + 1 - min) + min;
    }
    public int diceD20() {
        int min=1;
        int max=20;
        Random random = new Random();
        return random.nextInt(max + 1 - min) + min;
    }
    public int diceD100() {
        int min=10;
        int max=100;
        Random random = new Random();
        return random.nextInt(max + 1 - min) + min;
    }
    public int diceProcent() {
        int min=1;
        int max=100;
        Random random = new Random();
        return random.nextInt(max + 1 - min) + min;
    }

}
