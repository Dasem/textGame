package utils.random;

public class ObjectAndProbability<T> {
    T object;
    int probability;

    public ObjectAndProbability(T object, int probability) {
        this.object = object;
        this.probability = probability;
    }

    public T getObject() {
        return object;
    }

    public int getProbability() {
        return probability;
    }
}
