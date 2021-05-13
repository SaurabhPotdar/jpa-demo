package com.cg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cg.dto.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

	Student findByFirstNameOrLastName(String firstName, String lastName);

	@Query("select s from Student s where s.firstName = :firstName or s.lastName = :lastName")
	Student findByLastnameOrFirstnameNamedQuery(@Param("firstName") String firstname, @Param("lastName") String lastname);
	
	//Create a default method with a descriptive name to avoid using very long jpa query method names
	//Or use @Query with a descriptive method name
	default Student findByName(String firstName, String lastName) {
		return findByFirstNameOrLastName(firstName, lastName);
	}

}
