package com.project.sroa_manage_msa.service;

import com.project.sroa_manage_msa.model.*;
import com.project.sroa_manage_msa.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InfoServiceImpl implements InfoService {
    EngineerInfoRepository engineerInfoRepository;
    ServiceCenterRepository serviceCenterRepository;
    UserInfoRepository userInfoRepository;
    ProductRepository productRepository;
    EvaluationRepository evaluationRepository;
    ScheduleRepository scheduleRepository;

    @Autowired
    public InfoServiceImpl(EngineerInfoRepository engineerInfoRepository,
                           ServiceCenterRepository serviceCenterRepository,
                           UserInfoRepository userInfoRepository,
                           ProductRepository productRepository,
                           EvaluationRepository evaluationRepository,
                           ScheduleRepository scheduleRepository) {
        this.engineerInfoRepository = engineerInfoRepository;
        this.serviceCenterRepository = serviceCenterRepository;
        this.userInfoRepository = userInfoRepository;
        this.productRepository = productRepository;
        this.evaluationRepository = evaluationRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public List<EngineerInfo> searchEngineerAtCenter(Long centerNum) {
        return engineerInfoRepository.findAllByCenterNum(centerNum);
    }

    @Override
    public String findEngineerName(Long engineerNum) {
        return engineerInfoRepository.findEngineerNameByEngineerNum(engineerNum);
    }

    @Override
    public List<ServiceCenter> findAllCenter() {
        return serviceCenterRepository.findAll();
    }

    @Override
    public List<UserInfo> findAllUserByStatus(Integer status) {
        return userInfoRepository.findAllByStatus(status);
    }

    @Override
    public List<Schedule> findAllSchedule() {
        return scheduleRepository.findAllSchedules();
    }

    @Override
    public List<Evaluation> findEvaluationByEngineer(Long engineerNum) {
        return evaluationRepository.findAllByEngineerNum(engineerNum);
    }

    @Override
    public String convertStatusToString(Integer status) {
        String res = new String();
        if (status == 0)
            res = "예약완료";
        else if (status == 1)
            res = "처리완료";
        else if (status == 2)
            res = "입고 수리중";
        else if (status == 3)
            res = "수리완료/반납대기";
        else if (status == 4)
            res = "반납예약 완료";
        else
            res = "평가완료";
        return res;
    }
}

