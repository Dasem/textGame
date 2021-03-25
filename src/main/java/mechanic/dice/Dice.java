package mechanic.dice;


public enum Dice {
    D4(4, Dices::diceD4),
    D6(6, Dices::diceD6),
    D8(8, Dices::diceD8),
    D10(10, Dices::diceD10),
    D12(12, Dices::diceD12),
    D20(20, Dices::diceD20),
    D100(100, Dices::diceD100);

    private final int number;
    private final Rollable roll;

    Dice(int number, Rollable roll) {
        this.number = number;
        this.roll = roll;
    }

    public int roll() {
        return roll.roll();
    }

    public int getNumber() {
        return number;
    }
}
