package equipment.items;

import equipment.*;

public class QuestItem implements Item {
    int questId;
    String itemName;

    @Override
    public String getName() {
        return itemName;
    }

    @Override
    public void execute() {
        // Чот с ним сделать
    }
}
