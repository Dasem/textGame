package mechanic.location;

import menu.Executable;

public class Event {
    private int row;
    private int column;
    protected Executable action;
    private boolean singleTime = true;

    public Event(int row, int column, Executable action) {
        this.row = row;
        this.column = column;
        this.action = action;
    }

    public Event(int row, int column, Executable action, boolean singleTime) {
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
           action.execute();
           return true;
       }
       return false;
    }
}
