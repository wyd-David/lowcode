package com.yabushan.system.service.impl;

import com.yabushan.common.utils.StringUtils;
import com.yabushan.system.domain.ViewEmpInfo;
import com.yabushan.system.mapper.ViewEmpInfoMapper;
import com.yabushan.system.service.IViewEmpInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * VIEWService业务层处理
 *
 * @author 姜森焱
 * @date 2021-09-09
 */
@Service
public class ViewEmpInfoServiceImpl implements IViewEmpInfoService
{
    private static final Logger log = LoggerFactory.getLogger(ViewEmpInfoServiceImpl.class);
    @Autowired
    private ViewEmpInfoMapper viewEmpInfoMapper;
    //存放导入所有的成功失败数据
    private static List<ViewEmpInfo> viewEmpInfoInfo = new ArrayList<>();

    /**
    * 清空返回导入数据的集合
    */
    public void clearviewEmpInfoInfo() {
        if (StringUtils.isNotNull(viewEmpInfoInfo) && viewEmpInfoInfo.size() > 0) {
                viewEmpInfoInfo.clear();
        }
    }

    /**
     * 查询VIEW
     *
     * @param loginid VIEWID
     * @return VIEW
     */
    @Override
    public ViewEmpInfo selectViewEmpInfoById(String loginid)
    {

        return viewEmpInfoMapper.selectViewEmpInfoById(loginid);
    }


    /**
     * 查询VIEW
     *
     * @param loginid VIEWID
     * @return VIEW
     */
    @Override
    public ViewEmpInfo selectViewEmpInfoByIdAndErpID(String loginid,String erpID)
    {

        return viewEmpInfoMapper.selectViewEmpInfoByIdAndErpID(loginid,erpID);
    }

    /**
     * 查询VIEW列表
     *
     * @param viewEmpInfo VIEW
     * @return VIEW
     */
    @Override
    public List<ViewEmpInfo> selectViewEmpInfoList(ViewEmpInfo viewEmpInfo)
    {
        return viewEmpInfoMapper.selectViewEmpInfoList(viewEmpInfo);
    }

    /**
     * 新增VIEW
     *
     * @param viewEmpInfo VIEW
     * @return 结果
     */
    @Override
    public int insertViewEmpInfo(ViewEmpInfo viewEmpInfo)
    {
        return viewEmpInfoMapper.insertViewEmpInfo(viewEmpInfo);
    }

    /**
     * 批量新增VIEW
     *
     * @param viewEmpInfo VIEW
     * @return 结果
     */
    @Override
    public int bathInsertViewEmpInfo(List<ViewEmpInfo> viewEmpInfo)
    {
        return viewEmpInfoMapper.bathInsertViewEmpInfo(viewEmpInfo);
    }


    /**
     * 修改VIEW
     *
     * @param viewEmpInfo VIEW
     * @return 结果
     */
    @Override
    public int updateViewEmpInfo(ViewEmpInfo viewEmpInfo)
    {
        return viewEmpInfoMapper.updateViewEmpInfo(viewEmpInfo);
    }

    /**
     * 批量删除VIEW
     *
     * @param loginids 需要删除的VIEWID
     * @return 结果
     */
    @Override
    public int deleteViewEmpInfoByIds(String[] loginids)
    {
        return viewEmpInfoMapper.deleteViewEmpInfoByIds(loginids);
    }

    /**
     * 删除VIEW信息
     *
     * @param loginid VIEWID
     * @return 结果
     */
    @Override
    public int deleteViewEmpInfoById(String loginid)
    {
        return viewEmpInfoMapper.deleteViewEmpInfoById(loginid);
    }

    /**
    * 根据传来的id集合修改禁用启用状态
    * @param ids
    * @param disableEnableState
    * @return
    */
    @Transactional
    @Override
    public Boolean updateByIds(String ids, String disableEnableState) {
        int count = 0;
        String idslist[]=ids.split(",",-1);
        if(StringUtils.isNotNull(idslist)&&idslist.length>=1){
            for (int i = 0; i < idslist.length; i++) {
                ViewEmpInfo viewEmpInfo=viewEmpInfoMapper.selectViewEmpInfoById(idslist[i]);
                viewEmpInfo.setDisableEnableState(disableEnableState);
                int isappinfo = viewEmpInfoMapper.updateViewEmpInfo(viewEmpInfo);
                if (isappinfo == 1) {
                    count++;
                }
            }
            if (count == idslist.length) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    /**
     * 导入VIEWExcel数据
     *
     * @param viewEmpInfoList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<ViewEmpInfo>  importViewEmpInfo(List<ViewEmpInfo> viewEmpInfoList, Boolean isUpdateSupport, String operName)
    {
        for (int i = 0; i < viewEmpInfoList.size(); i++) {
            ViewEmpInfo viewEmpInfo=viewEmpInfoList.get(i);
        try {
            // 验证是否存在这个用户
            ViewEmpInfo u = viewEmpInfoMapper.selectViewEmpInfoById(viewEmpInfo.getLoginid());
            if (StringUtils.isNull(u)) {
                    viewEmpInfoMapper.insertViewEmpInfo(viewEmpInfo);
                    viewEmpInfo.setImportStatus("导入成功");
                    viewEmpInfoInfo.add(viewEmpInfo);
            } else if (isUpdateSupport) {
                    viewEmpInfoMapper.updateViewEmpInfo(viewEmpInfo);
                    viewEmpInfo.setImportStatus("更新成功");
                    viewEmpInfoInfo.add(viewEmpInfo);
            } else {
                    viewEmpInfo.setImportStatus("已存在当前数据");
                    viewEmpInfoInfo.add(viewEmpInfo);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            viewEmpInfo.setImportStatus("数据格式有问题，请认证检查数据！");
            viewEmpInfoInfo.add(viewEmpInfo);
            //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
            importViewEmpInfo(viewEmpInfoList.subList(i+1,viewEmpInfoList.size()), isUpdateSupport, operName);
            return viewEmpInfoInfo;
        }

    }
        return viewEmpInfoInfo;
    }

    @Override
    public List<ViewEmpInfo> getViewEmpInfoListByloginIds(String loginId) {
        return viewEmpInfoMapper.getViewEmpInfoListByloginIds(loginId);
    }

    @Override
    public List<Map<String, Object>> getViewCompanyInfo(String companyId) {
        return viewEmpInfoMapper.getViewCompanyInfo(companyId);
    }
    @Override
    public List<Map<String, Object>> selectLiaisonsData(Map userName) {
        return viewEmpInfoMapper.selectLiaisonsData(userName);
    }
    
}
