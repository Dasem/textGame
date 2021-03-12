package mechanic.quest;

import com.google.common.collect.Lists;
import mechanic.*;
import mechanic.quest.task.*;
import units.Character;

import java.util.*;

public class Quest {
    String questIdentifier; // Идентификатор, по которому будет сдаваться квест при диалогах, напр. "questWithNpcName"
    List<Task> tasks;
    Reward reward;
    Actionable questCompleteAction;
    boolean done = false;

    public Quest(String questIdentifier, Reward reward, Actionable questCompleteAction, Task ... tasks) {
        this.questIdentifier = questIdentifier;
        this.tasks = Lists.newArrayList(tasks);
        this.tasks.forEach(task -> task.setQuest(this));
        this.reward = reward;
        this.questCompleteAction = questCompleteAction;
    }

    public Task addTask(Task task) {
        task.setQuest(this);
        tasks.add(task);
        return task;
    }

    public void completeQuest() {
        if (readyToCompleteQuest()) {
            questCompleteAction.doAction();
            reward.receiveReward();
            done = true;
        }
    }

    public boolean readyToCompleteQuest() {
        for (Task task : tasks) {
            if (!task.isComplete()) {
                return false;
            }
        }
        return true;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public String getQuestIdentifier() {
        return questIdentifier;
    }

    public boolean isDone() {
        return done;
    }
}
