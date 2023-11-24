package com.fdmgroup.skillify.dto.placement;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import com.fdmgroup.skillify.entity.Skill;

public class PlacementRequestDTO {
    private String title;
    private String description;
    private String companyName;
    private String authorEmail;
    private Set<Skill> skillNames; 
    private String expiredDate;
    
    
    
    
    
	public PlacementRequestDTO() {
		super();
	}

	public PlacementRequestDTO(String title, String description, String companyName, String authorEmail,
			Set<Skill> skillNames, String expiredDate) {
		super();
		this.title = title;
		this.description = description;
		this.companyName = companyName;
		this.authorEmail = authorEmail;
		this.skillNames = skillNames;
		this.expiredDate = expiredDate;
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

	public Set<Skill> getSkillNames() {
		return skillNames;
	}

	public void setSkillNames(Set<Skill> skillNames) {
		this.skillNames = skillNames;
	}

	public String getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}

	@Override
	public String toString() {
		return "PlacementRequestDTO [title=" + title + ", description=" + description + ", companyName=" + companyName
				+ ", authorEmail=" + authorEmail + ", skillNames=" + skillNames + ", expiredDate=" + expiredDate + "]";
	} 


}
