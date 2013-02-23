package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dao.DeptDAO;
import com.pojo.Dept;
import com.pojo.User;
import com.resource.DBConnection;

public class DeptDAOImpl implements DeptDAO
{

    private Connection conn;
    @Override
    public int addDept(Dept sj)
    {
        String sql = "insert into T_DEPT(id, name) values(?,?)";
        PreparedStatement st = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setInt(1, sj.getId());
            st.setString(2, sj.getName());
            st.execute();
            conn.commit();
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

    @Override
    public int deleteDeptById(int id)
    {
        String sql = "delete from T_DEPT where id=?";
        PreparedStatement st = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setInt(1, id);
            st.execute();
            conn.commit();
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

    @Override
    public int updateDeptById(int id, Dept sj)
    {
        String sql = "update T_DEPT SET name=? where id=?";
        PreparedStatement st = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1, sj.getName());
            st.setInt(2, id);
            st.execute();
            conn.commit();
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

    @Override
    public List<Dept> getAllDept()
    {
        String sql = "select id,name from T_DEPT";
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Dept> sjList = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            if (rs != null)
            {
                sjList = new ArrayList<Dept>();
                while (rs.next())
                {
                    Dept sj = new Dept();
                    sj.setId(rs.getInt(1));
                    sj.setName(rs.getString(2));
                    sjList.add(sj);
                }
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
        return sjList;

    }

    @Override
    public Dept quertDeptById(int id)
    {
        String sql = "select id,name from T_DEPT where id=?";
        PreparedStatement st = null;
        ResultSet rs = null;
        Dept dept = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs != null && rs.next())
            {
                dept = new Dept();
                dept.setId(rs.getInt(1));
                dept.setName(rs.getString(2));
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
        return dept;

    }
}
