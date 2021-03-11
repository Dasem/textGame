package mechanic.quest.task;

import mechanic.quest.*;

public abstract class Task {
    protected Quest quest; // обратная связь на квест к которому относится таска
    protected String description;
    boolean complete = false;

    //TODO: доделать
    public boolean isComplete() {
        return complete;
    }

    public void closeTask() {
        complete = true;
        quest.completeQuest();
    }
}