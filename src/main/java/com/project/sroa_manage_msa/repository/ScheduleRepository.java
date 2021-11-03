package com.project.sroa_manage_msa.repository;

import com.project.sroa_manage_msa.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {


    @Query(nativeQuery = true, value = "SELECT s.* FROM Schedule s WHERE s.start_date like concat('%', ?2, '%') AND s.engineer_num=?1")
    List<Schedule> findAllByEngineerInfoAndDateTime(Long engineerNum, String dateTime);


    @Query("SELECT s FROM Schedule s")
    List<Schedule> findAllSchedules();
}
