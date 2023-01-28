package com.user.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.user.auditables.Auditable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User extends Auditable{

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private long id;
	@Column(unique = true,length = 40)
	@Size(min=5, message = "Name must contain atleast 5 characters")
	@NotBlank(message = "name should not be empty")
	private String name;
	private String password;
	@Column(unique = true,length = 40)
	@Email(message = "Enter valid email")
	@NotBlank(message = "Email should not be empty")
	private String email;
	private boolean deleteStatus;
	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinTable(name="userRoles",joinColumns = @JoinColumn(name="userId"),inverseJoinColumns = @JoinColumn(name="roleId"))
	private List <Roles> roles;
	@OneToOne(fetch=FetchType.EAGER,cascade =CascadeType.ALL)
	@JoinColumn(name="userImage")
	private Attachments attachments;
}
