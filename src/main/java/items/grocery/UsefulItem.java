package items.grocery;

import items.*;
import menu.*;

@lombok.RequiredArgsConstructor
public class UsefulItem extends Item {

    private final String itemName;

    @Override
    public String getName() {
        return itemName;
    }
}
