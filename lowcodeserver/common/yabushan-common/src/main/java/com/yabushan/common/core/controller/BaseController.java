package com.yabushan.common.core.controller;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yabushan.common.core.domain.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yabushan.common.constant.HttpStatus;
import com.yabushan.common.core.page.PageDomain;
import com.yabushan.common.core.page.TableDataInfo;
import com.yabushan.common.core.page.TableSupport;
import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.common.utils.sql.SqlUtil;

/**
 * web层通用数据处理
 *
 * @author ruoyi
 */
public class BaseController
{
    protected final Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport()
        {
            @Override
            public void setAsText(String text)
            {
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    /**
     * 设置请求分页数据
     */
    protected void startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize))
        {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected TableDataInfo getDataTable(List<?> list)
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();

        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        if(pageDomain!=null){
            Integer pageSize = pageDomain.getPageSize();
            int totalPage = Integer.valueOf((int) rspData.getTotal()) / pageSize;
            if (Integer.valueOf((int) rspData.getTotal()) % pageSize != 0) {   //如果有余数
                totalPage += 1;       //总页数+1
            }
            if(pageDomain.getPageNum()>totalPage){
                rspData.setRows(new ArrayList<>());
            }
        }
        return rspData;
    }

    /**
     * 响应请求分页数据(投诉与建议)
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected TableDataInfo getDataTableTsu(List<?> list)
    {
//        PageDomain pageDomain = TableSupport.buildPageRequest();

        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");
        if(list==null){
            rspData.setRows(new ArrayList<>());
            rspData.setTotal(0);
        }else{
            rspData.setRows(list);
            rspData.setTotal(new PageInfo(list).getTotal());
        }
//        if(pageDomain!=null){
//            Integer pageSize = pageDomain.getPageSize();
//            int totalPage = Integer.valueOf((int) rspData.getTotal()) / pageSize;
//            if (Integer.valueOf((int) rspData.getTotal()) % pageSize != 0) {   //如果有余数
//                totalPage += 1;       //总页数+1
//            }
////            if(pageDomain.getPageNum()>totalPage){
////                rspData.setRows(new ArrayList<>());
////            }
//        }
        return rspData;
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(int rows)
    {
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 页面跳转
     */
    public String redirect(String url)
    {
        return StringUtils.format("redirect:{}", url);
    }

    public static TableDataInfo mySetPage(List<?> list){

        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        TableDataInfo rspData =new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(myStartPage(list, pageNum, pageSize));
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;

    }

    public static List myStartPage(List list, Integer pageNum, Integer pageSize){
        if(list ==null){
            return null;
        }
        if(list.size()==0){
            return null;
        }
        Integer count = list.size();//
        Integer pageCount =0;//
        if(count % pageSize ==0){
            pageCount = count / pageSize;
        }else{
            pageCount = count / pageSize +1;
        }
        int fromIndex =0;//
        int toIndex =0;//
        if(pageNum != pageCount){
            fromIndex =(pageNum -1)* pageSize;
            toIndex = fromIndex + pageSize;
        }else{
            fromIndex =(pageNum -1)* pageSize;
            toIndex = count;
        }
        List pageList = list.subList(fromIndex,toIndex);
        return pageList;
    }
}
