package com.sapient.learning.jta;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.atomikos.jms.AtomikosConnectionFactoryBean;
import com.sapient.learning.domain.Employee;
import org.apache.activemq.ActiveMQXAConnectionFactory;

import javax.jms.*;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;
import java.math.BigDecimal;
import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmployeeDao {

    private static String DB_CONNECTION_URL = "jdbc:mysql://127.0.0.1:3306/test";
    private static String DB_USER = "root";
    private static String DB_PASSWORD = "SopraBanking";

    private static String MB_CONNECTION_URL = "tcp://localhost:61616";

    private DataSource dataSource;
    private ConnectionFactory connectionFactory;
    private UserTransaction userTransaction;

    public EmployeeDao() {
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        Properties properties = new Properties();
        properties.setProperty("user", DB_USER);
        properties.setProperty("password", DB_PASSWORD);
        properties.setProperty("URL", DB_CONNECTION_URL);
        atomikosDataSourceBean.setXaDataSourceClassName("com.mysql.cj.jdbc.MysqlXADataSource");
        atomikosDataSourceBean.setXaProperties(properties);
        atomikosDataSourceBean.setUniqueResourceName("test");
        atomikosDataSourceBean.setPoolSize(10); // optional
        atomikosDataSourceBean.setBorrowConnectionTimeout(10); // optional
        dataSource = atomikosDataSourceBean;

        XAConnectionFactory xaConnectionFactory = new ActiveMQXAConnectionFactory(MB_CONNECTION_URL);
        AtomikosConnectionFactoryBean atomikosConnectionFactoryBean = new AtomikosConnectionFactoryBean();
        atomikosConnectionFactoryBean.setUniqueResourceName("activemq");
        atomikosConnectionFactoryBean.setXaConnectionFactory(xaConnectionFactory);
        atomikosConnectionFactoryBean.setPoolSize(5);
        connectionFactory = atomikosConnectionFactoryBean;

        userTransaction = new UserTransactionImp();
    }

    public int insertRecord(Employee employee) {
        int row = 0;
        try {
            userTransaction.begin();
            Connection dbConnection = dataSource.getConnection();
            String SQL_INSERT = "INSERT INTO EMPLOYEE (NAME, SALARY, CREATED_DATE) VALUES (?,?,?)";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(SQL_INSERT);
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setBigDecimal(2, employee.getSalary());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(employee.getCreatedDate()));
            row = preparedStatement.executeUpdate();

            javax.jms.Connection mbConnection = connectionFactory.createConnection();
            Session session = mbConnection.createSession(true, 0);
            Destination destination = session.createTopic("TEST.FOO");
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            String text = employee.toString() + " From: " + Thread.currentThread().getName() + " : " + this.hashCode();
            TextMessage textMessage = session.createTextMessage(text);
            System.out.println("Sent message: " + employee.toString());
            producer.send(textMessage);
            userTransaction.commit();
        } catch (Exception e) {
            try {
                userTransaction.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        return row;
    }

    public List<Employee> getAllEmployees() throws SQLException {
        String SQL_SELECT = "Select * from EMPLOYEE";
        List<Employee> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(DB_CONNECTION_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement(SQL_SELECT);
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
        } finally {
            preparedStatement.close();
            connection.close();
        }
        return result;
    }

    public void receiveMessage() throws JMSException {
        Session session = null;
        Destination destination = null;
        MessageConsumer consumer = null;
        try {
            userTransaction.begin();
            javax.jms.Connection connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            destination = session.createTopic("TEST.FOO");
            consumer = session.createConsumer(destination);
            while (true) {
                Message message = consumer.receive(1000);
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    String text = textMessage.getText();
                    System.out.println("Received: " + text);
                }
            }
        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        } finally {
            consumer.close();
            session.close();
        }
    }

}
