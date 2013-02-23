package com.resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection
{
    private Connection conn;

    public static Connection getConnection()
    {
        DBConnection dbc = new DBConnection();
        DBParameter dbp = DBParameter.getInstance();
        try
        {
            dbc.conn = DriverManager.getConnection(dbp.getUrl(), dbp.getUser(), dbp.getPasswd());
            dbc.conn.setAutoCommit(false);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return dbc.conn;
    }
/*
    public static void main(String[] args)
    {
        File file = new File("src/com/resource/db.cfg");
        BufferedReader reader = null;
        try
        {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null)
            {
                // 显示行号
                System.out.println(tempString);
            }
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (IOException e1)
                {
                }
            }
        }
    }
*/
}
