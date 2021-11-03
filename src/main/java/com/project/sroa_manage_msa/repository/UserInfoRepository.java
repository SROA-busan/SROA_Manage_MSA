package com.project.sroa_manage_msa.repository;

import com.project.sroa_manage_msa.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    @Query("SELECT u FROM UserInfo u WHERE u.code=?1")
    List<UserInfo> findAllByStatus(Integer status);
}
