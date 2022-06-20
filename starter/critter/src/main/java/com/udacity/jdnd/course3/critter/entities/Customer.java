package com.udacity.jdnd.course3.critter.entities;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity

public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phoneNumber;

    private String notes;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Pet> listPets;


    public void addPetToCustomer(Pet pet) {
        listPets.add(pet);
    }

    //Contructor -- Getter/Setter

    public Customer() {
    }

    public Customer(Long id, String name, String phoneNumber, String notes, List<Pet> listPets) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.notes = notes;
        this.listPets = listPets;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Pet> getListPets() {
        return listPets;
    }

    public void setListPets(List<Pet> listPets) {
        this.listPets = listPets;
    }


}
