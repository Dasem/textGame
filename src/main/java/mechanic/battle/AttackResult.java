package mechanic.battle;

public class AttackResult {
  private final   boolean isKill;
   private final String attackText;
   private final Battler attaker;
   private final Battler offencer;

    public AttackResult(boolean isKill, String attackText, Battler attaker, Battler offencer) {
        this.isKill = isKill;
        this.attackText = attackText;
        this.attaker = attaker;
        this.offencer = offencer;
    }

    public Battler getAttaker() {
        return attaker;
    }

    public Battler getOffencer() {
        return offencer;
    }

    public boolean isKill() {
        return isKill;
    }

    public String getAttackText() {
        return attackText;
    }
}
