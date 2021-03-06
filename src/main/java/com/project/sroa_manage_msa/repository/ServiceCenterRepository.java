package com.project.sroa_manage_msa.repository;

import com.project.sroa_manage_msa.model.ServiceCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ServiceCenterRepository extends JpaRepository<ServiceCenter, Long> {
    @Query("SELECT c FROM ServiceCenter c WHERE c.centerName=?1")
    ServiceCenter findByCenterName(String centerName);

    ServiceCenter findByCenterNum(Long num);

    @Transactional
    @Modifying
    @Query("UPDATE ServiceCenter s SET s.latitude=?2, s.longitude=?3 WHERE s.centerNum=?1")
    void updateCoor(Long centerNum, Double lat, Double lon);
}
