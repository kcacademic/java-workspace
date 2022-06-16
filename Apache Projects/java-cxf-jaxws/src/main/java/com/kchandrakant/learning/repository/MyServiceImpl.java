package com.kchandrakant.learning.repository;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.jws.WebService;

import com.kchandrakant.learning.model.Student;

@WebService(endpointInterface = "com.kchandrakant.learning.repository.MyService")
public class MyServiceImpl implements MyService {
    private Map<Integer, Student> students = new LinkedHashMap<Integer, Student>();

    public String hello(String name) {
        return "Hello " + name;
    }

    public String helloStudent(Student student) {
        students.put(students.size() + 1, student);
        return "Hello " + student.getName();
    }

    public Map<Integer, Student> getStudents() {
        return students;
    }
}
