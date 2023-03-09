package ru.fortushin.tvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fortushin.tvc.model.Employee;
import ru.fortushin.tvc.repository.EmployeeRepository;
import ru.fortushin.tvc.util.EmployeeNotFoundException;


import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getEmployeeList() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(long id) {
        Optional<Employee> employeeToBeFound = employeeRepository.findById(id);
        return employeeToBeFound.orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
    }

    public Employee getEmployeeByName(String employeeName) {
        Optional<Employee> employeeToBeFound = employeeRepository.findByName(employeeName);
        return employeeToBeFound.orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
    }

    @Transactional
    public void updateEmployee(long id, Employee changedEmployee) {
        changedEmployee.setId(id);
        employeeRepository.save(changedEmployee);
    }

    @Transactional
    public void deleteEmployee(Employee employeeToBeDeleted) {
        employeeRepository.delete(employeeToBeDeleted);
    }

    @Transactional
    public void createEmployee(Employee employeeToBeSaved) {
        employeeRepository.save(employeeToBeSaved);
    }


}
