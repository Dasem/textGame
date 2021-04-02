package utils.random;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ObjectAndProbability<T> {

    @Getter
    T object;
    @Getter
    int probability;

}
