package com.wanna_v_local.repository.impl;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanna_v_local.domain.QRestaurant;
import com.wanna_v_local.domain.QReview;
import com.wanna_v_local.domain.QUser;
import com.wanna_v_local.domain.Review;
import com.wanna_v_local.dto.request.ReviewRequestDTO;
import com.wanna_v_local.repository.ReviewCustomRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ReviewCustomRepositoryImpl implements ReviewCustomRepository {

    private final JPAQueryFactory queryFactory;
    private final QReview review = QReview.review;

    @Override
    public List<Review> findAll(ReviewRequestDTO reviewRequestDTO) {

        //기본
        JPAQuery<Review> result = queryFactory.selectFrom(review)
                .innerJoin(review.restaurant, QRestaurant.restaurant)
                .innerJoin(review.user, QUser.user);

        //동적쿼리
        processWhere(result, reviewRequestDTO);
        return result
                .offset(reviewRequestDTO.getOffset())
                .limit(reviewRequestDTO.getSize())
                .fetchJoin().fetch();
    }

    @Override
    public Integer count(ReviewRequestDTO reviewRequestDTO) {
        //기본
        JPAQuery<Long> result = queryFactory.select(review.count())
                .from(review)
                .innerJoin(review.restaurant, QRestaurant.restaurant)
                .innerJoin(review.user, QUser.user);

        //동적쿼리
        processWhere(result, reviewRequestDTO);
        return result.fetchFirst().intValue();
    }

    /**
     * 동적쿼리 메서드
     *
     * @param result
     * @param reviewRequestDTO
     */
    public void processWhere(JPAQuery<?> result, ReviewRequestDTO reviewRequestDTO) {

        if (reviewRequestDTO.getKeyword() == null) {
            if (reviewRequestDTO.getType() == null) {
                result.orderBy(review.id.desc());
            } else if (reviewRequestDTO.getType().equals("new")) {
                result.orderBy(review.id.desc());
            } else if (reviewRequestDTO.getType().equals("old")) {
                result.orderBy(review.id.asc());
            } else if (reviewRequestDTO.getType().equals("rating")) {
                result.where(review.rating.eq(Integer.parseInt(reviewRequestDTO.getScore())));
            } else if (reviewRequestDTO.getType().equals("status")) {
                result.where(review.isActive.eq(Boolean.parseBoolean(reviewRequestDTO.getIsActive())));
            }
        } else {
            //키워드 검색
            result.where(review.content.contains(reviewRequestDTO.getKeyword()))
                    .orderBy(review.id.asc());
        }
    }
}
