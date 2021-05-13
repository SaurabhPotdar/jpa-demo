package com.cg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.dto.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}
