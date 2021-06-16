package com.kchandrakant.learning.jpa;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import com.kchandrakant.learning.domain.Employee;

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
