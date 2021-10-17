package Lesson7;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AvailableTypes {
    // не совсем свойство, а метод
    // дефолт должен быть константа
    // @AliasFor так можно избавиться от одного свойства
    Class<?>[] classes();
}
