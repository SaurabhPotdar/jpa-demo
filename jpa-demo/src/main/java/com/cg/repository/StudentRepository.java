package com.cg.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cg.dto.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

	List<Student> findByFirstNameOrLastNameOrderByFirstNameDesc(String firstName, String lastName, Pageable pageable);

	@Query("select s from Student s where s.firstName = :firstName or s.lastName = :lastName")
	Student findByLastnameOrFirstnameNamedQuery(@Param("firstName") String firstname, @Param("lastName") String lastname);
	
	//Create a default method with a descriptive name to avoid using very long jpa query method names
	//Or use @Query with a descriptive method name
	default List<Student> findByName(String firstName, String lastName, int page, int size) {
		return findByFirstNameOrLastNameOrderByFirstNameDesc(firstName, lastName, PageRequest.of(page, size));
	}
	

}
