package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dao.SubjectDAO;
import com.pojo.Subject;
import com.pojo.Subject;
import com.pojo.User;
import com.resource.DBConnection;

public class SubjectDAOImpl implements SubjectDAO
{

    private Connection conn;
    @Override
    public int addSubject(Subject sj)
    {
        String sql = "insert into T_SUBJECT(id, name) values(?,?)";
        PreparedStatement st = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setInt(1, sj.getId());
            st.setString(2, sj.getName());
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

    @Override
    public int deleteSubjectById(int id)
    {
        String sql = "delete from T_SUBJECT where id=?";
        PreparedStatement st = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setInt(1, id);
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

    @Override
    public int updateSubjectById(int id, Subject sj)
    {
        String sql = "update T_SUBJECT SET name=? where id=?";
        PreparedStatement st = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1, sj.getName());
            st.setInt(2, id);
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

    @Override
    public List<Subject> getAllSubject()
    {
        String sql = "select id,name from T_SUBJECT";
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Subject> sjList = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            if (rs != null)
            {
                sjList = new ArrayList<Subject>();
                while (rs.next())
                {
                    Subject sj = new Subject();
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
    public Subject quertSubjectById(int id)
    {
        String sql = "select id,name from T_SUBJECT where id=?";
        PreparedStatement st = null;
        ResultSet rs = null;
        Subject subject = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs != null && rs.next())
            {
                subject = new Subject();
                subject.setId(rs.getInt(1));
                subject.setName(rs.getString(2));
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
        return subject;

    }

}
