package com.project.sroa_manage_msa.repository;

import com.project.sroa_manage_msa.model.EmployeeInfo;
import com.project.sroa_manage_msa.model.EngineerInfo;
import com.project.sroa_manage_msa.model.ServiceCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface EngineerInfoRepository extends JpaRepository<EngineerInfo, Long> {

    @Query("SELECT e FROM EngineerInfo e WHERE e.serviceCenter=?1 ")
    List<EngineerInfo> findAllByServiceCenter(ServiceCenter center);

    @Query("SELECT e.userInfo.name FROM EngineerInfo e WHERE e.engineerNum=?1 ")
    String findEngineerNameByEngineerNum(Long engineerNum);
}
