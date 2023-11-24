package com.fdmgroup.skillify.dto.placement;

import java.util.List;
import java.util.UUID;

import lombok.Data;

public class PlacementResponseDto {
 	private UUID id;
    private String title;
    private String description;
    private String companyName;
    private String authorEmail;
    private List<String> skillNames;
    private String expiredDate;
    
	public PlacementResponseDto() {
		super();
	}

	public PlacementResponseDto(UUID id, String title, String description, String companyName, String authorEmail,
			List<String> skillNames) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.companyName = companyName;
		this.authorEmail = authorEmail;
		this.skillNames = skillNames;
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

	public List<String> getSkillNames() {
		return skillNames;
	}

	public void setSkillNames(List<String> skillNames) {
		this.skillNames = skillNames;
	}

	@Override
	public String toString() {
		return "PlacementResponseDto [id=" + id + ", title=" + title + ", description=" + description + ", companyName="
				+ companyName + ", authorEmail=" + authorEmail + ", skillNames=" + skillNames + "]";
	} 
	
}
