package com.yabushan.ai.domain;


import com.yabushan.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;
@Data
public class VoiceRecord extends BaseEntity {

    private Long id;

    private String userId;
    private String fileName;
    private String filePath;
    private Date createTime;

    // getter和setter
}