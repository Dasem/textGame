package equipment.items;

import equipment.*;
import units.Character;

public class QuestItem extends Item {
    private final int questId;
    private final String itemName;

    public QuestItem(String itemName, int questId) {
        this.itemName = itemName;
        this.questId = questId;
        postInitialize();
    }

    public int getQuestId() {
        return questId;
    }

    @Override
    public String getName() {
        return itemName;
    }

    @Override
    public void execute() {
        // Чот с ним сделать
    }
}
