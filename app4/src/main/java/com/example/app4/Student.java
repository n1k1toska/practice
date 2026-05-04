package com.example.app4;

public class Student {
    int id;
    String fio;
    int age;
    int course;
    double gpa;

    public Student(int id, String fio, int age, int course, double gpa) {
        this.id = id;
        this.fio = fio;
        this.age = age;
        this.course = course;
        this.gpa = gpa;
    }

    @Override
    public String toString() {
        return fio + " (Курс: " + course + ", GPA: " + gpa + ")";
    }
}