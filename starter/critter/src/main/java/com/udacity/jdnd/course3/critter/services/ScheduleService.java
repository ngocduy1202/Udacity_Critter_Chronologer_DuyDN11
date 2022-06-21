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

    public List<Schedule> getScheduleByEmployeeId(Long empId) {
        //return emp.getListSchedule();
        List<Schedule> listSchedule = scheduleRepository.findAll();
        List<Schedule> listScheduleByEmp = new ArrayList<>();

//        for (Schedule s: listSchedule) {
//            if(s.getListEmp().stream().filter(employee -> employee.getId().longValue() == empId).collect(Collectors.toList())){
//                listScheduleByEmp.add(s);
//            }//
//        }
        return listSchedule.stream().filter(emp-> emp.getId().longValue() == empId).collect(Collectors.toList());
    }

     public List<Schedule> getScheduleByCustomer(Customer customer) {
        List<Schedule> customerSchedules = customer.getListPets()
                .stream()
                .map(Pet::getListSchedule)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        return customerSchedules;
    }

}
