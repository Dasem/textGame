import com.google.common.collect.*;
import items.equipment.*;
import mechanic.location.*;
import mechanic.quest.*;
import mechanic.quest.task.*;
import org.junit.jupiter.api.*;
import units.character.Character;
import units.npcs.*;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MyFirstJUnitJupiterTests {

    @BeforeAll
    static void initCharacter() {
        Character.createTestInstance();
    }

    @Test
    void moveWeapon() {
        Weapon weapon = new Weapon(WeaponType.SWORD);

        Character.getInstance().setWeapon(weapon);
        assertEquals(weapon, Character.getInstance().getWeapon());

        Character.getInstance().removeIfEquipped(weapon);
        assertNull(Character.getInstance().getWeapon());
    }

    @Test
    @Disabled
    void locationMovingTop() {
        //mock scanner
        String input = "1";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        //define start position
        Location testLocation = new Location("testLocation");
        testLocation.addActions(new EscapeEvent(2, 3, ()->{}));

        testLocation.enterLocation(3,3);

        Position currentPosition = Character.getInstance().getCurrentPosition();

        //check
        assertEquals( 2, currentPosition.currentRow);
        assertEquals( 3, currentPosition.currentColumn);
    }

    @Test
    void questTest() {
        String input = "1\n1\n1";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        QuestNPC testNPC = new QuestNPC("testQuest", "testQuest",
                () -> System.out.println("test1"),
                () -> System.out.println("test2"),
                () -> System.out.println("test3"),
                () -> System.out.println("test4"),
                () -> System.out.println("test5"));

        Location testLocation = new Location("testLocation");

        Collection<Quest> questCollection = Character.getInstance().getActiveQuests();
        assertTrue(questCollection.isEmpty());

        testLocation.addActions(Lists.newArrayList(
                new Event(3, 3, () -> {
                    Quest quest = new Quest("testQuest",
                            "Протестить квесты",
                            new Reward(0, null),
                            () -> {
                            });
                    quest.addTask(new DialogTask("testQuest", "test message"));
                    Character.getInstance().acceptQuest(quest);
                }),
                new Event(2, 3, testNPC::doDialog, false),
                new EscapeEvent(1,3, () -> {})
        ));

        testLocation.enterLocation(4, 3);

        assertTrue(questCollection.stream().allMatch(Quest::isDone));
        Quest temp = Character.getInstance().getQuestById("testQuest");
        assertTrue(temp.isDone());
    }
}