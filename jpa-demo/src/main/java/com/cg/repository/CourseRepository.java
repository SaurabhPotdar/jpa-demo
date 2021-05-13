package com.cg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.dto.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {

}
