import java.util.function.*;

public class MenuChoose <T,R> {
    private String name;
    private Function<T, R> function;

    public MenuChoose(String name, Function<T, R> function) {
        this.name = name;
        this.function = function;
    }

    public String getName() {
        return name;
    }

    public Function<T, R> getFunction() {
        return function;
    }
}
