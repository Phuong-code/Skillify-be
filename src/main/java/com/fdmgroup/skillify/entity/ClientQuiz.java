package com.fdmgroup.skillify.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "client_quiz")
public class ClientQuiz extends Quiz{
    @ManyToOne
    @JoinColumn(name = "placement_id", nullable = false)
    private Placement placement;
   
	public ClientQuiz() {
		super();
	}
	
	public ClientQuiz(Placement placement) {
		super();
		this.placement = placement;
	}

	public Placement getPlacement() {
		return placement;
	}

	public void setPlacement(Placement placement) {
		this.placement = placement;
	}

	@Override
	public String toString() {
		return "ClientQuiz [placement=" + placement + "]";
	}
	
}
