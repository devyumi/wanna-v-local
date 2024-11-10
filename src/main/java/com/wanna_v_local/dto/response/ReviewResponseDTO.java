package com.wanna_v_local.dto.response;

import com.wanna_v_local.domain.Review;
import com.wanna_v_local.dto.request.ReviewRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDTO {

    private Integer page;
    private Integer size;
    private String type;
    private String keyword;
    private Integer total;
    private Integer last;
    private Integer start;
    private Integer end;
    private List<Review> reviews;

    @Builder
    public ReviewResponseDTO(ReviewRequestDTO reviewRequestDto, List<Review> reviews, Integer total) {
        this.page = reviewRequestDto.getPage();
        this.size = reviewRequestDto.getSize();
        this.type = reviewRequestDto.getType();
        this.keyword = reviewRequestDto.getKeyword();
        this.reviews = reviews;
        this.total = total;
        this.last = (int) Math.ceil(total / (double) size);
        this.start = (page - 1) / 10 * 10 + 1;
        this.end = (last == 0) ? 1 : Math.min(start + 9, last);
    }
}
