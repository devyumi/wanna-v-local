package com.wanna_v_local.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewUpdateStatusDTO {

    @NotBlank(message = "숨긴 이유는 필수 입력 값입니다.")
    @Size(min = 5, max = 30, message = "5자 이상 30자 이하만 가능합니다.")
    private String note;
}
