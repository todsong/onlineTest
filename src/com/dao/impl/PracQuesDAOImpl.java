package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.dao.PracQuesDAO;
import com.pojo.PracQues;
import com.pojo.UserExam;
import com.resource.DBConnection;

public class PracQuesDAOImpl implements PracQuesDAO
{
    private Connection conn;

    @Override
    public int addPracQues(PracQues pq)
    {
        String sql = "insert into T_PRAC_QUES(pracId, caseId, judgeIdList, singleIdList, multiIdList) values(?,?,?,?,?)";
        PreparedStatement st = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setInt(1, pq.getPracId());
            st.setInt(2, pq.getCaseId());
            st.setString(3, pq.getJudgeIdList());
            st.setString(4, pq.getSingleIdList());
            st.setString(5, pq.getMultiIdList());
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
    public int deletePracQuesByPracId(int pracId)
    {
        String sql = "delete from T_PRAC_QUES where pracId=?";
        PreparedStatement st = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setInt(1, pracId);
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
    public int updatePracQues(PracQues pq)
    {
        String sql = "update T_PRAC_QUES SET judgeIdList=?, singleIdList=?, multiIdList=? where pracId=? and caseId=?";
        PreparedStatement st = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1, pq.getJudgeIdList());
            st.setString(2, pq.getSingleIdList());
            st.setString(3, pq.getMultiIdList());
            st.setInt(4, pq.getPracId());
            st.setInt(5, pq.getCaseId());
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
    public PracQues getPracQuesById(int pracId, int caseId)
    {
        String sql = "select judgeIdList, singleIdList, multiIdList from T_PRAC_QUES where pracId=? and caseId=?";
        PreparedStatement st = null;
        ResultSet rs = null;
        PracQues pq = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setInt(1, pracId);
            st.setInt(2, caseId);
            rs = st.executeQuery();
            if (rs != null && rs.next())
            {
                pq = new PracQues();
                pq.setPracId(pracId);
                pq.setCaseId(caseId);
                pq.setJudgeIdList(rs.getString(1));
                pq.setSingleIdList(rs.getString(2));
                pq.setMultiIdList(rs.getString(3));
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
        return pq;
    }

    @Override
    public boolean queryNoQuesId(String type, String id)
    {
        String sql = "select singleIdlist from T_PRAC_QUES " +
                "where "+ type + "IdList regexp '([^0-9]|^)"+id+"([^0-9]|$)'";
        Statement st = null;
        ResultSet rs = null;
        boolean res = false;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            if (rs != null)
            {
                if (rs.next())
                {
                    res = false;
                }
                else
                {
                    res = true;
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
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
        return res;
    }
}