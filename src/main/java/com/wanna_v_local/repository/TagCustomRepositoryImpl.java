package com.wanna_v_local.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanna_v_local.domain.QReviewTag;
import com.wanna_v_local.domain.QTag;
import com.wanna_v_local.domain.ReviewTag;
import com.wanna_v_local.dto.request.TagRequestDTO;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TagCustomRepositoryImpl implements TagCustomRepository {

    private final JPAQueryFactory queryFactory;
    private final QReviewTag reviewTag = QReviewTag.reviewTag;


    @Override
    public List<ReviewTag> findAll(TagRequestDTO tagRequestDTO) {
        //기본
        JPAQuery<ReviewTag> result = queryFactory.selectFrom(reviewTag)
                .rightJoin(reviewTag.tag, QTag.tag);

        //동적쿼리
        processWhere(result, tagRequestDTO);
        return result
                .groupBy(reviewTag.tag.id)
                .fetch();
    }

    @Override
    public List<Integer> count(TagRequestDTO tagRequestDTO) {
        //기본
        JPAQuery<Long> result = queryFactory.select(reviewTag.count())
                .from(reviewTag)
                .rightJoin(reviewTag.tag, QTag.tag);

        //동적쿼리
        processWhere(result, tagRequestDTO);
        return result
                .groupBy(reviewTag.tag.id)
                .fetch()
                .stream().map(Long::intValue)
                .collect(Collectors.toList());
    }

    public void processWhere(JPAQuery<?> result, TagRequestDTO tagRequestDTO) {
        switch (tagRequestDTO.getType()) {
            //이용형태
            case "pattern" -> {
                result.where(reviewTag.tag.category.contains("이용형태"));
            }

            //맛/가격
            case "price" -> {
                result.where(reviewTag.tag.category.contains("맛/가격"));
            }

            //서비스/기타
            case "service" -> {
                result.where(reviewTag.tag.category.contains("서비스/기타"));
            }

            //분위기
            case "mood" -> {
                result.where(reviewTag.tag.category.contains("분위기"));
            }
        }
    }
}
