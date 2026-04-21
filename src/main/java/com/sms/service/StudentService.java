package com.sms.service;

import com.sms.entity.Student;

import java.util.List;
import java.util.Map;

public interface StudentService {

    List<Student> getAllStudents();

    Student getStudentById(Long id);

    Student saveStudent(Student student);

    Student updateStudent(Long id, Student student);

    void deleteStudent(Long id);

    List<Student> searchStudents(String keyword);

    List<Student> getStudentsByDepartment(String department);

    List<Student> getStudentsByStatus(String status);

    boolean isEmailExists(String email);

    boolean isEmailExistsForOther(String email, Long id);

    long getTotalStudents();

    long getActiveStudents();

    long getInactiveStudents();

    Map<String, Long> getStudentsByDepartmentCount();
}