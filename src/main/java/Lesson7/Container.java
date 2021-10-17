package Lesson7;

public final class Container<T> {
    @AvailableTypes(classes = {String.class})
    private final T value;

    private Container(T value) {
        this.value = value;
    }
    public T getValue(){
        return value;
    }

    @Override
    public String toString() {
        return "Container{" +
                "value=" + value +
                '}';
    }
}
