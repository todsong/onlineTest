package com.dao.impl;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.dao.GenericDAO;
import com.datatype.InsertResp;
import com.resource.DBConnection;
import com.resource.DBResManager;

public class GenericDAOImpl implements GenericDAO
{
    public InsertResp insert(Object obj)
    {
        InsertResp resp = new InsertResp();
        resp.setResult("ok");
        Connection conn = null;
        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder("insert into ");

        Class clazz = obj.getClass();
        final String tableName = "t_"+clazz.getSimpleName();
        sql.append(tableName).append("(");

        final Field[] fields = clazz.getDeclaredFields();

        int fieldCount = 0;
        for (Field f : fields)
        {
            if (fieldCount != 0)
            {
                sql.append(",");
            }
            String name = f.getName();
            if(!name.equals("serialVersionUID"))
            {
                sql.append(name);
                fieldCount++;
            }
        }
        sql.append(") values(");

        for (int i = 0; i < fieldCount; i++)
        {
            if (i != 0)
            {
                sql.append(",");
            }
            sql.append("?");
        }
        sql.append(")");

        try
        {
            conn = DBConnection.getConnection();
            pst = conn.prepareStatement(sql.toString());

            AccessibleObject.setAccessible(fields, true);
            int count = 1;
            for (Field f : fields)
            {
                if(f.getName().equals("serialVersionUID"))
                {
                    continue;
                }
                Class t = f.getType();
                if (t.isPrimitive() || t == String.class)
                {
                    pst.setObject(count, f.get(obj));
                } else
                {
                    if (t == java.util.Date.class)
                    {
                        java.util.Date uDate = (java.util.Date) f.get(obj);
                        pst.setObject(count,
                                new java.sql.Date(uDate.getTime()));
                    }
                }
                count++;
            }
            pst.execute();
        } catch (SQLException e)
        {
            e.printStackTrace();
            resp.setResult("error");
        } catch (IllegalArgumentException e)
        {
            e.printStackTrace();
            resp.setResult("error");

        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
            resp.setResult("error");
        }
        finally
        {
            DBResManager.closePreStatement(pst);
            DBResManager.closeConnection(conn);
        }
        return resp;
    }
}
