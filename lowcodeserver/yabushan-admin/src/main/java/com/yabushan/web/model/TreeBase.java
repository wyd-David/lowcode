package com.yabushan.web.model;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 树形模型
 *
 * @author ly
 * @date 2020/2/14  10:56
 */
@Slf4j
@Data
public class TreeBase<T extends TreeBase> implements Serializable {
    private static final long serialVersionUID = 1L;

    Integer id;
    Integer parentId;
    List<T> children=new ArrayList<>();

}
