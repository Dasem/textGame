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
    int row;
    int column;
    Executable action;

    public Event(int row, int column, Executable action) {
        this.row = row;
        this.column = column;
        this.action = action;
    }

   public void checkPositionAndRunEvent(Position position){
       if(position.currentRow==row && position.currentColumn==column){
           action.execute();
       }
    }
}
