package ru.fortushin.tvc.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employee")
@Getter
@Setter
public class Employee {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "building")
    private String building;
    @Column(name = "floornumber")
    private int floorNumber;
    @Column(name = "department")
    private String department;
    @Column(name = "room")
    private String room;
    @Column(name = "place")
    private String place;
    @Column(name = "jobtitle")
    private String jobTitle;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;

    public Employee() {
    }

    public Employee(String building, int floorNumber, String department, String room, String place, String jobTitle, String name, String email) {
        this.building = building;
        this.floorNumber = floorNumber;
        this.department = department;
        this.room = room;
        this.place = place;
        this.jobTitle = jobTitle;
        this.name = name;
        this.email = email;
    }
}
