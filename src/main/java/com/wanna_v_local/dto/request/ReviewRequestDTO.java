package com.wanna_v_local.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRequestDTO {

    private String type;
    private String keyword;

    @Builder.Default
    private Integer page = 1;

    @Builder.Default
    private Integer size = 10;
}
