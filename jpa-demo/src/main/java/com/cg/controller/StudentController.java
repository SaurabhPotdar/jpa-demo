package com.cg.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.dto.Course;
import com.cg.dto.Student;
import com.cg.repository.CourseRepository;
import com.cg.repository.StudentRepository;

@RestController
@RequestMapping("/students")
public class StudentController {

	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private CourseRepository courseRepository;

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> findById(@PathVariable int id) {
		return new ResponseEntity<>(studentRepository.findById(id).orElse(null), HttpStatus.OK);
	}

	@GetMapping(value = "")
	public ResponseEntity<?> findAll() {
		return new ResponseEntity<>(studentRepository.findAll(), HttpStatus.OK);
	}

	@PostMapping(value = "")
	public ResponseEntity<?> addStudent(@RequestBody Student student) {
		return new ResponseEntity<>(studentRepository.save(student), HttpStatus.OK);
	}

	@PostMapping(value = "/{studentId}/courses/{courseId}")
	public ResponseEntity<?> addCourseToStudent(@PathVariable("studentId") int studentId, @PathVariable("courseId") int courseId) {
		Student student = studentRepository.findById(studentId).orElse(null);
		Course course = courseRepository.findById(courseId).orElse(null);
		Set<Student> students = course.getStudents();
		students.add(student);
		course.setStudents(students);
		//Cascade is on course
		return new ResponseEntity<>(courseRepository.save(course), HttpStatus.OK);
	}
	
	@GetMapping(value = "/search")
	public ResponseEntity<?> findByName(@RequestParam String firstName, @RequestParam String lastName) {
		//return new ResponseEntity<>(studentRepository.findByLastnameOrFirstnameNamedQuery(firstName, lastName), HttpStatus.OK);
		return new ResponseEntity<>(studentRepository.findByName(firstName, lastName), HttpStatus.OK);
	}

}
