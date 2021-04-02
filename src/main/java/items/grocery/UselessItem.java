package items.grocery;

import items.*;
import lombok.RequiredArgsConstructor;
import menu.*;

@RequiredArgsConstructor
public class UselessItem extends Item {

    private final String itemName;

    @Override
    public String getName() {
        return itemName;
    }
}
