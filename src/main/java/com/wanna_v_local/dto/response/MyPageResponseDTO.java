package com.wanna_v_local.dto.response;

import com.wanna_v_local.common.Grade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyPageResponseDTO {

    private String username;
    private Long point;
    private Grade grade;
    private Integer reviewCount;
    private Integer couponCount;
}
