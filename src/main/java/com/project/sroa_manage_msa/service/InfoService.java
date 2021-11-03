package com.project.sroa_manage_msa.service;

import com.project.sroa_manage_msa.model.*;

import java.util.List;

public interface InfoService {

    List<EngineerInfo> searchEngineerAtCenter(Long centerNum);

    String findEngineerName(Long engineerNum);

    List<ServiceCenter> findAllCenter();

    List<UserInfo> findAllUserByStatus(Integer status);


    List<Evaluation> findEvaluationByEngineer(Long engineerNum);

    List<Schedule> findAllSchedule();

    String convertStatusToString(Integer status);
}
