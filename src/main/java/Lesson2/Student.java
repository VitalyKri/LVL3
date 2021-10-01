package Lesson2;

public class Student {
    long id;
    String name, surname;

    public Student(String name, String surname) {
        this(0L,name,surname);
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Student(long id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }
}
