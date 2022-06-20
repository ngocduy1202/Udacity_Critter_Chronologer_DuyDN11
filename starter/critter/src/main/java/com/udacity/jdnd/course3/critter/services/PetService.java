package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import com.udacity.jdnd.course3.critter.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public List<Pet> getAllPet(){
        return petRepository.findAll();
    }

    public List<Pet> getAllPetByCustomerId(Long customerId){
        return petRepository.getAllPetByCustomerId(customerId);
    }

    public Pet getPetById(Long petId){
        return petRepository.getOne(petId);
    }

    public Pet savePet(Pet pet, Long customerId){
        Customer customer = customerRepository.getOne(customerId);
        pet.setCustomer(customer);
        pet = petRepository.save(pet);
        customer.addPetToCustomer(pet);
        customerRepository.save(customer);
        return pet;
    }

    public void deletePetById(Long id){
        petRepository.deleteById(id);
    }
}
