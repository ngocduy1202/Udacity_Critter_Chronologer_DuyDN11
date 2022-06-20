package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.*;

import java.time.DayOfWeek;
import java.util.Set;

@Entity

public class Employee{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ElementCollection
    private Set<EmployeeSkill> listSkills;

    @ElementCollection
    private Set<DayOfWeek> listDayOfWeek;

    //Contructor -- Getter/Setter

    public Employee() {
    }

    public Employee(Long id, String name, Set<EmployeeSkill> listSkills, Set<DayOfWeek> listDayOfWeek) {
        this.id = id;
        this.name = name;
        this.listSkills = listSkills;
        this.listDayOfWeek = listDayOfWeek;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EmployeeSkill> getListSkills() {
        return listSkills;
    }

    public void setListSkills(Set<EmployeeSkill> listSkills) {
        this.listSkills = listSkills;
    }

    public Set<DayOfWeek> getListDayOfWeek() {
        return listDayOfWeek;
    }

    public void setListDayOfWeek(Set<DayOfWeek> listDayOfWeek) {
        this.listDayOfWeek = listDayOfWeek;
    }
}
