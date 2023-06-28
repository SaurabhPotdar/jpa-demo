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
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "students"})
@EntityListeners(AuditingEntityListener.class)
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "course_id")
	private int id;

	@Column(name = "course_title")
	private String title;
	
	@ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JsonIgnoreProperties("courses")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	//Create a new table "student_course" with one colm from Course and one from Student
	//This table will keep track of many to many
	@JoinTable(name="course_student", joinColumns = {@JoinColumn(name="course_id")}, inverseJoinColumns = {@JoinColumn(name="student_id")})
	private Set<Student> students = new HashSet<>();

	@Column(name = "created_date")
	@CreatedDate
	private LocalDateTime createdDate;

	@Column(name = "last_modified_date")
	@LastModifiedDate
	private LocalDateTime lastModifiedDate;

}