package mechanic.battle;

import com.google.common.collect.*;
import items.Item;
import org.apache.commons.lang3.*;
import units.Lootable;
import units.character.Character;

import java.util.*;
import java.util.stream.*;

/**
 * Сражение между персонажем и списком противников
 */
public class Fight {
    private final List<Battler> battlers;
    private List<Battler> initiativeLine;

    public Fight(List<Battler> battlers) {
        this.battlers = battlers;
    }

    public Fight(Battler ... battlers) {
        this.battlers = Lists.newArrayList(battlers);
    }

    public Fight(Battler battler) {
        this.battlers = Lists.newArrayList(battler);
    }

    public void battle() {
        fillInitiativeLine();
        Collection<Item> lootFromFight = new ArrayList<>();
        boolean fightEnd = false;
        while (!fightEnd) {
            for (Battler battler : initiativeLine) {
                if (!battler.isDead()) {
                    BattleActionResult battleActionResult = battler.battleAction(initiativeLine);
                    System.out.println(battleActionResult.getBattleActionText());
                    if (battleActionResult.isEmergencyFightEnd()) {
                        fightEnd = true;
                        break;
                    }
                    if (Character.getInstance().isDead()) {
                        fightEnd = true;
                        break;
                    }
                    if (BattleUtils.extractAliveOpponents(Character.getInstance(), initiativeLine).isEmpty()) {
                        for (Battler deadBattler : battleActionResult.getDeadBattlers()) {
                            Character.getInstance().gainExp(deadBattler.mobExp());
                            if(deadBattler instanceof Lootable)
                            lootFromFight.addAll(((Lootable) deadBattler).getLoot());
                        }
                        fightEnd = true;
                        Character.getInstance().loot(lootFromFight);
                        break;
                    }
                }
            }
        }

    }

    private void fillInitiativeLine() {
        List<Battler> wantToInitiativeThrow = Lists.newArrayList(battlers); // 1: batl1, batl2; 2: batl3, batl4; 5: batl5
        wantToInitiativeThrow.add(Character.getInstance());
        initiativeLine = recursiveCalculateInitiative(wantToInitiativeThrow, 0);
    }

    private List<Battler> recursiveCalculateInitiative(List<Battler> wantToInitiativeThrow, int reRollDeep) {
        if (reRollDeep > 0) {
            System.out.println("У юнитов " + StringUtils.join(wantToInitiativeThrow.stream().map(Battler::getName).collect(Collectors.toList()), ", ") + " одинаковая инициатива, рероллим");
        }
        Map<Integer, List<BattlerWithInitiativeThrow>> battlersGroupedByInitiative = wantToInitiativeThrow.stream()
                .map(BattlerWithInitiativeThrow::new)
                // Сортируем список батлеров с их инициативой по инициативе
                .sorted()
                // Группируем баттлеров по инициативе
                .collect(Collectors.groupingBy(battlerWithInitiativeThrow -> battlerWithInitiativeThrow.rolledInitiative));
        // Собираем уже отсортированный список баттлеров по инцициативе, которую перероллили по необходимости
        ArrayList<Battler> result = new ArrayList<>();
        List<Integer> sortedInitiative = battlersGroupedByInitiative.keySet().stream().sorted((o1, o2) -> o2 - o1).collect(Collectors.toList());
        for (int initiative : sortedInitiative) {
            // Если у нас 1 элемент в группе, значит конкурентов батлеру нет и мы пихаем его в список с чистой совестью на своё место
            if (battlersGroupedByInitiative.get(initiative).size() == 1) {
                result.add(battlersGroupedByInitiative.get(initiative).get(0).battler);
            } else {
                // Если у нас больше баттлеров по этой инициативе, значит делаем дальнейший реролл таким же путём
                List<Battler> battlersForReRoll = recursiveCalculateInitiative(battlersGroupedByInitiative.get(initiative).stream()
                        .map(battlerWithInitiativeThrow -> battlerWithInitiativeThrow.battler)
                        .collect(Collectors.toList()), reRollDeep +1);
                result.addAll(battlersForReRoll);
            }
        }
        return result;
    }

    static class BattlerWithInitiativeThrow implements Comparable<BattlerWithInitiativeThrow> {
        int rolledInitiative;
        Battler battler;

        public BattlerWithInitiativeThrow(Battler battler) {
            this.battler = battler;
            this.rolledInitiative = battler.initiativeThrow();
        }

        @Override
        public int compareTo(BattlerWithInitiativeThrow o) {
            return this.rolledInitiative - o.rolledInitiative;
        }
    }
}
