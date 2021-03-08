package units.npcs;

import mechanic.battle.*;
import units.Lootable;

public abstract class Enemy extends NPC implements Battler, Lootable {
    int currentHealth = getMaxHealth();

    @Override
    public void setCurrentHealth(int currentHealth) {
        if (currentHealth < 0) {
            this.currentHealth = 0;
        } else {
            this.currentHealth = Math.min(currentHealth, getMaxHealth());
        }
    }
}
