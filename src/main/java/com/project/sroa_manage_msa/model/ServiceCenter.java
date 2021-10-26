package com.project.sroa_manage_msa.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class ServiceCenter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long centerNum;
    private String centerName;
    private String address;
    private Double latitude;
    private Double longitude;
}
