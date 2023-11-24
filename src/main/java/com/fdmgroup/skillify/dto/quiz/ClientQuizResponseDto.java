package com.fdmgroup.skillify.dto.quiz;

import com.fdmgroup.skillify.dto.placement.PlacementResponseDto;

public class ClientQuizResponseDto extends QuizDto{
    private PlacementResponseDto placement;

    public ClientQuizResponseDto() {
        super();
    }

    public PlacementResponseDto getPlacement() {
        return placement;
    }

    public void setPlacement(PlacementResponseDto placement) {
        this.placement = placement;
    }

    @Override
    public String toString() {
        return "ClientQuizResponseDto{" +
                "placement=" + placement +
                '}';
    }
}
