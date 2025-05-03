package com.yabushan.system.mapper;

import com.yabushan.system.domain.ViewDeptInfo;

import java.util.List;
import java.util.Map;

public interface ViewDeptInfoMapper {

   public  List<ViewDeptInfo> selectViewDeptInfoList(ViewDeptInfo viewDeptInfo);

   public List<Map<String,Object>> selectViewPostLevelInfo(Map<String,Object> map);

   List<Map<String,Object>>  getCompanyList(ViewDeptInfo viewDeptInfo);
}
