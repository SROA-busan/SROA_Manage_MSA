package com.project.sroa_manage_msa.dto;

import lombok.Getter;

@Getter
public class ProductView {
    private Long scheduleNum;
    private String productName;
    private String problem;
    private String startDate;
    private String endDate;
    private String status;
    private String userId;
    private Long empNum;

    public ProductView(Long scheduleNum, String productName, String problem, String startDate, String endDate, String status, String userId, Long empNum) {
        this.scheduleNum = scheduleNum;
        this.productName = productName;
        this.problem = problem;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.userId = userId;
        this.empNum = empNum;
    }
}
