package mechanic.location;

import menu.*;

public class EscapeEvent extends Event {
    public EscapeEvent(int row, int column, Executable action) {
        super(row, column, action);
    }

    public EscapeEvent(int row, int column, Executable action, boolean singleTime) {
        super(row, column, action, singleTime);
    }

    public void escapeAction() {
        action.execute();
    }
}
