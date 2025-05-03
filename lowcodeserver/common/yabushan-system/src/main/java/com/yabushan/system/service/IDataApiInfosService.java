package com.yabushan.system.service;

import java.util.List;
import com.yabushan.system.domain.DataApiInfos;


/**
 * 数据服务APIService接口
 *
 * @author yabushan
 * @date 2022-11-11
 */
public interface IDataApiInfosService
{



    /**
     * 清空返回导入数据的集合
     */
    public void cleardataApiInfosInfo();


    /**
     * 查询数据服务API
     *
     * @param id 数据服务APIID
     * @return 数据服务API
     */
    public DataApiInfos selectDataApiInfosById(Long id);
    public DataApiInfos selectDataApiInfosCode(String apiCode);

    /**
     * 查询数据服务API列表
     *
     * @param dataApiInfos 数据服务API
     * @return 数据服务API集合
     */
    public List<DataApiInfos> selectDataApiInfosList(DataApiInfos dataApiInfos);

    /**
     * 新增数据服务API
     *
     * @param dataApiInfos 数据服务API
     * @return 结果
     */
    public int insertDataApiInfos(DataApiInfos dataApiInfos);

    /**
     * 批量新增数据服务API
     *
     * @param dataApiInfos 数据服务API
     * @return 结果
     */
    public int bathInsertDataApiInfos(List<DataApiInfos> dataApiInfos);

    /**
     * 修改数据服务API
     *
     * @param dataApiInfos 数据服务API
     * @return 结果
     */
    public int updateDataApiInfos(DataApiInfos dataApiInfos);
    /**
     * 通过id集合修改状态
     */
    public Boolean updateByIds(String ids,String disableEnableState);

    /**
     * 批量删除数据服务API
     *
     * @param ids 需要删除的数据服务APIID
     * @return 结果
     */
    public int deleteDataApiInfosByIds(Long[] ids);

    /**
     * 删除数据服务API信息
     *
     * @param id 数据服务APIID
     * @return 结果
     */
    public int deleteDataApiInfosById(Long id);

    /**
     * 导入数据服务APIExcel数据
     *
     * @param dataApiInfosList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<DataApiInfos> importDataApiInfos(List<DataApiInfos> dataApiInfosList, Boolean isUpdateSupport, String operName);


    public Long getCompanyIdByUserName(String userName);
}
