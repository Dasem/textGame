package items.grocery;

import items.*;
import menu.*;

public class QuestItem extends Item {
    private final int questId;
    private final String itemName;

    public QuestItem(String itemName, int questId) {
        this.itemName = itemName;
        this.questId = questId;
    }

    public int getQuestId() {
        return questId;
    }

    @Override
    public String getName() {
        return itemName;
    }
}
