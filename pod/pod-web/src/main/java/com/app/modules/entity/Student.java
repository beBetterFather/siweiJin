package com.app.modules.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Student {
    //姓名
    private String name;

    //年龄
    private int age;

    //籍贯
    private String location;

    public Student(){}

    public Student(String name, int age, String location){
        this.name = name;
        this.age = age;
        this.location = location;
    }
}
