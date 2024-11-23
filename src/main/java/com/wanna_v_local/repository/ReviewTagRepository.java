package com.wanna_v_local.repository;

import com.wanna_v_local.domain.ReviewTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewTagRepository extends JpaRepository<ReviewTag, Long> {

    List<ReviewTag> findAllByReviewId(Long reviewId);
}
