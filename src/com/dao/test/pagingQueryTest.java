package com.dao.test;

import java.util.Iterator;
import java.util.List;

import com.dao.GenericPagingQueryDAO;
import com.dao.impl.GenericPagingQueryDAOImpl;
import com.pojo.PagingReq;
import com.pojo.PagingResp;
import com.pojo.User;

public class pagingQueryTest
{
    @SuppressWarnings("unchecked")
    public  static void main(String[] args)
    {
        GenericPagingQueryDAO gpq = new GenericPagingQueryDAOImpl();
        PagingReq req = new PagingReq();
        req.setPageNum(1);
        req.setPageSize(10);
//        req.setParaKey("userType");
//        req.setParaValue("1");
//        req.setParaOptr("=");
        req.setTableName("T_USER");
        PagingResp resp = gpq.pagingQuery(req);
        List<User> resList = (List<User>) resp.getResList();
        //System.out.println(resList.size());
        for(Iterator<User> it = resList.iterator(); it.hasNext();)
        {
            User user = (User) it.next();
            System.out.println(user.getId()+", "+user.getName());
        }
        System.out.println(resp.getPageSize()+"行/页  共"+resp.getTotalCount()+"条记录  第"+resp.getPageNum()+"/"+resp.getTotalPage()+"页");
    }
}
