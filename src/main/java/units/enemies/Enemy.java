package units.enemies;

import mechanic.battle.*;
import mechanic.quest.Quest;
import mechanic.quest.task.MobTask;
import mechanic.quest.task.Task;
import units.*;
import units.character.Character;
import utils.*;

import java.util.List;

public abstract class Enemy extends Unit implements Lootable {
    int currentHealth = getMaxHealth();

    protected Enemy() {
        //todo красивее
        super(Fraction.getByName("Враги"));
    }

    @Override
    public void setCurrentHealth(int currentHealth) {
        if (currentHealth < 0) {
            this.currentHealth = 0;
        } else {
            this.currentHealth = Math.min(currentHealth, getMaxHealth());
        }
    }

    @Override
    public void died() {
        Character.getInstance().getExp(25);
        System.out.println("Вы получили 25 опыта");
        for(Quest quest : Character.getInstance().getActiveQuests()){
            List<Task> tasks = quest.getTasks();
            for (Task task : tasks) {
                if (task instanceof MobTask) {
                    MobTask mobTask = (MobTask) task;
                    mobTask.killWithId(getName());
                }
            }
        }
    }

    @Override
    public BattleActionResult battleAction(List<Battler> possibleTargets) {
        Utils.suspense();
        List<Battler> opponents = BattleUtils.extractAliveAllies(possibleTargets);
        return BattleUtils.doDirectAttack(this, opponents.get(0));
    }



}
