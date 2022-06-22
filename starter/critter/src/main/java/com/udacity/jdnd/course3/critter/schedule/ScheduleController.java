package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.services.CustomerService;
import com.udacity.jdnd.course3.critter.services.EmployeeService;
import com.udacity.jdnd.course3.critter.services.PetService;
import com.udacity.jdnd.course3.critter.services.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PetService petService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        //BeanUtils.copyProperties(scheduleDTO, schedule);
        schedule.setDate(scheduleDTO.getDate());
        schedule.setListEmpSkill(scheduleDTO.getActivities());
        return getScheduleDTO(scheduleService.addSchedule(schedule, scheduleDTO.getEmployeeIds(), scheduleDTO.getPetIds()));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedule();
        return getListScheduleDTO(schedules);
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
//        Pet pet = petService.getPetById(petId);
//        return getListScheduleDTO(pet.getListSchedule());
        return getListScheduleDTO(scheduleService.getScheduleByPetId(petId));
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
//        Employee emp = employeeService.getEmpById(employeeId);
//        return  getListScheduleDTO(emp.getListSchedule());
        return getListScheduleDTO(scheduleService.getScheduleByEmployeeId(employeeId));
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
//        Customer customer = customerService.getCustomerById(customerId);
//        return getListScheduleDTO(scheduleService.getScheduleByCustomer(customer));
        return getListScheduleDTO(scheduleService.getScheduleByCustomerId(customerId));
    }

    private ScheduleDTO getScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
//         use BeanUtil copy properties
//        BeanUtils.copyProperties(schedule, scheduleDTO);
//        schedule.getListEmp().forEach(employee -> {scheduleDTO.getEmployeeIds().add(employee.getId());});
//        schedule.getListPet().forEach(pet -> {scheduleDTO.getPetIds().add(pet.getId());});
//        schedule.getListEmp().forEach(employee -> {scheduleDTO.getEmployeeIds().add(employee.getId());});
//        schedule.getListPet().forEach(pet -> {scheduleDTO.getPetIds().add(pet.getId());});
        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setEmployeeIds(schedule.getListEmp().stream().map(Employee::getId).collect(Collectors.toList()));
        scheduleDTO.setPetIds(schedule.getListPet().stream().map(Pet::getId).collect(Collectors.toList()));
        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setActivities(schedule.getListEmpSkill());
        return scheduleDTO;
    }

    private List<ScheduleDTO> getListScheduleDTO(List<Schedule> schedules) {
        return schedules
                .stream()
                .map(s -> { return getScheduleDTO(s); })
                .collect(Collectors.toList());
    }
}
