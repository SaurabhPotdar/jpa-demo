package com.cg;

import com.cg.constants.Constants;
import com.cg.dto.Employee;
import com.cg.dto.EmployeeView;
import com.cg.dto.Student;
import com.cg.repository.EmployeeRepository;
import com.cg.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Slf4j
class JpaDemoApplicationTests {

	@Nested
	@DisplayName("Student Tests")
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	class StudentTests {

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

		@AfterEach
		public void delete() {
			studentRepository.deleteAll();
		}

		@Test
		void testFindAll() {
			Student student = new Student("QR","ST",null);
			assertNotNull(studentRepository.save(student));
			assertEquals(5, studentRepository.count());
		}

		@Test
		void testFindByLastnameOrFirstnameNamedQuery() {
			assertEquals(4, studentRepository.count());  //Each test is transactional and rollback
			assertEquals(2, studentRepository.findByLastnameOrFirstnameNamedQuery("AB", "GH", PageRequest.of(0, 10)).size());
		}

		@Test
		void testFindByName() {
			assertEquals(4, studentRepository.count());  //Each test is transactional and rollback
			assertEquals(2, studentRepository.findByName("AB", "GH", 0, 10).size());
		}

	}
	
	@Nested
	@DisplayName("Employee Tests")
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	class EmployeeTests {

		@Autowired
		private EmployeeRepository employeeRepository;
		private Pageable pageable;

		@BeforeAll
		void init() {
			pageable = Pageable.unpaged();
			employeeRepository.saveAll(Arrays.asList(
					new Employee(1, "John", "Doe", "Manager", 100000L),
					new Employee(2, "Jane", "Doe", "Developer", 200000L),
					new Employee(3, "Jack", "Jones", "Developer", 300000L),
					new Employee(4, "Jill", "Smith", "Manager", 400000L)));
		}

		private List<Employee> getEmployees(final Map<String, String> filters) {
			return employeeRepository.findAll(EmployeeRepository.FiltersUtils.findBy(filters), pageable).getContent();
		}

		@Test
		@DisplayName("Find by name")
		void testFindByName() {
			final Map<String, String> filters = new HashMap<>();
			filters.put(Constants.FIRST_NAME, "John");
			final List<Employee> employees = getEmployees(filters);
			assertEquals(1, employees.size());
		}

		@Test
		@DisplayName("Find by designation and salary")
		void testFindAll() {
			final Map<String, String> filters = new HashMap<>();
			filters.put(Constants.DESIGNATION, "Developer");
			filters.put(Constants.SALARY, "200000");
			final List<Employee> employees = getEmployees(filters);
			assertEquals(2, employees.size());
		}

		@Test
		@DisplayName("Testing projection")
		void testProjection() {
			final List<EmployeeView> employees = employeeRepository.findByFirstName("John");
			employees.forEach(employee -> log.info("Employee Projection: name = {}, designation = {}", employee.getFullName(), employee.getDesignation()));
			assertEquals(1, employees.size());
		}

	}


}
