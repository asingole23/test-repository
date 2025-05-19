package com.cybage.gms.app.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "email") })
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer userId;
	@Column(name = "first_name", length = 20)
	private String firstName;
	@Column(name = "last_name", length = 20)
	private String lastName;
	@Column(name = "username", length = 30)
	private String username;
	@Column(length = 30, unique = true)
	private String email;
	@NotBlank
	@Size(max = 120)
	private String password;
	private LocalDate dob;
	@Column(name = "phone_no", length = 10)
	private String phoneNo;
	@Enumerated(EnumType.STRING)
	private Gender gender;
	@Embedded
	private Address address;
	@ManyToMany(fetch = FetchType.LAZY)
	@JsonIgnoreProperties
	@JsonIgnore
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
	private Boolean isEnabled = true;

	@OneToMany(mappedBy = "citizen", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties("citizen")
	private List<Complain> complains = new ArrayList<>();

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "departmentHead")
	private Department department;
	
//	private List<Notification> notifications = new ArrayList<>();
	
	/*
	 * @Enumerated(EnumType.STRING)
	 * 
	 * @Column(name = "category") private WorkCategory workCategory;
	 * 
	 * @JsonIgnoreProperties("user")
	 * 
	 * @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval =
	 * true) private List<UserWorkHistory> userWork = new ArrayList<>();
	 * 
	 * @JsonIgnoreProperties("userWork")
	 * 
	 * @OneToMany(mappedBy = "userWork", cascade = CascadeType.ALL, orphanRemoval =
	 * true) private List<WorkDescription> workDescription = new ArrayList<>();
	 */

	public User() {
		System.out.println("in user constructor");
	}

	public User(String firstName, String lastName, String username, String email,
			@NotBlank @Size(max = 120) String password, LocalDate dob, String phoneNo, Gender gender, Address address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.password = password;
		this.dob = dob;
		this.phoneNo = phoneNo;
		this.gender = gender;
		this.address = address;
	}

	/*
	 * public void addUserWork(UserWorkHistory c) { userWork.add(c); }
	 * 
	 * public void removeUserWork(UserWorkHistory c) { userWork.remove(c);
	 * c.setUser(null); }
	 * 
	 * public void addUserDescription(WorkDescription c) { workDescription.add(c); }
	 * 
	 * public void removeUserDescription(WorkDescription c) {
	 * workDescription.remove(c); c.setUserWork(null); }
	 */

}
