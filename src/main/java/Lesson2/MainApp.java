package Lesson2;

public class MainApp {
    public static void main(String[] args) {
        StudentService studentService = new StudentService();

        System.out.println(studentService.findAll());
    }
}
