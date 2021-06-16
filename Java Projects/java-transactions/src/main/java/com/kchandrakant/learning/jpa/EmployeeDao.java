package com.kchandrakant.learning.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.kchandrakant.learning.domain.Employee;

import java.util.List;

public class EmployeeDao {

    private EntityManagerFactory entityManagerFactory;

    public EmployeeDao() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpa-example");
    }

    public void insertRecord(Employee employee) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(employee);
            entityManager.getTransaction().commit();
        } catch (Exception e){
            entityManager.getTransaction().rollback();
        }
    }

    public List<Employee> getAllEmployees() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.createQuery("Select e from Employee e", Employee.class).getResultList();
    }

}
