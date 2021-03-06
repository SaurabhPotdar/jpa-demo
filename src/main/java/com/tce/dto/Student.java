package com.tce.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "courses"})
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
	
	public Student(String firstName, String lastName, Set<Course> courses) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.courses = courses;
	}

}
