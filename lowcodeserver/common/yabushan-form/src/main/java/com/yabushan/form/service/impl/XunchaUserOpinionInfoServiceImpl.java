package com.yabushan.form.service.impl;

import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.form.domain.XunchaUserOpinionInfo;
import com.yabushan.form.mapper.XunchaUserOpinionInfoMapper;
import com.yabushan.form.service.IXunchaUserOpinionInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 常用意见Service业务层处理
 *
 * @author CJH
 * @date 2022-08-04
 */
@Service
public class XunchaUserOpinionInfoServiceImpl implements IXunchaUserOpinionInfoService {
    private static final Logger log = LoggerFactory.getLogger(XunchaUserOpinionInfoServiceImpl.class);
    @Autowired
    private XunchaUserOpinionInfoMapper xunchaUserOpinionInfoMapper;

    //存放导入所有的成功失败数据
    private static List<XunchaUserOpinionInfo> xunchaUserOpinionInfoInfo = new ArrayList<>();

    /**
     * 清空返回导入数据的集合
     */
    public void clearxunchaUserOpinionInfoInfo() {
        if (StringUtils.isNotNull(xunchaUserOpinionInfoInfo) && xunchaUserOpinionInfoInfo.size() > 0) {
            xunchaUserOpinionInfoInfo.clear();
        }
    }

    /**
     * 查询常用意见
     *
     * @param userId 常用意见ID
     * @return 常用意见
     */
    @Override
    public XunchaUserOpinionInfo selectXunchaUserOpinionInfoById(String userId) {
        return xunchaUserOpinionInfoMapper.selectXunchaUserOpinionInfoById(userId);
    }

    /**
     * 查询常用意见列表
     *
     * @param xunchaUserOpinionInfo 常用意见
     * @return 常用意见
     */
    @Override
    public List<XunchaUserOpinionInfo> selectXunchaUserOpinionInfoList(XunchaUserOpinionInfo xunchaUserOpinionInfo) {
        return xunchaUserOpinionInfoMapper.selectXunchaUserOpinionInfoList(xunchaUserOpinionInfo);
    }

    /**
     * 新增常用意见
     *
     * @param xunchaUserOpinionInfo 常用意见
     * @return 结果
     */
    @Override
    public int insertXunchaUserOpinionInfo(XunchaUserOpinionInfo xunchaUserOpinionInfo) {
        xunchaUserOpinionInfo.setUserName(SecurityUtils.getNickName());
        xunchaUserOpinionInfo.setCreatedTime(DateUtils.getNowDate());
        return xunchaUserOpinionInfoMapper.insertXunchaUserOpinionInfo(xunchaUserOpinionInfo);
    }

    /**
     * 批量新增常用意见
     *
     * @param xunchaUserOpinionInfo 常用意见
     * @return 结果
     */
    @Override
    public int bathInsertXunchaUserOpinionInfo(List<XunchaUserOpinionInfo> xunchaUserOpinionInfo) {
        return xunchaUserOpinionInfoMapper.bathInsertXunchaUserOpinionInfo(xunchaUserOpinionInfo);
    }


    /**
     * 修改常用意见
     *
     * @param xunchaUserOpinionInfo 常用意见
     * @return 结果
     */
    @Override
    public int updateXunchaUserOpinionInfo(XunchaUserOpinionInfo xunchaUserOpinionInfo) {
        return xunchaUserOpinionInfoMapper.updateXunchaUserOpinionInfo(xunchaUserOpinionInfo);
    }

    /**
     * 批量删除常用意见
     *
     * @param userIds 需要删除的常用意见ID
     * @return 结果
     */
    @Override
    public int deleteXunchaUserOpinionInfoByIds(String[] userIds) {
        return xunchaUserOpinionInfoMapper.deleteXunchaUserOpinionInfoByIds(userIds);
    }

    /**
     * 删除常用意见信息
     *
     * @param userId 常用意见ID
     * @return 结果
     */
    @Override
    public int deleteXunchaUserOpinionInfoById(String userId) {
        return xunchaUserOpinionInfoMapper.deleteXunchaUserOpinionInfoById(userId);
    }


}
