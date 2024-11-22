package com.wanna_v_local.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileDTO {

    private String originalFileName;    //test.png
    private String uploadFileName;      //UUID.png
    private String uploadFilePath;      //Object Storage에 설정한 경로
    private String uploadFileUrl;       //file url
}
