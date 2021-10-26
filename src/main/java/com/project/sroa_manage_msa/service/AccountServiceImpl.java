package com.project.sroa_manage_msa.service;

import com.project.sroa_manage_msa.model.EmployeeInfo;
import com.project.sroa_manage_msa.model.EngineerInfo;
import com.project.sroa_manage_msa.model.ServiceCenter;
import com.project.sroa_manage_msa.model.UserInfo;
import com.project.sroa_manage_msa.repository.EmployeeInfoRepository;
import com.project.sroa_manage_msa.repository.EngineerInfoRepository;
import com.project.sroa_manage_msa.repository.ServiceCenterRepository;
import com.project.sroa_manage_msa.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService{
    ServiceCenterRepository serviceCenterRepository;
    EmployeeInfoRepository employeeInfoRepository;
    UserInfoRepository userInfoRepository;
    EngineerInfoRepository engineerInfoRepository;

    @Autowired
    public AccountServiceImpl(ServiceCenterRepository serviceCenterRepository,
                              EmployeeInfoRepository employeeInfoRepository,
                              UserInfoRepository userInfoRepository,
                              EngineerInfoRepository engineerInfoRepository){
        this.serviceCenterRepository=serviceCenterRepository;
        this.employeeInfoRepository=employeeInfoRepository;
        this.userInfoRepository=userInfoRepository;
        this.engineerInfoRepository=engineerInfoRepository;
    }

    @Override
    public List<EngineerInfo> searchEngineerAtCenter(ServiceCenter center) {
        return engineerInfoRepository.findAllByServiceCenter(center);
    }

    @Override
    public String findEngineerName(Long engineerNum) {
        return engineerInfoRepository.findEngineerNameByEngineerNum(engineerNum);
    }

    @Override
    public List<ServiceCenter> searchAllCenter() {
        return serviceCenterRepository.findAll();
    }

    @Override
    public ServiceCenter searchCenter(String centerName) {
        return serviceCenterRepository.findByCenterName(centerName);
    }

    @Override
    public EmployeeInfo createEmployee(Long employeeNum, String name) {
        EmployeeInfo employeeInfo= EmployeeInfo.builder()
                .empNum(employeeNum)
                .name(name)
                .build();
        return employeeInfoRepository.save(employeeInfo);
    }

    @Override
    public UserInfo createUserInfo(Long employeeNum, String name, String phoneNum) {
        UserInfo userInfo= UserInfo.builder()
                .address(null)
                .name(name)
                .phoneNum(phoneNum)
                .id(employeeNum.toString())
                .pw("00000000")
                .build();
        return userInfoRepository.save(userInfo);
    }

    @Override
    public void createEnginnerInfo(ServiceCenter center, EmployeeInfo employee, UserInfo user) {
        EngineerInfo engineerInfo=EngineerInfo.builder()
                .employeeInfo(employee)
                .serviceCenter(center)
                .userInfo(user)
                .build();
        engineerInfoRepository.save(engineerInfo);
    }

    @Override
    public boolean checkDuplicateEmployeeNum(Long employeeNum) {
        if (employeeInfoRepository.findByEmpNum(employeeNum) != null) {
            return true;
        }
        return false;
    }
}
