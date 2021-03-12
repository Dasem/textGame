package mechanic.quest.task;

import mechanic.Actionable;
import mechanic.quest.*;

public abstract class Task {
    protected Quest quest; // обратная связь на квест к которому относится таска
    protected String description;
    protected boolean complete = false;
    protected Actionable afterTask = () -> {};

    //TODO: доделать
    public boolean isComplete() {
        return complete;
    }

    public void closeTask() {
        complete = true;
        afterTask.doAction();
        quest.completeQuest();
    }

    public void print(){
        System.out.println(description);
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }
}