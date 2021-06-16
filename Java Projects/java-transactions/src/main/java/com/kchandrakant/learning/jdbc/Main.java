package com.kchandrakant.learning.jdbc;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import com.kchandrakant.learning.domain.Employee;

public class Main {

    public static void main(String[] args) throws SQLException {
        Employee employee = new Employee("Tom Sawyer",
                new BigDecimal(799.88), Timestamp.valueOf(LocalDateTime.now()).toLocalDateTime());
        EmployeeDao employeeDao = new EmployeeDao();
        int records = employeeDao.insertRecord(employee);
        System.out.println("Number of records inserted: " + records);
        List<Employee> employees = employeeDao.getAllEmployees();
        employees.forEach(e -> System.out.println(e));
    }

}