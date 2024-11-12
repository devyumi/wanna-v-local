package com.wanna_v_local.repository;

import com.wanna_v_local.domain.Review;
import com.wanna_v_local.dto.request.ReviewRequestDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewCustomRepository {

    List<Review> findAll(ReviewRequestDTO reviewRequestDTO);

    Integer count(ReviewRequestDTO reviewRequestDTO);
}
