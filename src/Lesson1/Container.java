package Lesson1;

public class Container<T>{
    // распространение в свой свойства
    private final T value;

    // распространение в свои методы
    public T getValue() {
        // каким бы типов не был т, он может вернуть объект (вариативность)
        return value;
    }

    public Container(T value) {
        // но если Т определенного типа, он не может записать объект (инвариативность)
        this.value = value;
    }
}
