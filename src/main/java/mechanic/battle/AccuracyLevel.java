package mechanic.battle;

import mechanic.battle.Battler;

public enum AccuracyLevel {

    CRITICAL_HIT("Критический удар") {
        @Override
        public int getTotalDamage(Battler battler) {
            return Math.max(battler.getOnHitDamage() + battler.getOnHitDamage(), 0);
        }
    },
    NORMAL_HIT("Попадание") {
        @Override
        public int getTotalDamage(Battler battler) {
            return Math.max(battler.getOnHitDamage(), 0);
        }
    },
    MISS("Промах") {
        @Override
        public int getTotalDamage(Battler battler) {
            return 0;
        }
    };

    private final String title;

    AccuracyLevel(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    abstract public int getTotalDamage(Battler battler);


}
