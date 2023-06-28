package com.tce.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private int id;
	
	private String description;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@Column(name = "created_date")
	@CreatedDate
	private LocalDateTime createdDate;

	@Column(name = "last_modified_date")
	@LastModifiedDate
	private LocalDateTime lastModifiedDate;

}
