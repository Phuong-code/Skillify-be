package com.fdmgroup.skillify.entity;

import com.fdmgroup.skillify.enums.RoleType;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;


@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ColumnDefault("false")
    @Column(name = "authenticated", nullable = false)
    private boolean authenticated;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "role", nullable = false)
    private RoleType role;

    
	public AppUser() {
		super();
	}

	public AppUser(String email, String password, String firstName, String lastName, boolean authenticated,
			RoleType role) {
		super();
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.authenticated = authenticated;
		this.role = role;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

	public RoleType getRole() {
		return role;
	}

	public void setRole(RoleType role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "AppUser [id=" + id + ", email=" + email + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", authenticated=" + authenticated + ", role=" + role + "]";
	}
    
}
