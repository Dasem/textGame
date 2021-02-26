package utils;

import equipment.*;

import java.util.*;

public class Utils {
    public static Scanner sc = new Scanner(System.in);
    public static Random random = new Random();

    public static void suspense() {
       suspense(1000);
    }

    public static void suspense(int millis) {
        try { Thread.sleep(millis);
        } catch (InterruptedException ignored) {}
    }
}
