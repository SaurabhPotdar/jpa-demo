package com.cg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.dto.Course;
import com.cg.repository.CourseRepository;

@RestController
@RequestMapping("/courses")
public class CourseController {
	
	@Autowired
	private CourseRepository courseRepository;
	
	@GetMapping(value="/{id}")
	public ResponseEntity<?> findById(@PathVariable int id) {
		return new ResponseEntity<Course>(courseRepository.findById(id).orElse(null), HttpStatus.OK);
	}
	
	@GetMapping()
	public ResponseEntity<?> findAll() {
		return new ResponseEntity<>(courseRepository.findAll(), HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<?> addCourse(@RequestBody Course course) {
		return new ResponseEntity<>(courseRepository.save(course),HttpStatus.OK);
	}

}
