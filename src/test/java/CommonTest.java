import items.equipment.*;
import mechanic.location.*;
import org.junit.jupiter.api.*;
import units.character.Character;

import java.io.*;

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
}