package com.cg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import com.cg.dto.Student;
import com.cg.repository.StudentRepository;

@DataJpaTest
class JpaDemoApplicationTests {
	
	@Autowired
	private StudentRepository studentRepository;
	
	@BeforeEach
	public void init() {
		studentRepository.saveAll(Arrays.asList(
				new Student("AB","CD",null),
				new Student("EF","GH",null),
				new Student("IJ","KL",null),
				new Student("MN","OP",null)));
	}

	@Test
	void testFindAll() {
		Student student = new Student("QR","ST",null);
		assertNotNull(studentRepository.save(student));
		assertEquals(5, studentRepository.count());
	}
	
	@Test
	void testFindByLastnameOrFirstnameNamedQuery() {
		assertEquals(2, studentRepository.findByLastnameOrFirstnameNamedQuery("AB", "GH", PageRequest.of(0, 10)).size());
	}

}
