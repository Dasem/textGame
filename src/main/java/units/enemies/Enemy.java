package units.enemies;

import mechanic.quest.Quest;
import mechanic.quest.task.MobTask;
import mechanic.quest.task.Task;
import units.*;
import units.character.Character;

import java.util.List;

public abstract class Enemy extends Unit implements Lootable {

    {
        setFraction(Fraction.ENEMIES);
        currentHealth = getMaxHealth();
    }

    @Override
    public void died() {
        Character.getInstance().gainExp(25);
        System.out.println("Вы получили 25 опыта");
        for (Quest quest : Character.getInstance().getActiveQuests()) {
            List<Task> tasks = quest.getTasks();
            for (Task task : tasks) {
                if (task instanceof MobTask) {
                    MobTask mobTask = (MobTask) task;
                    mobTask.killWithId(getName());
                }
            }
        }
    }
}
