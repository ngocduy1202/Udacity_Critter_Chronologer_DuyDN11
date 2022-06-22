package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import com.udacity.jdnd.course3.critter.repositories.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repositories.PetRepository;
import com.udacity.jdnd.course3.critter.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Schedule> getAllSchedule(){
        return scheduleRepository.findAll();
    }

    public Schedule addSchedule(Schedule schedule, List<Long> empId, List<Long> petId){
        List<Employee> listEmp = employeeRepository.findAllById(empId);
        List<Pet> listPet = petRepository.findAllById(petId);
        schedule.setListEmp(listEmp);
        schedule.setListPet(listPet);
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getScheduleByPet(Pet pet){
        return pet.getListSchedule();
    }

    public List<Schedule> getScheduleByEmployee(Employee employee) {
        return employee.getListSchedule();
    }


     public List<Schedule> getScheduleByCustomer(Customer customer) {
        List<Schedule> customerSchedules = customer.getListPets()
                .stream()
                .map(Pet::getListSchedule)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        return customerSchedules;
    }


    public List<Schedule> getScheduleByEmployeeId(Long empId){
        return scheduleRepository.getScheduleByEmployeeId(empId);
    }

    public List<Schedule> getScheduleByPetId(Long petId){
        return scheduleRepository.getScheduleByPetId(petId);
    }

    public List<Schedule> getScheduleByCustomerId(Long cusId){
        return  scheduleRepository.getScheduleByCustomerId(cusId);
    }

}
