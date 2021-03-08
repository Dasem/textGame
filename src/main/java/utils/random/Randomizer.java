package utils.random;

import com.google.common.collect.Lists;
import utils.*;

import java.util.*;

public class Randomizer {
    public static <T> T randomize(ObjectAndProbability<T>... objects) {
        return randomizeContainer(Lists.newArrayList(objects)).getObject();
    }


    public static <T> ObjectAndProbability<T> randomizeContainer(Collection<ObjectAndProbability<T>> objects) {
        int sum = objects.stream()
                .map(ObjectAndProbability::getProbability)
                .reduce(0, Integer::sum);
        int threshold = Utils.random.nextInt(sum);

        int currentSumWeight = 0;
        for (ObjectAndProbability<T> object : objects) {
            currentSumWeight += object.getProbability();
            if (currentSumWeight > threshold) {
                return object;
            }
        }

        throw new IllegalStateException("Как это вообще произошло");
    }

    public static <T> T randomize(T ... objects) {
        int num = Utils.random.nextInt(objects.length);
        return objects[num];
    }


}
