package com.kchandrakant.learning.jms;

import javax.jms.JMSException;

public class Main {

    public static void main(String[] args) throws JMSException {

        EmployeeDao employeeDao = new EmployeeDao();
        new Thread(() -> {
            try {
                employeeDao.receiveMessage();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }).start();

        employeeDao.sendMessage("Hello World!");
        employeeDao.sendMessage("Hello World2!");

    }
}
