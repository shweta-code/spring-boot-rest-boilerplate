package com.intuit.exchange.rest.service;

import com.intuit.exchange.rest.dto.StudentCourse;
import com.intuit.exchange.rest.entity.Course;

public interface CourseService {

    Course getCourseById(Long courseId);

    Course createNewCourse(Course course);

    Course enrollStudentToCourse(StudentCourse studentCourse);

}
