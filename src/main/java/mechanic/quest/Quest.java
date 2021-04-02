package mechanic.quest;

import com.google.common.collect.Lists;
import lombok.Getter;
import mechanic.*;
import mechanic.quest.task.*;

import java.util.*;

public class Quest {
    @Getter
    String questIdentifier; // Идентификатор, по которому будет сдаваться квест при диалогах, напр. "questWithNpcName"
    @Getter
    List<Task> tasks;
    Reward reward;
    Actionable questCompleteAction;
    @Getter
    boolean done = false;
    @Getter
    String description;

    public Quest(String questIdentifier,String description, Reward reward, Actionable questCompleteAction, Task ... tasks) {
        this.questIdentifier = questIdentifier;
        this.tasks = Lists.newArrayList(tasks);
        this.tasks.forEach(task -> task.setQuest(this));
        this.reward = reward;
        this.questCompleteAction = questCompleteAction;
        this.description = description;
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
}
