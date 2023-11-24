package com.fdmgroup.skillify.entity;

import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "placement")
public class Placement {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false, length = 2000)
    @Lob
    private String description;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private AppUser author;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "placement_skill",
            joinColumns = @JoinColumn(name = "placement_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skills;
    
	@OneToMany(mappedBy = "placement", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<ClientQuiz> clientQuizs;

    @CreationTimestamp
    @Column(name = "created_date", nullable = false)
    private Timestamp createdDate;

    @Column(name = "expired_date")
    private Timestamp expiredDate;

	public Placement() {
		super();
	}

	public Placement(String title, String description, String companyName, AppUser author, Set<Skill> skills,
			Timestamp createdDate, Timestamp expiredDate) {
		super();
		this.title = title;
		this.description = description;
		this.companyName = companyName;
		this.author = author;
		this.skills = skills;
		this.createdDate = createdDate;
		this.expiredDate = expiredDate;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public AppUser getAuthor() {
		return author;
	}

	public void setAuthor(AppUser author) {
		this.author = author;
	}

	public Set<Skill> getSkills() {
		return skills;
	}

	public void setSkills(Set<Skill> skills) {
		this.skills = skills;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Timestamp expiredDate) {
		this.expiredDate = expiredDate;
	}

	@Override
	public String toString() {
		return "Placement [id=" + id + ", title=" + title + ", description=" + description + ", companyName="
				+ companyName + ", author=" + author + ", skills=" + skills + ", createdDate=" + createdDate
				+ ", expiredDate=" + expiredDate + "]";
	}
    
}
