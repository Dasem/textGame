package mechanic.battle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
@AllArgsConstructor
public class BattleActionResult {

    @Getter
    private final List<Battler> deadBattlers;
    @Getter
    private final String battleActionText;
    @Getter
    private final Battler actor;
    @Getter
    private final List<Battler> actualTargets;
    @Getter
    private boolean emergencyFightEnd = false;

}
