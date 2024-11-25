package com.wanna_v_local.dto.request;

import com.wanna_v_local.domain.Review;
import com.wanna_v_local.domain.User;
import com.wanna_v_local.domain.UserGradeLog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointDTO {

    private Review review;
    private User user;
    private UserGradeLog userGradeLog;
}
