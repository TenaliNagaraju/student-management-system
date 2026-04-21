package com.sms.config;

import com.sms.entity.Student;
import com.sms.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final StudentRepository studentRepository;

    @Autowired
    public DataLoader(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void run(String... args) {
        if (studentRepository.count() == 0) {
            studentRepository.save(new Student("Rahul", "Sharma",
                    "rahul.sharma@email.com", "9876543210",
                    LocalDate.of(2002, 5, 15), "Male",
                    "Computer Science", "3rd Year",
                    "123 Main Street", "Mumbai", "Maharashtra", "400001", "Active"));

            studentRepository.save(new Student("Priya", "Patel",
                    "priya.patel@email.com", "9876543211",
                    LocalDate.of(2003, 8, 22), "Female",
                    "Electronics", "2nd Year",
                    "456 Park Avenue", "Delhi", "Delhi", "110001", "Active"));

            studentRepository.save(new Student("Amit", "Kumar",
                    "amit.kumar@email.com", "9876543212",
                    LocalDate.of(2001, 3, 10), "Male",
                    "Mechanical", "4th Year",
                    "789 Lake Road", "Bangalore", "Karnataka", "560001", "Active"));

            studentRepository.save(new Student("Sneha", "Reddy",
                    "sneha.reddy@email.com", "9876543213",
                    LocalDate.of(2003, 11, 5), "Female",
                    "Computer Science", "2nd Year",
                    "321 Hill View", "Hyderabad", "Telangana", "500001", "Active"));

            studentRepository.save(new Student("Vikram", "Singh",
                    "vikram.singh@email.com", "9876543214",
                    LocalDate.of(2002, 7, 18), "Male",
                    "Civil", "3rd Year",
                    "654 Garden City", "Pune", "Maharashtra", "411001", "Inactive"));

            System.out.println(">>> Sample data loaded: 5 students added.");
        }
    }
}