package mechanic.quest.task;

import org.apache.commons.lang3.*;

public class DialogTask extends Task {
    String dialogIdentifier; // Идентификатор, по которому будет сдаваться квест при диалогах, напр. "firstDialogWithNpcName"

    public DialogTask(String dialogIdentifier, String description) {
        this.dialogIdentifier = dialogIdentifier;
        this.description = description;
    }

    boolean talkWithId(String dialogIdentifier) {
        if (StringUtils.equals(dialogIdentifier, this.dialogIdentifier)) {
            closeTask();
            return true;
        }
        return false;
    }
}
