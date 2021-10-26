package com.project.sroa_manage_msa.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class EngineerView {
    private String engineerName;
    private Integer avgScore;
    private Integer amountWork;

    @Builder
    public EngineerView(String engineerName, Integer avgScore, Integer amountWork){
        this.engineerName=engineerName;
        this.avgScore=avgScore;
        this.amountWork=amountWork;
    }
}
