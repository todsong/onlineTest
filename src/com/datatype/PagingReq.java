package com.datatype;

import java.io.Serializable;
import java.util.List;


public class PagingReq implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -8691346982680982868L;
    private String orderKey;
    private String orderValue;
    private int pageSize;
    private int pageNum;
    private String tableName;
    private List<Paras> paraList;
    
    public PagingReq()
    {
        pageSize = -1;
        pageNum = -1;
    }

    public String getOrderKey()
    {
        return orderKey;
    }

    public void setOrderKey(String orderKey)
    {
        this.orderKey = orderKey;
    }

    public String getOrderValue()
    {
        return orderValue;
    }

    public void setOrderValue(String orderValue)
    {
        this.orderValue = orderValue;
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

    public String getTableName()
    {
        return tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public List<Paras> getParaList()
    {
        return paraList;
    }

    public void setParaList(List<Paras> paraList)
    {
        this.paraList = paraList;
    }
}
