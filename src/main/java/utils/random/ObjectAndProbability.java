package utils.random;

@lombok.AllArgsConstructor
public class ObjectAndProbability<T> {

    @lombok.Getter T object;
    @lombok.Getter int probability;

}
