package mechanic.Traps;

import mechanic.dice.*;
import menu.Menu;
import menu.MenuSetting;
import units.character.Character;
import units.character.Stat;
import utils.Utils;

public class Trap {

    private final TrapType trapType;
    public Menu trapMenu = new Menu(() -> "Впереди ловушка. Что будешь делать?", MenuSetting.HIDE_CHARACTER_MENU, MenuSetting.ADD_BACK_BUTTON);

    public Trap(TrapType trapType) {
        addTrapMenu();
        this.trapType = trapType;
    }

    private void addTrapMenu() {
        trapMenu.addItem("Пройти ловушку", () -> {
            int rollResult = Dice.D20.roll();
            System.out.println("Вы совершаете проверку характеристики: " + getTrapStat().getName() + ".");
            Utils.suspense();
            System.out.println("Ваш результат: " + (rollResult + Character.getInstance().factStat(getTrapStat())));
            Utils.suspense();
            if (rollResult + Character.getInstance().factStat(getTrapStat()) >= getTrapDifficulty()) {
                System.out.println(getTextTrapSuccess());

            } else {
                int trapDamageOut = (getTrapDamage() / 2);
                Character.getInstance().takeDamage((getTrapDamage() / 2));
                System.out.println(getTextTrapFail());
                System.out.println("Вы получаете " + trapDamageOut + " урона.");

            }

        });
        trapMenu.addItem("Отступить", () -> {

            System.out.println("Вы скипаете ловушку =(");
        });

    }

    public int getTrapDifficulty() {
        return trapType.trapDifficulty();
    }

    public int getTrapPerceptionThreshold() {
        return trapType.trapPerceptionThreshold();
    }

    public int getTrapDamage() {
        return trapType.trapDamage();
    }

    public Stat getTrapStat() {
        return trapType.trapStat();
    }

    public String getTextTrapNotNoticed() {
        return trapType.textTrapNotNoticed();
    }

    public String getTextTrapNoticed() {
        return trapType.textTrapNoticed();
    }

    public String getTextTrapSuccess() {
        return trapType.textTrapSuccess();
    }

    public String getTextTrapFail() {
        return trapType.textTrapFail();
    }

    public boolean getReverse() {
        return trapType.reverse();
    }

}

