package com.sapient.learning.jdbc;

import com.sapient.learning.domain.Employee;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {

    private static String CONNECTION_URL = "jdbc:mysql://127.0.0.1:3306/test";
    private static String USER = "root";
    private static String PASSWORD = "SopraBanking";
    private Connection connection;

    public EmployeeDao() throws SQLException {
        connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
    }

    public int insertRecord(Employee employee) throws SQLException {
        String SQL_INSERT = "INSERT INTO EMPLOYEE (NAME, SALARY, CREATED_DATE) VALUES (?,?,?)";
        int row = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setBigDecimal(2, employee.getSalary());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(employee.getCreatedDate()));
            row = preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        return row;
    }

    public List<Employee> getAllEmployees() {
        String SQL_SELECT = "Select * from EMPLOYEE";
        List<Employee> result = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                BigDecimal salary = resultSet.getBigDecimal("SALARY");
                Timestamp createdDate = resultSet.getTimestamp("CREATED_DATE");
                Employee employee = new Employee();
                employee.setId(id);
                employee.setName(name);
                employee.setSalary(salary);
                employee.setCreatedDate(createdDate.toLocalDateTime());
                result.add(employee);
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        return result;
    }
}
