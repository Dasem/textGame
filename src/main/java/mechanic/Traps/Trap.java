package mechanic.Traps;

import menu.Menu;
import units.character.Character;
import units.character.Stat;

public class Trap {

    private final TrapType trapType;
/*    private Menu itemMenu;*/

    public Trap (TrapType trapType) {
/*        addTrapMenu();*/
        this.trapType = trapType;
    }

/*    private void addTrapMenu() {
        itemMenu.addItem("Выпить", () -> {
            int heal = getHeal();
            Character.getInstance().healing(heal);
            System.out.println("Ну выпил и выпил, чё бубнить-то, захилен на " + heal + " ХП." + Character.getInstance().getHpBar());
            Character.getInstance().getInventory().removeItem(this);
        });
    }*/

    public int getTrapDifficulty() {
        return trapType.trapDifficulty();
    }

    public int getTrapPerceptionThreshold() {
        return trapType.trapPerceptionThreshold();
    }

    public Stat trapStat() {
        return trapType.trapStat();
    }


}
