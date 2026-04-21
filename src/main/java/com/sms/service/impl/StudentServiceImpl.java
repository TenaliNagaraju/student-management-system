package com.sms.service.impl;

import com.sms.entity.Student;
import com.sms.repository.StudentRepository;
import com.sms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Long id, Student studentDetails) {
        Student student = getStudentById(id);
        student.setFirstName(studentDetails.getFirstName());
        student.setLastName(studentDetails.getLastName());
        student.setEmail(studentDetails.getEmail());
        student.setPhone(studentDetails.getPhone());
        student.setDateOfBirth(studentDetails.getDateOfBirth());
        student.setGender(studentDetails.getGender());
        student.setDepartment(studentDetails.getDepartment());
        student.setYearOfStudy(studentDetails.getYearOfStudy());
        student.setAddress(studentDetails.getAddress());
        student.setCity(studentDetails.getCity());
        student.setState(studentDetails.getState());
        student.setZipCode(studentDetails.getZipCode());
        student.setStatus(studentDetails.getStatus());
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = getStudentById(id);
        studentRepository.delete(student);
    }

    @Override
    public List<Student> searchStudents(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllStudents();
        }
        return studentRepository.searchStudents(keyword.trim());
    }

    @Override
    public List<Student> getStudentsByDepartment(String department) {
        return studentRepository.findByDepartment(department);
    }

    @Override
    public List<Student> getStudentsByStatus(String status) {
        return studentRepository.findByStatus(status);
    }

    @Override
    public boolean isEmailExists(String email) {
        return studentRepository.existsByEmail(email);
    }

    @Override
    public boolean isEmailExistsForOther(String email, Long id) {
        Optional<Student> student = studentRepository.findByEmail(email);
        return student.isPresent() && !student.get().getId().equals(id);
    }

    @Override
    public long getTotalStudents() {
        return studentRepository.count();
    }

    @Override
    public long getActiveStudents() {
        return studentRepository.countByStatus("Active");
    }

    @Override
    public long getInactiveStudents() {
        return studentRepository.countByStatus("Inactive");
    }

    @Override
    public Map<String, Long> getStudentsByDepartmentCount() {
        List<Student> allStudents = studentRepository.findAll();
        return allStudents.stream()
                .collect(Collectors.groupingBy(Student::getDepartment, Collectors.counting()));
    }
}