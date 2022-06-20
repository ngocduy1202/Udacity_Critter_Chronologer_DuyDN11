package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.services.CustomerService;
import com.udacity.jdnd.course3.critter.services.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = new Customer();
        //use BeanUtil copy properties for faster :D
        //BeanUtils.copyProperties(customerDTO,customer);
        customer.setName(customerDTO.getName());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setNotes(customerDTO.getNotes());
        List<Long> petIds = customerDTO.getPetIds();
        return getCustomerDTO(customerService.saveCustomer(customer, petIds));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> listCustomer = customerService.getAllCustomer();
        return listCustomer.stream().map(this::getCustomerDTO).collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Customer customer = customerService.getCustomerByPetId(petId);
        CustomerDTO customerDTO = getCustomerDTO(customer);
        return customerDTO;

    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee emp = new Employee();
        emp.setName(employeeDTO.getName());
        emp.setListSkills(employeeDTO.getSkills());
        emp.setListDayOfWeek(employeeDTO.getDaysAvailable());
        employeeService.saveEmployee(emp);
        return getEmployeeDTO(emp);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmpById(employeeId);
        return getEmployeeDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setAvailibleDayEmp(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> listEmp = employeeService.getEmpBySkills(employeeDTO.getDate(), employeeDTO.getSkills());
        return listEmp.stream().map(this::getEmployeeDTO).collect(Collectors.toList());
    }

    private CustomerDTO getCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setNotes(customer.getNotes());
        List<Long> petIds = customer.getListPets().stream().map(Pet::getId).collect(Collectors.toList());
        customerDTO.setPetIds(petIds);
        return customerDTO;
    }

    private EmployeeDTO getEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSkills(employee.getListSkills());
        employeeDTO.setDaysAvailable(employee.getListDayOfWeek());
        return employeeDTO;
    }

}
