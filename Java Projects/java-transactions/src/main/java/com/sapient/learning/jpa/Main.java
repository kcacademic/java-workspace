package com.sapient.learning.jpa;

import com.sapient.learning.domain.Employee;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Employee employee = new Employee("Tom Sawyer",
                new BigDecimal(799.88), Timestamp.valueOf(LocalDateTime.now()).toLocalDateTime());
        EmployeeDao employeeDao = new EmployeeDao();
        employeeDao.insertRecord(employee);
        List<Employee> employees = employeeDao.getAllEmployees();
        employees.forEach(e -> System.out.println(e));

    }

}
