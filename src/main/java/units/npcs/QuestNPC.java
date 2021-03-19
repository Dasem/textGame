package units.npcs;

import mechanic.*;
import mechanic.quest.*;
import units.character.Character;

public class QuestNPC extends NPC{
    private final String questId;
    private final String taskId;
    private final Actionable greetings;
    private final Actionable withoutQuest;
    private final Actionable questInProgress;
    private final Actionable dialogSuccess;
    private final Actionable questCompleted;

    public QuestNPC(String questId, String taskId, Actionable greetings, Actionable withoutQuest, Actionable questInProgress, Actionable dialogSuccess, Actionable questCompleted) {
        this.questId = questId;
        this.taskId = taskId;
        this.greetings = greetings;
        this.withoutQuest = withoutQuest;
        this.questInProgress = questInProgress;
        this.dialogSuccess = dialogSuccess;
        this.questCompleted = questCompleted;
    }

    public void doDialog() {
        greetings.doAction();
        Quest quest = Character.getInstance().getQuestById(questId);
        if (quest == null) {
            withoutQuest.doAction();
        } else if (quest.isDone()) {
            questCompleted.doAction();
        } else {
            Character.getInstance().tryDialog(taskId,
                    dialogSuccess,
                    questInProgress);
        }
    }
}
