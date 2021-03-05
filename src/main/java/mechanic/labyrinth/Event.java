package mechanic.labyrinth;

import com.google.common.collect.Lists;
import equipment.Armor;
import equipment.ArmorType;
import equipment.Weapon;
import equipment.WeaponType;
import equipment.items.HealingPotion;
import equipment.items.HealingPotionType;
import mechanic.battle.Battler;
import mechanic.battle.Fight;
import menu.Executable;
import units.Character;
import units.npcs.*;
import utils.Utils;
import utils.random.ObjectAndProbability;
import utils.random.Randomizer;

public class Event {
    private int row;
    private int column;
    protected Executable action;
    private boolean singleTime = true;

    public Event(int row, int column, Executable action) {
        this.row = row;
        this.column = column;
        this.action = action;
    }

    public Event(int row, int column, Executable action, boolean singleTime) {
        this.row = row;
        this.column = column;
        this.action = action;
        this.singleTime = singleTime;
    }

    public boolean isSingleTime() {
        return singleTime;
    }

    public boolean checkPosition(Position position) {
        return position.currentRow==row && position.currentColumn==column;
    }

    public boolean checkPositionAndRunEvent(Position position){
       if(checkPosition(position)){
           action.execute();
           return true;
       }
       return false;
    }
}
