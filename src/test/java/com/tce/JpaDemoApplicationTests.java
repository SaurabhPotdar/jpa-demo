package com.tce;

import com.tce.constants.Constants;
import com.tce.dto.Employee;
import com.tce.dto.EmployeeView;
import com.tce.dto.Student;
import com.tce.repository.EmployeeRepository;
import com.tce.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JpaDemoApplicationTests {

	@Nested
	@DisplayName("Student Tests")
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	class StudentTests {

		@Autowired
		private StudentRepository studentRepository;

		@BeforeAll
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
			//In the previous tests, we added an entry to the table.
			//But here the count is still 4, as each test is transactional and rolls back.
			assertEquals(4, studentRepository.count());
			assertEquals(2, studentRepository.findByLastnameOrFirstnameNamedQuery("AB", "GH", PageRequest.of(0, 10)).size());
		}

		@Test
		void testFindByName() {
			assertEquals(4, studentRepository.count());
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

		@Test
		@DisplayName("Testing find by designation")
		void testFindByDesignation() {
			final List<String> names = employeeRepository.findByDesignation("");
			//Returns an empty list if no data found.
			names.stream().map(employee -> employee.toUpperCase(Locale.ROOT)).forEach(log::info);
			assertEquals(0, employeeRepository.findByDesignation("Developer").size());
		}

		@Test
		@DisplayName("Testing pagination with JPQL")
		void testPaginationWithJpql() {
			final List<String> names = employeeRepository.findBySalaryPaginated(100000L, PageRequest.of(0, 2));
			assertEquals(2, names.size());
		}

		@Test
		@DisplayName("Testing pagination with Native query")
		void testPaginationWithNative() {
			final List<Employee> employees = employeeRepository.findBySalaryPaginatedNative(100000L, PageRequest.of(0, 2));
			assertEquals(2, employees.size());
		}

	}


}
