package mechanic.battle;

@lombok.RequiredArgsConstructor
public class AttackResult {

    @lombok.Getter private final boolean isKill;
    @lombok.Getter private final String attackText;
    @lombok.Getter private final Battler attacker;
    @lombok.Getter private final Battler defender;

}
