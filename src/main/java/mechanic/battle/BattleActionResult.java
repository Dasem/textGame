package mechanic.battle;

import java.util.*;

@lombok.RequiredArgsConstructor
@lombok.AllArgsConstructor
public class BattleActionResult {

    @lombok.Getter private final List<Battler> deadBattlers;
    @lombok.Getter private final String battleActionText;
    @lombok.Getter private final Battler actor;
    @lombok.Getter private final List<Battler> actualTargets;
    @lombok.Getter private boolean emergencyFightEnd = false;

}
