package com.example.myapplication.practice12;

import java.io.Serializable;

public class Student implements Serializable {
    private String fullName;
    private String group;
    private int age;
    private float grade;

    public Student(String fullName, String group, int age, float grade) {
        this.fullName = fullName;
        this.group = group;
        this.age = age;
        this.grade = grade;
    }

    public String getFullName() {
        return fullName;
    }
    public String getGroup() {
        return group;
    }
    public int getAge() {
        return age;
    }
    public float getGrade() {
        return grade;
    }
}