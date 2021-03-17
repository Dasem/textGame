package mechanic.battle;

import java.util.*;

public class BattleActionResult {
    private final List<Battler> deadBattlers;
    private final String battleActionText;
    private final Battler actor;
    private final List<Battler> actualTargets;
    private boolean emergencyFightEnd = false;

    public BattleActionResult(List<Battler> deadBattlers, String battleActionText, Battler actor, List<Battler> actualTargets) {
        this.deadBattlers = deadBattlers;
        this.battleActionText = battleActionText;
        this.actor = actor;
        this.actualTargets = actualTargets;
    }

    public BattleActionResult(List<Battler> deadBattlers, String battleActionText, Battler actor, List<Battler> actualTargets, boolean emergencyFightEnd) {
        this.deadBattlers = deadBattlers;
        this.battleActionText = battleActionText;
        this.actor = actor;
        this.actualTargets = actualTargets;
        this.emergencyFightEnd = emergencyFightEnd;
    }

    public List<Battler> getDeadBattlers() {
        return deadBattlers;
    }

    public String getBattleActionText() {
        return battleActionText;
    }

    public Battler getActor() {
        return actor;
    }

    public List<Battler> getActualTargets() {
        return actualTargets;
    }

    public boolean isEmergencyFightEnd() {
        return emergencyFightEnd;
    }
}
