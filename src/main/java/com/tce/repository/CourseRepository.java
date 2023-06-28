package com.tce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tce.model.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {

}
