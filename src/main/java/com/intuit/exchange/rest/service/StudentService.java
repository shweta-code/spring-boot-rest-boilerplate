package com.intuit.exchange.rest.service;

import com.intuit.exchange.rest.dto.StudentLocation;
import com.intuit.exchange.rest.entity.Student;

public interface StudentService {

    StudentLocation getStudentLocation(Long id);

    Student getStudentById(Long id);

    Student createNewStudent(Student student);
}
