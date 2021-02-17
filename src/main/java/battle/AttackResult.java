package battle;

public class AttackResult {
    boolean isKill;
    String attackText;

    public AttackResult(boolean isKill, String attackText) {
        this.isKill = isKill;
        this.attackText = attackText;
    }

    public boolean isKill() {
        return isKill;
    }

    public String getAttackText() {
        return attackText;
    }
}
