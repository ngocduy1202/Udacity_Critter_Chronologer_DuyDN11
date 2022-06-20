package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.NotFoundException;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import com.udacity.jdnd.course3.critter.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetRepository petRepository;

    public Customer getCustomerById(Long id){
        return customerRepository.getOne(id);
    }

    public List<Customer> getAllCustomer(){
        return customerRepository.findAll();
    }

    public Customer getCustomerByPetId(Long id){
        return petRepository.getOne(id).getCustomer();
    }

    public Customer saveCustomer(Customer customer, List<Long> listPetIds){
        List<Pet> listPet = new ArrayList<>();
        customer.getListPets().clear();

        // #1 use for each

        for (Long petId: listPetIds ) {
            Pet pet = petRepository.getPetbyId(petId);
            if(petRepository.existsById(pet.getId())){
                listPet.add(pet);
            }else{
                throw  new NotFoundException("Cannot found Pet");
            }
        }

//         #2 use map(element -> ()) method
//        if(listPetIds == null && !listPetIds.isEmpty()) {
//            listPet = listPetIds.stream().map((petId) -> petRepository.getOne(petId)).collect(Collectors.toList());
//        }

        customer.setListPets(listPet);

        return customerRepository.save(customer);
    }

    public void deleteCustomerById(Long id){
        customerRepository.deleteById(id);
    }

}
