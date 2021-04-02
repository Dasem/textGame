package mechanic.location;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mechanic.*;

@RequiredArgsConstructor
@AllArgsConstructor
public class Event {
    private final int row;
    private final int column;
    protected final Actionable action;
    @Getter
    private boolean singleTime = true;

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
