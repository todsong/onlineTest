package com.resource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBResManager
{
    public static void closeConnection(Connection conn)
    {
        if(conn!=null)
        {
            try
            {
                conn.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
    public static void closePreStatement(PreparedStatement pst)
    {
        if(pst!=null)
        {
            try
            {
                pst.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
    public static void closeResultSet(ResultSet rst)
    {
        if(rst!=null)
        {
            try
            {
                rst.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
            
        }
    }
}
