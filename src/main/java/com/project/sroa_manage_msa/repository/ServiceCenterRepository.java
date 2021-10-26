package com.project.sroa_manage_msa.repository;

import com.project.sroa_manage_msa.model.ServiceCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ServiceCenterRepository extends JpaRepository<ServiceCenter, Long> {
    ServiceCenter findByCenterName(String centerName);

    ServiceCenter findByCenterNum(Long num);
}
