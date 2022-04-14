package com.example.demo.controllers;

import com.example.demo.models.Employee;
import com.example.demo.models.ResponseObject;
import com.example.demo.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/Employees")
public class EmployeeController {
    @Autowired
    private EmployeeRepository repository;

    @GetMapping("")
    List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<Employee> employee = repository.findById(id);
        return employee.isPresent() ?
                ResponseEntity
                        .status(HttpStatus.OK)
                        .body(new ResponseObject("ok", "query employee successfully", employee)) :
                ResponseEntity
                        .status(HttpStatus.UNPROCESSABLE_ENTITY)
                        .body(new ResponseObject("false", "cannot find employee with id = " + id, ""));
    }

    @PostMapping("")
    ResponseEntity<ResponseObject> insertEmployee(@RequestBody Employee newEmployee) {
        List<Employee> employees = repository.findByName(newEmployee.getName().trim());

        if (employees.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("ok", "same name", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "insert employee successfully", repository.save(newEmployee))
        );
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
        Employee employee = repository.findById(id).map(e -> {
            e.setName(newEmployee.getName());
            return repository.save(e);
        }).orElseGet(() -> {
            return repository.save(newEmployee);
        });

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "success", employee)
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteEmployee(@PathVariable Long id) {
        boolean exists = repository.existsById(id);

        if (exists) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "success", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("fail", "not have employee", "")
            );
        }
    }
}
