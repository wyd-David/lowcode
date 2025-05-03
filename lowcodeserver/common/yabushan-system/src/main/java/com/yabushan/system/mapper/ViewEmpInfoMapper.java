package com.yabushan.system.mapper;

import com.yabushan.system.domain.ViewEmpInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * VIEWMapper接口
 *
 * @author 姜森焱
 * @date 2021-09-09
 */
public interface ViewEmpInfoMapper
{
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
    public ViewEmpInfo selectViewEmpInfoByIdAndErpID(@Param("loginid") String loginid,@Param("erpid")  String erpid);

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
    public int bathInsertViewEmpInfo(List<ViewEmpInfo> list);

    /**
     * 修改VIEW
     *
     * @param viewEmpInfo VIEW
     * @return 结果
     */
    public int updateViewEmpInfo(ViewEmpInfo viewEmpInfo);

    /**
     * 删除VIEW
     *
     * @param loginid VIEWID
     * @return 结果
     */
    public int deleteViewEmpInfoById(String loginid);

    /**
     * 批量删除VIEW
     *
     * @param loginids 需要删除的数据ID
     * @return 结果
     */
    public int deleteViewEmpInfoByIds(String[] loginids);


    //根据登录id获取用户信息，多个根据逗号分隔
    public List<ViewEmpInfo> getViewEmpInfoListByloginIds(@Param("loginId") String loginid);

    List<Map<String, Object>> getViewCompanyInfo(String companyId);

    List<Map<String, Object>> selectLiaisonsData(Map userName);
    
}
