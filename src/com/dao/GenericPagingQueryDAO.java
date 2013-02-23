package com.dao;

import com.pojo.PagingReq;
import com.pojo.PagingResp;

public interface GenericPagingQueryDAO
{
    public PagingResp pagingQuery(PagingReq req);
}
