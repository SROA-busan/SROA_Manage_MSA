package com.project.sroa_manage_msa.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class EngineerView {
    private Long empNum;
    private String pw;
    private String name;
    private Long engineerNum;
    private String engineerName;
    private Integer avgScore;
    private Integer amountWork;


    @Builder
    public EngineerView(Long empNum, String pw, String name, Long engineerNum, String engineerName, Integer avgScore, Integer amountWork){
        this.empNum=empNum;
        this.pw=pw;
        this.name=name;
        this.engineerNum=engineerNum;
        this.engineerName=engineerName;
        this.avgScore=avgScore;
        this.amountWork=amountWork;
    }
}
