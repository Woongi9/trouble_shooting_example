package com.trouble_shooting.jpa_context.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "USER")
public class User extends ParentEntity {

	@Column(name = "name", length = 10)
	private String name;

	@Column(name = "phone", length = 20)
	private String phone;

	@Builder
	public User(String name, String phone) {
		this.name = name;
		this.phone = phone;
	}
}
