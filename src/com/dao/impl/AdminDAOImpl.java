package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dao.AdminDAO;
import com.pojo.Admin;
import com.resource.DBConnection;

public class AdminDAOImpl implements AdminDAO
{

    private Connection conn;
    @Override
    public Admin queryAdminByPwd(String id, String passwd)
    {
        String sql = "select id,passwd from T_ADMIN where id=? and passwd=?";
        PreparedStatement st = null;
        ResultSet rs = null;
        Admin admin = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1, id);
            st.setString(2, passwd);
            rs = st.executeQuery();
            if (rs != null && rs.next())
            {
                admin = new Admin();
                admin.setId(rs.getString(1));
                admin.setPasswd(rs.getString(2));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            try
            {
                rs.close();
                st.close();
                conn.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return admin;
    }
    @Override
    public int updateAdminById(String id, Admin admin)
    {
        String sql = "update T_ADMIN SET passwd=? where id=?";
        PreparedStatement st = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1, admin.getPasswd());
            st.setString(2, id);
            st.execute();
            //conn.commit();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return 1;
        }
        finally
        {
            try
            {
                st.close();
                conn.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return 0;
    }

}
