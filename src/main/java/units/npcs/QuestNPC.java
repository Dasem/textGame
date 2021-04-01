package units.npcs;

import mechanic.*;
import mechanic.quest.*;
import units.character.Character;

@lombok.RequiredArgsConstructor
public class QuestNPC extends NPC{
    private final String questId;
    private final String taskId;
    private final Actionable greetings;
    private final Actionable withoutQuest;
    private final Actionable questInProgress;
    private final Actionable dialogSuccess;
    private final Actionable questCompleted;

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
