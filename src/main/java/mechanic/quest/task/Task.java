package mechanic.quest.task;

import mechanic.Actionable;
import mechanic.quest.*;

public abstract class Task {
    @lombok.Setter protected Quest quest; // обратная связь на квест к которому относится таска
    protected String description;
    @lombok.Getter protected boolean complete = false;
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