package com.yabushan.system.service;

import java.util.List;
import java.util.Map;

import com.yabushan.system.domain.ViewEmpInfo;

/**
 * VIEWService接口
 *
 * @author 姜森焱
 * @date 2021-09-09
 */
public interface IViewEmpInfoService {
    /**
     * 查询VIEW
     *
     * @param loginid VIEWID
     * @return VIEW
     */
    public ViewEmpInfo selectViewEmpInfoById(String loginid);

    /**
     * 查询VIEW
     *
     * @param loginid VIEWID
     * @return VIEW
     */
    public ViewEmpInfo selectViewEmpInfoByIdAndErpID(String loginid,String erpID);

    /**
     * 查询VIEW列表
     *
     * @param viewEmpInfo VIEW
     * @return VIEW集合
     */
    public List<ViewEmpInfo> selectViewEmpInfoList(ViewEmpInfo viewEmpInfo);

    /**
     * 新增VIEW
     *
     * @param viewEmpInfo VIEW
     * @return 结果
     */
    public int insertViewEmpInfo(ViewEmpInfo viewEmpInfo);

    /**
     * 批量新增VIEW
     *
     * @param viewEmpInfo VIEW
     * @return 结果
     */
    public int bathInsertViewEmpInfo(List<ViewEmpInfo> viewEmpInfo);

    /**
     * 修改VIEW
     *
     * @param viewEmpInfo VIEW
     * @return 结果
     */
    public int updateViewEmpInfo(ViewEmpInfo viewEmpInfo);

    /**
     * 通过id集合修改状态
     */
    public Boolean updateByIds(String ids, String disableEnableState);

    /**
     * 批量删除VIEW
     *
     * @param loginids 需要删除的VIEWID
     * @return 结果
     */
    public int deleteViewEmpInfoByIds(String[] loginids);

    /**
     * 删除VIEW信息
     *
     * @param loginid VIEWID
     * @return 结果
     */
    public int deleteViewEmpInfoById(String loginid);

    /**
     * 导入VIEWExcel数据
     *
     * @param viewEmpInfoList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    public List<ViewEmpInfo> importViewEmpInfo(List<ViewEmpInfo> viewEmpInfoList, Boolean isUpdateSupport, String operName);


    public List<ViewEmpInfo> getViewEmpInfoListByloginIds(String loginId);

    public List<Map<String,Object>> getViewCompanyInfo(String companyId);

    public List<Map<String,Object>> selectLiaisonsData(Map companyId);
}
