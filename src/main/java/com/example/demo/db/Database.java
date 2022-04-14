package com.example.demo.db;

import com.example.demo.models.Employee;
import com.example.demo.repositories.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Database {
    private static final Logger logger = LoggerFactory.getLogger(Database.class);

    //    @Bean
    CommandLineRunner initDatabase(EmployeeRepository employeeRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Employee e1 = new Employee("name1");
                Employee e2 = new Employee("name2");

                logger.info("insert data " + employeeRepository.save(e1));
                logger.info("insert data " + employeeRepository.save(e2));
            }
        };
    }
}
