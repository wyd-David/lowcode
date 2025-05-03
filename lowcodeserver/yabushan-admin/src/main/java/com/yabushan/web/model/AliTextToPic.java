package com.yabushan.web.model;

import lombok.Data;

@Data
public class AliTextToPic {
    String prompt;
    String key;
    String model;
    Integer number;
    Integer width;
    Integer height;
}
