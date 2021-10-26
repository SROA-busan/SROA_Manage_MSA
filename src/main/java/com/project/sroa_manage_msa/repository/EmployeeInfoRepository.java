package com.project.sroa_manage_msa.repository;

import com.project.sroa_manage_msa.model.EmployeeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeInfoRepository extends JpaRepository<EmployeeInfo, Long> {

    EmployeeInfo findByEmpNum(Long empNum);
}
