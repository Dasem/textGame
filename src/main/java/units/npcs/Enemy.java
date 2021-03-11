package units.npcs;

import mechanic.battle.*;
import units.*;

public abstract class Enemy extends NPC implements Battler, Lootable, Deadable {
    int currentHealth = getMaxHealth();

    boolean dead = false;

    @Override
    public void setCurrentHealth(int currentHealth) {
        if (currentHealth < 0) {
            this.currentHealth = 0;
            if (!dead) {
                dead = true;
                died();
            }
        } else {
            this.currentHealth = Math.min(currentHealth, getMaxHealth());
        }
    }

    @Override
    public void died() {

    }
}
