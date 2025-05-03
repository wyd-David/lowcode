package com.yabushan.datasource.utils.pages;

import java.io.Serializable;
import java.util.List;

/**
 * 分页对象. 包含当前页数据及分页信息
 *
 * @author
 *
 */
public class PageFinderDTO<T> implements Serializable {

    private static final long serialVersionUID = -3193453346742977879L;
    /**
     * 当前页数
     */
    private int pageNum;
    /**
     * 每页条数
     */
    private int pageSize;
    /**
     * 分页查询数据
     */
    private List<T> data;
    /**
     * 总条数
     */
    private int totalPageNum;
    /**
     * 总条数
     */
    private long totalCount;

    /**
     * 是否有上一页
     */
    private boolean hasPrevious = false;

    /**
     * 是否有下一页
     */
    private boolean hasNext = false;


    /**
     * 构造方法
     */
    public PageFinderDTO() {}

    /**
     * 构造方法
     * @param pageNum 当前页数
     * @param totalCount 总条数
     */
    public PageFinderDTO(int pageNum, long totalCount) {
        this.pageNum = pageNum;
        this.totalCount = totalCount;
        this.totalPageNum = getTotalPageCount();
        refresh();
    }


    /**
     * 构造方法
     * @param pageNum 当前页数
     * @param pageSize 每页条数
     * @param totalCount 总条数
     */
    public PageFinderDTO(int pageNum, int pageSize, long totalCount) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.totalPageNum = getTotalPageCount();
        refresh();
    }

    /**
     * 构造方法
     * @param pageNum 当前页数
     * @param pageSize 每页条数
     * @param totalCount 总条数
     * @param data 分页查询数据
     */
    public PageFinderDTO(int pageNum, int pageSize, long totalCount, List<T> data) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.totalPageNum = getTotalPageCount();
        this.data = data;
        refresh();
    }

    /**
     * 获取总页数
     */
    private final int getTotalPageCount() {
        if (totalCount % pageSize == 0) {
            return (int) (totalCount / pageSize);
        } else {
            return (int) (totalCount / pageSize + 1);
        }
    }

    /**
     * 刷新当前分页对象数据
     */
    private void refresh() {
        if(pageNum==0 && (totalPageNum==0 || totalPageNum==1)){
            hasPrevious = false;
            hasNext = false;
        }else if (pageNum==0 && totalPageNum>0) {
            hasPrevious = false;
            hasNext = true;
        } else if (pageNum >totalPageNum ) {
            hasPrevious = true;
            hasNext = false;
        }else if (pageNum == (totalPageNum-1)) {
            hasPrevious = true;
            hasNext = false;
        } else {
            hasPrevious = true;
            hasNext = true;
        }
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

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }

    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    @Override
    public String toString() {
        return "PageFinderDTO{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", data=" + data +
                ", totalPageNum=" + totalPageNum +
                ", totalCount=" + totalCount +
                ", hasPrevious=" + hasPrevious +
                ", hasNext=" + hasNext +
                '}';
    }
}
