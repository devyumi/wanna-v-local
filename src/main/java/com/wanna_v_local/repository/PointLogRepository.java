package com.wanna_v_local.repository;

import com.wanna_v_local.domain.PointLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointLogRepository extends JpaRepository<PointLog, Long> {

    List<PointLog> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}
