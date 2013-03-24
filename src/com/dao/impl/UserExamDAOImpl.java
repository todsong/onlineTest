package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.dao.UserExamDAO;
import com.pojo.User;
import com.pojo.UserExam;
import com.resource.DBConnection;

public class UserExamDAOImpl implements UserExamDAO
{
    private Connection conn;

    @Override
    public int addNewUserExam(UserExam ue)
    {
        String sql = "insert into T_USER_EXAM(examId, userId, score, judgeIdList, singleIdList, multiIdList) values(?,?,?,?,?,?)";
        PreparedStatement st = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setInt(1, ue.getExamId());
            st.setString(2, ue.getUserId());
            st.setInt(3, ue.getScore());
            st.setString(4, ue.getJudgeIdList());
            st.setString(5, ue.getSingleIdList());
            st.setString(6, ue.getMultiIdList());
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
    public int updateUserExamResult(UserExam ue)
    {
        String sql = "update T_USER_EXAM SET score=?, judgeAnswerList=?, singleAnswerList=?, multiAnswerList=? where examId=? and userId=?";
        PreparedStatement st = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setInt(1, ue.getScore());
            st.setString(2, ue.getJudgeAnswerList());
            st.setString(3, ue.getSingleAnswerList());
            st.setString(4, ue.getMultiAnswerList());
            st.setInt(5, ue.getExamId());
            st.setString(6, ue.getUserId());
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
    public List<UserExam> queryUserExamByUserId(String userId)
    {
        String sql = "select examId, score, judgeIdList, singleIdList, multiIdList," +
        		" judgeAnswerList, singleAnswerList, multiAnswerList from T_USER_EXAM where userId=?";
        PreparedStatement st = null;
        ResultSet rs = null;
        List<UserExam> ueList = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1, userId);
            rs = st.executeQuery();
            if (rs != null)
            {
                ueList = new ArrayList<UserExam>();
                while (rs.next())
                {
                    UserExam ue = new UserExam();
                    ue.setUserId(userId);
                    ue.setExamId(rs.getInt(1));
                    ue.setScore(rs.getInt(2));
                    ue.setJudgeIdList(rs.getString(3));
                    ue.setSingleIdList(rs.getString(4));
                    ue.setMultiIdList(rs.getString(5));
                    ue.setJudgeAnswerList(rs.getString(6));
                    ue.setSingleAnswerList(rs.getString(7));
                    ue.setMultiAnswerList(rs.getString(8));
                    ueList.add(ue);
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
        return ueList;
    }

    @Override
    public List<UserExam> queryUserExamByExamId(int examId)
    {
        String sql = "select userId, score, judgeIdList, singleIdList, multiIdList," +
                " judgeAnswerList, singleAnswerList, multiAnswerList from T_USER_EXAM where examId=?";
        PreparedStatement st = null;
        ResultSet rs = null;
        List<UserExam> ueList = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setInt(1, examId);
            rs = st.executeQuery();
            if (rs != null)
            {
                ueList = new ArrayList<UserExam>();
                while (rs.next())
                {
                    UserExam ue = new UserExam();
                    ue.setUserId(rs.getString(1));
                    ue.setExamId(examId);
                    ue.setScore(rs.getInt(2));
                    ue.setJudgeIdList(rs.getString(3));
                    ue.setSingleIdList(rs.getString(4));
                    ue.setMultiIdList(rs.getString(5));
                    ue.setJudgeAnswerList(rs.getString(6));
                    ue.setSingleAnswerList(rs.getString(7));
                    ue.setMultiAnswerList(rs.getString(8));
                    ueList.add(ue);
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
        return ueList;
    }

    @Override
    public UserExam queryUniqueUserExam(int examId, String userId)
    {
        String sql = "select score, judgeIdList, singleIdList, multiIdList," +
                " judgeAnswerList, singleAnswerList, multiAnswerList from T_USER_EXAM where examId=? and  userId=?";
        PreparedStatement st = null;
        ResultSet rs = null;
        UserExam ue = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setInt(1, examId);
            st.setString(2, userId);
            rs = st.executeQuery();
            if (rs != null)
            {
                if (rs.next())
                {
                    ue = new UserExam();
                    ue.setUserId(userId);
                    ue.setExamId(examId);
                    ue.setScore(rs.getInt(1));
                    ue.setJudgeIdList(rs.getString(2));
                    ue.setSingleIdList(rs.getString(3));
                    ue.setMultiIdList(rs.getString(4));
                    ue.setJudgeAnswerList(rs.getString(5));
                    ue.setSingleAnswerList(rs.getString(6));
                    ue.setMultiAnswerList(rs.getString(7));
                    
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
        return ue;
    }

    @Override
    public boolean queryNoQuesId(String type, String id)
    {
        String sql = "select singleIdlist from T_USER_EXAM " +
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

