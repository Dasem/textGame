package utils;

public class Utils {
    public static void suspense(int millis) {
        try { Thread.sleep(millis);
        } catch (InterruptedException ignored) {}
    }
}
