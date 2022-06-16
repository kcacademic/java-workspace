package com.kchandrakant.learning.repository;

import java.util.Map;

import javax.jws.WebService;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.kchandrakant.learning.model.Student;
import com.kchandrakant.learning.model.StudentMapAdapter;

@WebService
public interface MyService {
    public String hello(String name);

    public String helloStudent(Student student);

    @XmlJavaTypeAdapter(StudentMapAdapter.class)
    public Map<Integer, Student> getStudents();
}
