package com.intuit.exchange.rest.service.impl;

import java.util.Optional;

import com.intuit.exchange.rest.dto.StudentLocation;
import com.intuit.exchange.rest.exception.ResourceNotFoundException;
import com.intuit.exchange.rest.entity.Student;
import com.intuit.exchange.rest.repository.StudentRepository;
import com.intuit.exchange.rest.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public StudentLocation getStudentLocation(Long id) {
        Optional<StudentLocation> studentLocation = studentRepository.findStudentLocationById(id);
        if (!studentLocation.isPresent()) {
            throw new ResourceNotFoundException("Student Location", "id", id.toString());
        }
        return studentLocation.get();
    }

    @Override
    public Student getStudentById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (!student.isPresent()) {
            throw new ResourceNotFoundException("Student", "id", id.toString());
        }
        return student.get();
    }

    @Override
    public Student createNewStudent(Student student) {
        try {
            return studentRepository.save(student);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
