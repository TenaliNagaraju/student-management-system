package com.sms.controller;

import com.sms.entity.Student;
import com.sms.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // ======================== DASHBOARD / HOME ========================

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("totalStudents", studentService.getTotalStudents());
        model.addAttribute("activeStudents", studentService.getActiveStudents());
        model.addAttribute("inactiveStudents", studentService.getInactiveStudents());
        model.addAttribute("departmentCounts", studentService.getStudentsByDepartmentCount());
        model.addAttribute("recentStudents", studentService.getAllStudents());
        return "redirect:/students";
    }

    // ======================== LIST ALL STUDENTS ========================

    @GetMapping("/students")
    public String listStudents(Model model,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "department", required = false) String department,
                               @RequestParam(value = "status", required = false) String status) {

        List<Student> students;

        if (keyword != null && !keyword.trim().isEmpty()) {
            students = studentService.searchStudents(keyword);
            model.addAttribute("keyword", keyword);
        } else if (department != null && !department.trim().isEmpty()) {
            students = studentService.getStudentsByDepartment(department);
            model.addAttribute("selectedDepartment", department);
        } else if (status != null && !status.trim().isEmpty()) {
            students = studentService.getStudentsByStatus(status);
            model.addAttribute("selectedStatus", status);
        } else {
            students = studentService.getAllStudents();
        }

        model.addAttribute("students", students);
        model.addAttribute("totalStudents", studentService.getTotalStudents());
        model.addAttribute("activeStudents", studentService.getActiveStudents());
        model.addAttribute("inactiveStudents", studentService.getInactiveStudents());
        model.addAttribute("departmentCounts", studentService.getStudentsByDepartmentCount());
        return "students";
    }

    // ======================== CREATE STUDENT ========================

    @GetMapping("/students/new")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("pageTitle", "Add New Student");
        return "create_student";
    }

    @PostMapping("/students")
    public String saveStudent(@Valid @ModelAttribute("student") Student student,
                              BindingResult result,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        // Check for email uniqueness
        if (studentService.isEmailExists(student.getEmail())) {
            result.rejectValue("email", "error.student",
                    "A student with this email already exists");
        }

        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Add New Student");
            return "create_student";
        }

        studentService.saveStudent(student);
        redirectAttributes.addFlashAttribute("successMessage",
                "Student '" + student.getFullName() + "' has been added successfully!");
        return "redirect:/students";
    }

    // ======================== VIEW STUDENT ========================

    @GetMapping("/students/{id}")
    public String viewStudent(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        model.addAttribute("pageTitle", "Student Details");
        return "view_student";
    }

    // ======================== EDIT STUDENT ========================

    @GetMapping("/students/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        model.addAttribute("pageTitle", "Edit Student");
        return "edit_student";
    }

    @PostMapping("/students/{id}")
    public String updateStudent(@PathVariable Long id,
                                @Valid @ModelAttribute("student") Student student,
                                BindingResult result,
                                RedirectAttributes redirectAttributes,
                                Model model) {

        // Check for email uniqueness (excluding current student)
        if (studentService.isEmailExistsForOther(student.getEmail(), id)) {
            result.rejectValue("email", "error.student",
                    "A student with this email already exists");
        }

        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Edit Student");
            student.setId(id);
            return "edit_student";
        }

        studentService.updateStudent(id, student);
        redirectAttributes.addFlashAttribute("successMessage",
                "Student '" + student.getFullName() + "' has been updated successfully!");
        return "redirect:/students";
    }

    // ======================== DELETE STUDENT ========================

    @GetMapping("/students/{id}/delete")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Student student = studentService.getStudentById(id);
        String name = student.getFullName();
        studentService.deleteStudent(id);
        redirectAttributes.addFlashAttribute("successMessage",
                "Student '" + name + "' has been deleted successfully!");
        return "redirect:/students";
    }

    // ======================== ERROR HANDLING ========================

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "redirect:/students";
    }
}