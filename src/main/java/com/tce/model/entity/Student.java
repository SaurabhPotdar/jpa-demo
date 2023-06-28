package com.tce.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "courses"})
@EntityListeners(AuditingEntityListener.class)
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "student_id")
	private int id;

	@Column(name = "student_firstname")
	private String firstName;
	
	@Column(name = "student_lastname")
	private String lastName;

	@JsonIgnoreProperties("students")
	@ManyToMany(mappedBy = "students")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<Course> courses = new HashSet<>();

	@Column(name = "created_date")
	@CreatedDate
	private LocalDateTime createdDate;

	@Column(name = "last_modified_date")
	@LastModifiedDate
	private LocalDateTime lastModifiedDate;
	
	public Student(String firstName, String lastName, Set<Course> courses) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.courses = courses;
	}

}
