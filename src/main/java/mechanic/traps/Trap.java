package mechanic.traps;

import mechanic.location.Location;
import mechanic.dice.*;
import menu.Menu;
import menu.MenuSetting;
import units.character.Character;
import units.character.Stat;
import utils.Utils;

public class Trap {

    private final TrapType trapType;

    public Trap(TrapType trapType) {
        this.trapType = trapType;
    }

    public Menu generateTrapMenu() {
        Menu trapMenu = new Menu("Впереди ловушка. Что будешь делать?", MenuSetting.HIDE_CHARACTER_MENU, MenuSetting.ADD_BACK_BUTTON);
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
            System.out.println("Вы решаете отступить и пойти другим путём.");
            Character.getInstance().goBack();
        });
        return trapMenu;
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

