package main.java.objects;

import java.io.Serializable;
import java.time.LocalDate;

public class Patient implements Serializable {
    public String name, phone_no, past_complications;
    int age;
    Gender gender;
    LocalDate date;


    public Patient(String name, int age, String phone_no, Gender gender, LocalDate date, String pastComplications) {
        this.name = name;
        this.age = age;
        this.phone_no = phone_no;
        this.gender = gender;
        this.date = date;
        this.past_complications = pastComplications;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Patient {");
        builder.append("name:" +name + ", ").append("gender: " + gender.name() + ", ").append("age:" + age).append("}");
        return new String(builder);
    }
}
