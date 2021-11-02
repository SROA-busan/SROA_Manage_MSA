package com.project.sroa_manage_msa.service;

import com.project.sroa_manage_msa.model.EmployeeInfo;
import com.project.sroa_manage_msa.model.EngineerInfo;
import com.project.sroa_manage_msa.model.ServiceCenter;
import com.project.sroa_manage_msa.model.UserInfo;
import com.project.sroa_manage_msa.opt.Coordinates;

import java.util.List;

public interface AccountService {
    List<ServiceCenter> searchAllCenter();

    ServiceCenter searchCenter(String centerName);

    EmployeeInfo createEmployee(Long employeeNum, String name);

    UserInfo createUserInfo(Long employeeNum, String name, String phoneNum);

    void createEnginnerInfo(ServiceCenter center, EmployeeInfo employee, UserInfo user);

    boolean checkDuplicateEmployeeNum(Long employeeNum);

    List<EngineerInfo> searchEngineerAtCenter(ServiceCenter center);

    String findEngineerName(Long engineerNum);

    void storeCenter(String centerName, String address, Coordinates coor);
}
