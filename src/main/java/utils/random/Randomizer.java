package utils.random;

import utils.*;

import java.util.*;

public class Randomizer {
    public static <T> T randomize(ObjectAndProbability<T>... objects) {
        int sum = Arrays.stream(objects)
                .map(ObjectAndProbability::getProbability)
                .reduce(0, Integer::sum);
        int threshold = Utils.random.nextInt(sum);

        int currentSumWeight = 0;
        for (ObjectAndProbability<T> object : objects) {
            currentSumWeight += object.getProbability();
            if (currentSumWeight > threshold) {
                return object.getObject();
            }
        }

        throw new IllegalStateException("Как это вообще произошло");
    }

    public static <T> T randomize(T ... objects) {
        int num = Utils.random.nextInt(objects.length);
        return objects[num];
    }

}
