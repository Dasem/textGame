package items.grocery;

import items.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import menu.*;

@RequiredArgsConstructor
public class QuestItem extends Item {

    private final String itemName;
    @Getter
    private final int questId;

    @Override
    public String getName() {
        return itemName;
    }
}
