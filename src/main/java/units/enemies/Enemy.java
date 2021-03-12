package units.enemies;

import mechanic.battle.*;
import mechanic.quest.Quest;
import mechanic.quest.task.MobTask;
import mechanic.quest.task.Task;
import units.*;
import units.character.Character;

import java.util.List;

public abstract class Enemy implements Battler, Lootable, Deadable {
    int currentHealth = getMaxHealth();

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
        for(Quest quest : Character.getInstance().getActiveQuests()){
            List<Task> tasks = quest.getTasks();
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                if (task instanceof MobTask) {
                    MobTask mobTask = (MobTask) task;
                    mobTask.killWithId(getName());
                }
            }
        }
    }
}
