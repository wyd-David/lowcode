package com.yabushan.form.vo;

import lombok.Data;

@Data
public class FileStr {
    
    public String status;
    public String name;
    public String size;
    public String response;

    public FileStr(String status, String name, String size, String response) {
        this.status = status;
        this.name = name;
        this.size = size;
        this.response = response;
    }
}
