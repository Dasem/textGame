package mechanic.location;

import mechanic.*;

public class EscapeEvent extends Event {
    public EscapeEvent(int row, int column, Actionable action) {
        super(row, column, action);
    }

    public EscapeEvent(int row, int column, Actionable action, boolean singleTime) {
        super(row, column, action, singleTime);
    }

    public void escapeAction() {
        action.doAction();
    }
}
