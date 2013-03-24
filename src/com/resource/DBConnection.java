package com.resource;

import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBConnection
{
    private static ComboPooledDataSource cpds = new ComboPooledDataSource();
    {
        try
        {
            cpds.getConnection().close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public static Connection getConnection()
    {
        Connection conn = null;
        try
        {
            conn = cpds.getConnection();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return conn;
    }
}
