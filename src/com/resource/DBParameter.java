package com.resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DBParameter
{
    private String driver;
    private String url;
    private String user;
    private String passwd;
    private static DBParameter dbp = null;
    private DBParameter()
    {
    }
    static
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
    public static DBParameter getInstance()
    {
        if(dbp==null)
        {
            dbp = new DBParameter();
            String path = DBParameter.class.getResource("").getFile()+"db.cfg";
            //System.out.println("path:   "+path);
            File file = new File(path);
            BufferedReader reader = null;
            try
            {
                reader = new BufferedReader(new FileReader(file));
            }
            catch (FileNotFoundException e1)
            {
                e1.printStackTrace();
            }
            String tempString = null;
            try
            {
                Map<String,String> para = new HashMap<String,String>();
                while ((tempString = reader.readLine()) != null)
                {
                    para.put(tempString.split("=")[0],tempString.split("=")[1]);
                }
                reader.close();
                
                dbp.url = para.get("url");
                dbp.user = para.get("user");
                dbp.passwd = para.get("passwd");
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
        }
        return dbp;
    }
    public String getDriver()
    {
        return driver;
    }
    public String getUrl()
    {
        return url;
    }
    public String getUser()
    {
        return user;
    }
    public String getPasswd()
    {
        return passwd;
    }
}
