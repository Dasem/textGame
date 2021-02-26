import mechanic.labyrinth.*;
import units.Character;

import java.util.Scanner;

public class CommonTest {
    public static int[] massivIntov() {
        Scanner ara = new Scanner(System.in);
        int n = ara.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = ara.nextInt();
        }
        return arr;
    }

    public static void vivodFunc(int[] hzmass) {
        for (int i = 0; i < hzmass.length; i++) {
            System.out.println(hzmass[i]);
        }

    }

    public static void main(String[] args) {
        int[] massInt = massivIntov();
        vivodFunc(massInt);

    }
}
