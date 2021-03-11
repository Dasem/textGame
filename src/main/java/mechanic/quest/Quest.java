package mechanic.quest;

import mechanic.*;
import mechanic.quest.task.*;

import java.util.*;

public class Quest {
    String questIdentifier; // Идентификатор, по которому будет сдаваться квест при диалогах, напр. "questWithNpcName"
    List<Task> tasks;
    Award award;
    Actionable questCompleteAction;

    public Quest(String questIdentifier, List<Task> tasks, Award award, Actionable questCompleteAction) {
        this.questIdentifier = questIdentifier;
        this.tasks = tasks;
        this.award = award;
        this.questCompleteAction = questCompleteAction;
    }

    public void completeQuest() {
        if (readyToCompleteQuest()) {
            questCompleteAction.doAction();
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
