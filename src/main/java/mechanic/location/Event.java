package mechanic.location;


import mechanic.*;

public class Event {
    private final int row;
    private final int column;
    protected Actionable action;
    private boolean singleTime = true;

    public Event(int row, int column, Actionable action) {
        this.row = row;
        this.column = column;
        this.action = action;
    }

    public Event(int row, int column, Actionable action, boolean singleTime) {
        this.row = row;
        this.column = column;
        this.action = action;
        this.singleTime = singleTime;
    }

    public boolean isSingleTime() {
        return singleTime;
    }

    public boolean checkPosition(Position position) {
        return position.currentRow==row && position.currentColumn==column;
    }

    public boolean checkPositionAndRunEvent(Position position){
       if(checkPosition(position)){
           action.doAction();
           return true;
       }
       return false;
    }
}
