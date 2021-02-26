package utils.random;

public class ObjectWithWeight<T> {
    T object;
    int weight;

    public ObjectWithWeight(T object, int weight) {
        this.object = object;
        this.weight = weight;
    }

    public T getObject() {
        return object;
    }

    public int getWeight() {
        return weight;
    }
}
