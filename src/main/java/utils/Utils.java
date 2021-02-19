package utils;

import java.util.*;

public class Utils {
    public static Scanner sc = new Scanner(System.in);

    public static void suspense(int millis) {
        try { Thread.sleep(millis);
        } catch (InterruptedException ignored) {}
    }
}
