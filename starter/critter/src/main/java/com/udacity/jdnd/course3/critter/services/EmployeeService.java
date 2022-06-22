package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.repositories.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public void saveEmployee(Employee employee){
        employeeRepository.save(employee);
    }

    public Employee getEmpById(Long id){
        return employeeRepository.getOne(id);
    }

    public void setAvailibleDayEmp(Set<DayOfWeek> days, long empId){
        Employee employee = employeeRepository.getOne(empId);
        employee.setListDayOfWeek(days);
        employeeRepository.save(employee);
    }

    public List<Employee> getEmpBySkills(LocalDate date, Set<EmployeeSkill> skills){
        List<Employee> listEmp = employeeRepository.findAll();
        List<Employee> listEmpSkill = new ArrayList<>();
        for (Employee emp: listEmp) {
            if(emp.getListSkills().containsAll(skills)){
                listEmpSkill.add(emp);
            }
        }
        //listEmp.stream().filter(emp -> emp.getListSkills().containsAll(skills));
        return listEmpSkill;


    }


}
