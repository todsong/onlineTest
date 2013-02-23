package com.pojo;

import java.util.List;

public class PagingResp
{
    private List<?> resList;
    private int totalCount;
    private int totalPage;
    private int pageSize;
    private int pageNum;
    public List<?> getResList()
    {
        return resList;
    }
    public void setResList(List<?> resList)
    {
        this.resList = resList;
    }
    public int getTotalCount()
    {
        return totalCount;
    }
    public void setTotalCount(int totalCount)
    {
        this.totalCount = totalCount;
    }
    public int getTotalPage()
    {
        return totalPage;
    }
    public void setTotalPage(int totalPage)
    {
        this.totalPage = totalPage;
    }
    public int getPageSize()
    {
        return pageSize;
    }
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    public int getPageNum()
    {
        return pageNum;
    }
    public void setPageNum(int pageNum)
    {
        this.pageNum = pageNum;
    }
}
