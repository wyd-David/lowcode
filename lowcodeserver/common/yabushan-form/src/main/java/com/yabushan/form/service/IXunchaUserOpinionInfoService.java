package com.yabushan.form.service;


import com.yabushan.form.domain.XunchaUserOpinionInfo;

import java.util.List;

/**
 * 常用意见Service接口
 *
 * @author CJH
 * @date 2022-08-04
 */
public interface IXunchaUserOpinionInfoService
{



    /**
    * 清空返回导入数据的集合
    */
    public void clearxunchaUserOpinionInfoInfo();


    /**
     * 查询常用意见
     *
     * @param userId 常用意见ID
     * @return 常用意见
     */
    public XunchaUserOpinionInfo selectXunchaUserOpinionInfoById(String userId);

    /**
     * 查询常用意见列表
     *
     * @param xunchaUserOpinionInfo 常用意见
     * @return 常用意见集合
     */
    public List<XunchaUserOpinionInfo> selectXunchaUserOpinionInfoList(XunchaUserOpinionInfo xunchaUserOpinionInfo);

    /**
     * 新增常用意见
     *
     * @param xunchaUserOpinionInfo 常用意见
     * @return 结果
     */
    public int insertXunchaUserOpinionInfo(XunchaUserOpinionInfo xunchaUserOpinionInfo);

    /**
     * 批量新增常用意见
     *
     * @param xunchaUserOpinionInfo 常用意见
     * @return 结果
     */
    public int bathInsertXunchaUserOpinionInfo(List<XunchaUserOpinionInfo> xunchaUserOpinionInfo);

    /**
     * 修改常用意见
     *
     * @param xunchaUserOpinionInfo 常用意见
     * @return 结果
     */
    public int updateXunchaUserOpinionInfo(XunchaUserOpinionInfo xunchaUserOpinionInfo);

    /**
     * 批量删除常用意见
     *
     * @param userIds 需要删除的常用意见ID
     * @return 结果
     */
    public int deleteXunchaUserOpinionInfoByIds(String[] userIds);

    /**
     * 删除常用意见信息
     *
     * @param userId 常用意见ID
     * @return 结果
     */
    public int deleteXunchaUserOpinionInfoById(String userId);

}
