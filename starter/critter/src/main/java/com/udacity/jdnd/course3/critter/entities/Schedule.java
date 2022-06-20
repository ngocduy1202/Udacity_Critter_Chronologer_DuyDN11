package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity

public class Schedule  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "schedule_emp",
            joinColumns = { @JoinColumn(name = "scheduleId")},
            inverseJoinColumns = { @JoinColumn(name = "empId")}
    )
    private List<Employee> listEmp;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "schedule_pet",
            joinColumns = { @JoinColumn(name = "scheduleId")},
            inverseJoinColumns = { @JoinColumn(name = "petId")}
    )
    private List<Pet> listPet;

    @ElementCollection
    private Set<EmployeeSkill> listEmpSkill;

    //Contructor -- Getter/Setter

    public Schedule(Long id, LocalDate date, List<Employee> listEmp, List<Pet> listPet, Set<EmployeeSkill> listEmpSkill) {
        this.id = id;
        this.date = date;
        this.listEmp = listEmp;
        this.listPet = listPet;
        this.listEmpSkill = listEmpSkill;
    }

    public Schedule() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Employee> getListEmp() {
        return listEmp;
    }

    public void setListEmp(List<Employee> listEmp) {
        this.listEmp = listEmp;
    }

    public List<Pet> getListPet() {
        return listPet;
    }

    public void setListPet(List<Pet> listPet) {
        this.listPet = listPet;
    }

    public Set<EmployeeSkill> getListEmpSkill() {
        return listEmpSkill;
    }

    public void setListEmpSkill(Set<EmployeeSkill> listEmpSkill) {
        this.listEmpSkill = listEmpSkill;
    }
}
