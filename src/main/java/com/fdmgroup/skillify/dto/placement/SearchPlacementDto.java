package com.fdmgroup.skillify.dto.placement;

import java.util.List;
import java.util.UUID;

import lombok.Data;

public class SearchPlacementDto {
 	private UUID id;
    private String title;
    private String description;
    private String companyName;
    private String authorEmail;
    private String authorFirstName;
    private String authorLastName;
    private List<String> skillNames;
    private String expiredDate;
    
	public SearchPlacementDto() {
		super();
	}

	public SearchPlacementDto(UUID id, String title, String description, String companyName, String authorEmail, String authorFirstname, String authorLastName,
			List<String> skillNames) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.companyName = companyName;
		this.authorEmail = authorEmail;
		this.skillNames = skillNames;
		this.authorFirstName = authorFirstname;
		this.authorLastName = authorLastName;
	}

	
	public String getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(String expiredDate) {
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

	public String getAuthorEmail() {
		return authorEmail;
	}

	public void setAuthorEmail(String authorEmail) {
		this.authorEmail = authorEmail;
	}
	
	public String getAuthorFirstName() {
		return authorFirstName;
	}

	public void setAuthorFirstName(String authorFirstName) {
		this.authorFirstName = authorFirstName;
	}

	public String getAuthorLastName() {
		return authorLastName;
	}

	public void setAuthorLastName(String authorLastName) {
		this.authorLastName = authorLastName;
	}

	public List<String> getSkillNames() {
		return skillNames;
	}

	public void setSkillNames(List<String> skillNames) {
		this.skillNames = skillNames;
	}

	public SearchPlacementDto(UUID id, String title, String description, String companyName, String authorEmail,
			String authorFirstName, String authorLastName, List<String> skillNames, String expiredDate) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.companyName = companyName;
		this.authorEmail = authorEmail;
		this.authorFirstName = authorFirstName;
		this.authorLastName = authorLastName;
		this.skillNames = skillNames;
		this.expiredDate = expiredDate;
	}
	
}
