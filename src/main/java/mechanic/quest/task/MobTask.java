package mechanic.quest.task;

import org.apache.commons.lang3.*;

public class MobTask extends Task {
    String mobIdentifier; // Идентификатор, по которому будет сдаваться квест при диалогах, напр. "firstDialogWithNpcName"
    int mobsCount;
    int killedMobsCount = 0;

    public MobTask(String mobIdentifier, int mobsCount, String description) {
        this.mobIdentifier = mobIdentifier;
        this.mobsCount = mobsCount;
        this.description = description;
    }

    boolean killWithId(String mobIdentifier) {
        if (StringUtils.equals(mobIdentifier, this.mobIdentifier)) {
            if (++killedMobsCount == mobsCount) {
                closeTask();
            }
            return true; // Убил моба по этому таску
        }
        return false;
    }
}
