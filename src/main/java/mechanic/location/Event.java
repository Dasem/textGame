package mechanic.location;


import mechanic.*;

@lombok.RequiredArgsConstructor
@lombok.AllArgsConstructor
public class Event {
    private final int row;
    private final int column;
    protected final Actionable action;
    @lombok.Getter private boolean singleTime = true;

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
