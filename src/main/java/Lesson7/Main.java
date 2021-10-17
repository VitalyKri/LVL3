package Lesson7;


import com.sun.javaws.IconUtil;

import java.io.DataInputStream;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws NoSuchMethodException,
            InstantiationException,
            IllegalAccessException,
            InvocationTargetException, NoSuchFieldException {
        //doGoThruEncapculationDemo();
        doAnnotionDemo();
    }

    public static void doReflectionIntro() {
        Class<? extends String> stringClass = "Hello, world".getClass();
        System.out.println(stringClass.getName());
        System.out.println(stringClass.getSimpleName());

        Class<Integer> integerClass = Integer.class;
        System.out.println(integerClass.getName());
        System.out.println(integerClass.getSimpleName());

    }


    public static void doAnnotionDemo() throws NoSuchMethodException,
            InstantiationException,
            IllegalAccessException,
            InvocationTargetException, NoSuchFieldException {

        Class<Container> containerClass = Container.class;
        Constructor<Container> constructor = containerClass.getDeclaredConstructor(Object.class);

        constructor.setAccessible(true);
        Container container = constructor.newInstance("Hello, world");
        constructor.setAccessible(false);

        Field valueField = containerClass.getDeclaredField("value");
        AvailableTypes annotion = valueField.getAnnotation(AvailableTypes.class);
        System.out.println(annotion);

        Class<?> fieldType = valueField.getType();

        // выброс ошибки при неправильного типа.
        List<Class<?>> collect = Arrays.stream(annotion.classes()).collect(Collectors.toList());
        if (collect.contains(fieldType)){
            System.out.println("YES contains");
        } else {
            throw new IllegalArgumentException("NOK");
        }

        // Пребразование типов.
//        for (Class<?> availableType: annotion.classes()
//        ){
//
//            System.out.println(fieldType);
//            System.out.println(availableType);
//
//            if (availableType.isAssignableFrom(fieldType)){
//                System.out.println("YES");
//                Object cast = fieldType.cast(availableType);
//                System.out.println(cast);
//            }
//        }

    }

    public static void doGoThruEncapculationDemo() throws NoSuchMethodException,
            InstantiationException,
            IllegalAccessException,
            InvocationTargetException, NoSuchFieldException {
        // нельзя создать через объект, сделали приватный конструктор
        Class<Container> containerClass = Container.class;

        // для получения конструктора нужно задать параметр, для дженериков сложно
        // Ошибка для непубличны повторяется

        //Constructor<Container<? extends Object>> constructor = containerClass.getConstructor(Object.class);

        // нельзя делать для определенного дженерика для выполнения, т.к. все происходит в момент инициализации

        Constructor<Container> constructor = containerClass.getDeclaredConstructor(Object.class);

        // нельзя явно вызвать приватно. для этого устанавливает устанавливается публичный доступ для этого метода

        constructor.setAccessible(true);
        Container container = constructor.newInstance("Hello, world");
        constructor.setAccessible(false);

        System.out.println(container);

        Field value = containerClass.getDeclaredField("value");
        value.setAccessible(true);
        value.set(constructor, "Changed hello, world");
        value.setAccessible(false);
        System.out.println(container);

    }

    public static void doReflectionClassDataDemo() {
        Class<DataInputStream> dataInputStreamClass = DataInputStream.class;
        Class<? super DataInputStream> superClass = dataInputStreamClass.getSuperclass();// обратно extends получить родителя
        System.out.println(superClass);

        // Только публичные
        for (Field field : dataInputStreamClass.getFields()) {
            System.out.println(field);
        }
        ;

        for (Field field : dataInputStreamClass.getDeclaredFields()) {
            System.out.println(field);
        }
        ;

        for (Method method : dataInputStreamClass.getDeclaredMethods()) {
            System.out.println(method);
        }
        ;


    }

    public static void doReflectionAccessModidifiersDemo() {
        Class<String> stringClass = String.class;
        int modifiers = stringClass.getModifiers();
        if (Modifier.isAbstract(modifiers)) {
            System.out.println("class is abstract");
        }

        if (Modifier.isFinal(modifiers)) {
            System.out.println("class is Final");
        }

    }
}
