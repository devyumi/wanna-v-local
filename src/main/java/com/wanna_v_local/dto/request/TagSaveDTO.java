package com.wanna_v_local.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagSaveDTO {

    private String category;

    @NotBlank(message = "태그명은 필수 입력 값입니다.")
    @Size(min = 3, max = 20, message = "5자 이상 20자 이하로 입력해주세요.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9\\s]+$", message = "한글, 영어, 숫자만 가능합니다.")
    private String name;
}
