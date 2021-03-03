package mechanic.battle;

public class AttackResult {
    private final boolean isKill;
    private final String attackText;
    private final Battler attacker;
    private final Battler defender;

    public AttackResult(boolean isKill, String attackText, Battler attacker, Battler defender) {
        this.isKill = isKill;
        this.attackText = attackText;
        this.attacker = attacker;
        this.defender = defender;
    }

    public Battler getAttacker() {
        return attacker;
    }

    public Battler getDefender() {
        return defender;
    }

    public boolean isKill() {
        return isKill;
    }

    public String getAttackText() {
        return attackText;
    }
}
