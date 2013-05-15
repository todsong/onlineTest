package com.dao;

import com.datatype.PagingReq;
import com.datatype.PagingResp;

public interface GenericPagingQueryDAO
{
    public PagingResp pagingQuery(PagingReq req);
}
