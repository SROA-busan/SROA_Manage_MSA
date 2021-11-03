package com.project.sroa_manage_msa.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Data
public class EmployeeInfo {
    @Id
    public Long empNum;
    public String name;

    @Builder
    public EmployeeInfo(Long empNum, String name){
        this.empNum=empNum;
        this.name=name;
    }
}
