package com.yabushan.datasource.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SqldxecuteMapper {
    @Select("${sqlStr}")
    Object execute(@Param(value = "sqlStr") String sqlStr);

    @Select("${sqlStr}")
    List<Map<String, Object>> selectPublicItemList(@Param(value="sqlStr") String sqlStr);



}
