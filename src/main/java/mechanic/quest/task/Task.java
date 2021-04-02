package mechanic.quest.task;

import lombok.Getter;
import lombok.Setter;
import mechanic.Actionable;
import mechanic.quest.*;

public abstract class Task {
    @Setter
    protected Quest quest; // обратная связь на квест к которому относится таска
    protected String description;
    @Getter
    protected boolean complete = false;
    protected Actionable afterTask = () -> {};

    public void closeTask() {
        complete = true;
        afterTask.doAction();
        quest.completeQuest();
    }

    public void print(){
        System.out.println(description);
    }

}