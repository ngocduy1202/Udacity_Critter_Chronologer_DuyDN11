package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.repositories.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

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
        listEmp.stream().filter(emp -> emp.getListSkills().containsAll(skills));
        return listEmp;
    }
}
