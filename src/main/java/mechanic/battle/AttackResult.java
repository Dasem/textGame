package mechanic.battle;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AttackResult {

    @Getter
    private final boolean isKill;
    @Getter
    private final String attackText;
    @Getter
    private final Battler attacker;
    @Getter
    private final Battler defender;

}
