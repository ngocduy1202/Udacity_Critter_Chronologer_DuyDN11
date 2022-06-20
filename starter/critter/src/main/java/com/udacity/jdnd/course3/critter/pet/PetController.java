package com.udacity.jdnd.course3.critter.pet;


import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.services.CustomerService;
import com.udacity.jdnd.course3.critter.services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PetService petService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = new Pet();
        // right here, I can use BeanUntils to copy properties, but I want to make it look detail
        pet.setName(petDTO.getName());
        pet.setNotes(petDTO.getNotes());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setPetType(petDTO.getType());
        pet.setCustomer(customerService.getCustomerById(petDTO.getOwnerId()));

        return getPetDTO(petService.savePet(pet, petDTO.getOwnerId())) ;
        //throw new UnsupportedOperationException();
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getPetById(petId);
        return getPetDTO(pet);
    }

    @GetMapping("/pet")
    public List<PetDTO> getPets(){
        return petService.getAllPet().stream().map(this::getPetDTO).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        Customer customer = customerService.getCustomerById(ownerId);
        List<Pet> listPets = petService.getAllPetByCustomerId(ownerId);
        List<PetDTO> petDTOS = new ArrayList<>();

        listPets.forEach(pet -> {
            petDTOS.add(new PetDTO(
                    pet.getId().longValue(), //because ID is Long type
                    pet.getPetType(),
                    pet.getName(),
                    pet.getCustomer().getId().longValue(), //because ID is Long type
                    pet.getBirthDate(),
                    pet.getNotes()));
        });
        return petDTOS;
    }


    private PetDTO getPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        petDTO.setId(pet.getId());
        petDTO.setName(pet.getName());
        petDTO.setType(pet.getPetType());
        petDTO.setOwnerId(pet.getCustomer().getId());
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setNotes(pet.getNotes());
        return petDTO;
    }
}
