package com.yabushan.datasource.utils.pages;

/**
 * 分页对象
 * @author
 */
public class QueryPageDTO {

    public static int DEFAULT_PAGE_NUM = 1;
    public static int DEFAULT_PAGE_SIZE = 10;
    public static int MAX_PAGE_SIZE = 100000;

    /**
     * 当前页数
     */
    private int pageNum;
    /**
     * 每页条数
     */
    private int pageSize;
    
   
    /**
     * 构造方法
     */
    public QueryPageDTO() {
        this.pageNum = DEFAULT_PAGE_NUM;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    /**
     *  构造方法
     * @param pageNum 当前页数
     * @param pageSize 每页条数
     */
    public QueryPageDTO(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


   
}


