package com.fdmgroup.skillify.dto.placement;

import java.util.Set;
import java.sql.Timestamp;
import com.fdmgroup.skillify.entity.Skill;

import java.util.List;

import lombok.Data;

public class UpdatedPlacementDto {
    private String title;
    private String description;
    private String companyName;
    private Set<Skill> skillNames; 
    private Timestamp expiredDate;
    
	public UpdatedPlacementDto() {
		super();
	}

	public UpdatedPlacementDto(String title, String description, String companyName, Set<Skill> skillNames,
			Timestamp expiredDate) {
		super();
		this.title = title;
		this.description = description;
		this.companyName = companyName;
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

	public Set<Skill> getSkillNames() {
		return skillNames;
	}

	public void setSkillNames(Set<Skill> skillNames) {
		this.skillNames = skillNames;
	}

	public Timestamp getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Timestamp expiredDate) {
		this.expiredDate = expiredDate;
	}

	@Override
	public String toString() {
		return "UpdatedPlacementDto [title=" + title + ", description=" + description + ", companyName=" + companyName
				+ ", skillNames=" + skillNames + ", expiredDate=" + expiredDate + "]";
	} 
    
}
