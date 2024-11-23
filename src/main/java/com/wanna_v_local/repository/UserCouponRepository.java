package com.wanna_v_local.repository;

import com.wanna_v_local.domain.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {

    @Query("SELECT uc FROM UserCoupon uc " +
            "JOIN uc.coupon c " +
            "WHERE uc.user.id = :userId " +
            "AND c.endDate >= CURRENT_TIMESTAMP " +
            "AND c.active = true " +
            "AND uc.used = false")
    List<UserCoupon> findAllByUserIdAndEndDate(Long userId);
}
