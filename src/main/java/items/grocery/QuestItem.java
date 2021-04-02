package items.grocery;

import items.*;
import menu.*;

@lombok.RequiredArgsConstructor
public class QuestItem extends Item {

    private final String itemName;
    @lombok.Getter private final int questId;

    @Override
    public String getName() {
        return itemName;
    }
}
