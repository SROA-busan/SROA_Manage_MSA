package com.project.sroa_manage_msa.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class ServiceCenter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long centerNum;
    private String centerName;
    private String address;
    private Double latitude;
    private Double longitude;

    @Builder
    public ServiceCenter(String centerName, String address, Double latitude, Double longitude){
        this.centerName=centerName;
        this.address=address;
        this.latitude=latitude;
        this.longitude=longitude;
    }
}
