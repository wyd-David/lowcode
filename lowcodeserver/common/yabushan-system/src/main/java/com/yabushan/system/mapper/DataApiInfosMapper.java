package com.yabushan.system.mapper;

import java.util.List;
import com.yabushan.system.domain.DataApiInfos;

/**
 * 数据服务APIMapper接口
 *
 * @author yabushan
 * @date 2021-03-28
 */
public interface DataApiInfosMapper
{
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
     * @param list 数据服务API
     * @return 结果
     */
    public int bathInsertDataApiInfos(List<DataApiInfos> list);

    /**
     * 修改数据服务API
     *
     * @param dataApiInfos 数据服务API
     * @return 结果
     */
    public int updateDataApiInfos(DataApiInfos dataApiInfos);

    /**
     * 删除数据服务API
     *
     * @param id 数据服务APIID
     * @return 结果
     */
    public int deleteDataApiInfosById(Long id);

    /**
     * 批量删除数据服务API
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDataApiInfosByIds(Long[] ids);
}
