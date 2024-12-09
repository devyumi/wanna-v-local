package com.wanna_v_local.repository.mypage.query;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanna_v_local.domain.QReview;
import com.wanna_v_local.domain.QUser;
import com.wanna_v_local.domain.QUserCoupon;
import com.wanna_v_local.domain.QUserGradeLog;
import com.wanna_v_local.dto.response.MyPageResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MyPageDTORepository {

    private final JPAQueryFactory queryFactory;
    private final QUser user = QUser.user;
    private final QUserGradeLog userGradeLog = QUserGradeLog.userGradeLog;

    public MyPageResponseDTO findMyPageById(Long userId) {
        return queryFactory
                .select(Projections.fields(MyPageResponseDTO.class,
                        user.username, user.profile, user.point, userGradeLog.grade,
                        QReview.review.id.countDistinct().intValue().as("reviewCount"),
                        QUserCoupon.userCoupon.id.countDistinct().intValue().as("couponCount")))
                .from(user)
                .join(userGradeLog).on(userGradeLog.user.id.eq(user.id))
                .leftJoin(QReview.review).on(QReview.review.user.id.eq(user.id))
                .leftJoin(QUserCoupon.userCoupon).on(QUserCoupon.userCoupon.user.id.eq(user.id))
                .where(user.id.eq(userId)
                        .and(userGradeLog.createdAt.eq(
                                JPAExpressions.select(userGradeLog.createdAt.max())
                                        .from(userGradeLog)
                                        .where(userGradeLog.user.id.eq(user.id)))))
                .groupBy(user.username, user.profile, user.point, userGradeLog.grade).fetchFirst();
    }
}
