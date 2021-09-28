package Lesson1.transmitter;


import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

public class Transmitter<T extends Package> {
    // распространение в свой свойства
    private final T value;



    // распространение в свои методы
    public T getValue() {
        // каким бы типов не был т, он может вернуть объект (вариативность)
        return value;
    }

    public Transmitter(T value) {
        // но если Т определенного типа, он не может записать объект (инвариативность)
        this.value = value;
    }

    // Т привязана к контректному объекту при <T extends Package> generic всегда связан с объектом
    // На уровне класса, только объектный контекст, на уровне метода и статичный, и объектный
//    public static List<T> convertToRow(T){
//
//    }

    // так можно сделать для статичных методов
    // Здесь можно работать с методами класса DATA
    public static <DATA extends Package> List<DATA> convertToRow(DATA value){
        List<DATA> list = new ArrayList<>();
        list.add(value);
        return  list;
    }
    // здесь нельзя работать с методами класса дата напрямую, а только с методами интерфейса Package
    // здесь нельзя рабоать
    public static <DATA extends Collection<DATA>> List<DATA> convertToRow2(Package<DATA > value){
        List<DATA> list = new ArrayList<DATA>();
        list.add((DATA) value);
        return  list;
    }

    //тут тип существует только на уровне переменной, дальше его нельзя использовать
    // любой тип данных Package, который расширается от CharSequence можно работать с множеством типов родителя. T это хардкод.
    // в методе может не быть требуемого интерфейса
    public static IntStream convertToRow1(Package<? extends CharSequence> value){
        IntStream chars = value.getValue().chars();
        return  chars;
    }





}
