package com.project.sroa_manage_msa.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CenterView {
    private String centerName;
    private Integer engineerCnt;

    @Builder
    public CenterView(String centerName, Integer engineerCnt){
        this.centerName=centerName;
        this.engineerCnt=engineerCnt;
    }
}
