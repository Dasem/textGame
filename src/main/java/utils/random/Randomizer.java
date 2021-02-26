package utils.random;

import utils.*;

import java.util.*;

public class Randomizer {
    public static <T> T randomize(ObjectWithWeight<T> ... objects) {
        int sum = Arrays.stream(objects)
                .map(ObjectWithWeight::getWeight)
                .reduce(0, Integer::sum);
        int threshold = Utils.random.nextInt(sum);

        int currentSumWeight = 0;
        for (ObjectWithWeight<T> object : objects) {
            currentSumWeight += object.getWeight();
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
