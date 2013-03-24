package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.dao.MultiQuesDAO;
import com.pojo.MultiQues;
import com.resource.DBConnection;

public class MultiQuesDAOImpl implements MultiQuesDAO
{
    private Connection conn;

    @Override
    public int addMultiQues(MultiQues jq)
    {
        String sql = "insert into T_MULTI_QUES(qName, qAnswer," +
        		" optionA, optionB, optionC, optionD, optionE, optNum, subjectId,hash,status) values(?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement st = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1, jq.getqName());
            st.setString(2, jq.getqAnswer());
            st.setString(3, jq.getOptionA());
            st.setString(4, jq.getOptionB());
            st.setString(5, jq.getOptionC());
            st.setString(6, jq.getOptionD());
            st.setString(7, jq.getOptionE());
            st.setInt(8, jq.getOptNum());
            st.setInt(9, jq.getSubjectId());
            st.setString(10, jq.getHash());
            st.setInt(11, jq.getStatus());
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
    public int addMultiQuesList(List<MultiQues> jqList)
    {
        String sql = "insert into T_MULTI_QUES(qName, qAnswer, optionA, optionB, optionC, optionD, optionE," +
        		" optNum, subjectId,hash,status) values(?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement st = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            MultiQues jq = null;
            for(Iterator<MultiQues> iter = jqList.iterator(); iter.hasNext(); )
            {
                jq = iter.next();
                st.setString(1, jq.getqName());
                st.setString(2, jq.getqAnswer());
                st.setString(3, jq.getOptionA());
                st.setString(4, jq.getOptionB());
                st.setString(5, jq.getOptionC());
                st.setString(6, jq.getOptionD());
                st.setString(7, jq.getOptionE());
                st.setInt(8, jq.getOptNum());
                st.setInt(9, jq.getSubjectId());
                st.setString(10, jq.getHash());
                st.setInt(11, jq.getStatus());
                st.addBatch();
            }
            st.executeBatch();
            //conn.commit();
            st.clearBatch();
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
    public int deleteMultiQues(int id)
    {
        String sql = "delete from T_MULTI_QUES where id=?";
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
    public int updateMultiQues(int id, MultiQues jq)
    {
        String sql = "update T_MULTI_QUES SET qName=?, qAnswer=?, optionA=?, optionB=?, optionC=?, optionD=?," +
        		" optionE=?, optNum=?, subjectId=?,hash=?,status=? where id=?";
        PreparedStatement st = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1, jq.getqName());
            st.setString(2, jq.getqAnswer());
            st.setString(3, jq.getOptionA());
            st.setString(4, jq.getOptionB());
            st.setString(5, jq.getOptionC());
            st.setString(6, jq.getOptionD());
            st.setString(7, jq.getOptionE());
            st.setInt(8, jq.getOptNum());
            st.setInt(9, jq.getSubjectId());
            st.setString(10, jq.getHash());
            st.setInt(11, jq.getStatus());
            st.setInt(12, id);
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
    public MultiQues queryMultiQuesById(int id)
    {
        String sql = "select id,qName,qAnswer,optionA,optionB,optionC,optionD,optionE,optNum,subjectId,hash,status from T_MULTI_QUES where id=?";
        PreparedStatement st = null;
        ResultSet rs = null;
        MultiQues jq = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs != null && rs.next())
            {
                jq = new MultiQues();
                jq.setId(rs.getInt(1));
                jq.setqName(rs.getString(2));
                jq.setqAnswer(rs.getString(3));
                jq.setOptionA(rs.getString(4));
                jq.setOptionB(rs.getString(5));
                jq.setOptionC(rs.getString(6));
                jq.setOptionD(rs.getString(7));
                jq.setOptionE(rs.getString(8));
                jq.setOptNum(rs.getInt(9));
                jq.setSubjectId(rs.getInt(10));
                jq.setHash(rs.getString(11));
                jq.setStatus(rs.getInt(12));
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
        return jq;
    }

    @Override
    public List<MultiQues> getAllMultiQues()
    {
        String sql = "select id,qName,qAnswer,optionA,optionB,optionC,optionD,optionE,optNum,subjectId,hash,status from T_MULTI_QUES";
        PreparedStatement st = null;
        ResultSet rs = null;
        MultiQues jq = null;
        List<MultiQues> jqList = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            if (rs != null)
            {
                jqList = new ArrayList<MultiQues>();
                while (rs.next())
                {
                    jq = new MultiQues();
                    jq.setId(rs.getInt(1));
                    jq.setqName(rs.getString(2));
                    jq.setqAnswer(rs.getString(3));
                    jq.setOptionA(rs.getString(4));
                    jq.setOptionB(rs.getString(5));
                    jq.setOptionC(rs.getString(6));
                    jq.setOptionD(rs.getString(7));
                    jq.setOptionE(rs.getString(8));
                    jq.setOptNum(rs.getInt(9));
                    jq.setSubjectId(rs.getInt(10));
                    jq.setHash(rs.getString(11));
                    jq.setStatus(rs.getInt(12));
                    jqList.add(jq);
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
        return jqList;
    }
    @Override
    public int getCountSum()
    {
        String sql = "select count(1) from T_MULTI_QUES";
        PreparedStatement st = null;
        ResultSet rs = null;
        int res = 0;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            
            if (rs != null  && rs.next())
            {
                res = rs.getInt(1);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return 0;
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

    @Override
    public List<MultiQues> queryMultiQuesBySubject(int subjectId)
    {
        String sql = "select id,qName,qAnswer,optionA,optionB,optionC,optionD,optionE,optNum,subjectId,hash,status " +
        		"from T_MULTI_QUES where subjectId=?";
        PreparedStatement st = null;
        ResultSet rs = null;
        MultiQues jq = null;
        List<MultiQues> jqList = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setInt(1, subjectId);
            rs = st.executeQuery();
            if (rs != null)
            {
                jqList = new ArrayList<MultiQues>();
                while (rs.next())
                {
                    jq = new MultiQues();
                    jq.setId(rs.getInt(1));
                    jq.setqName(rs.getString(2));
                    jq.setqAnswer(rs.getString(3));
                    jq.setOptionA(rs.getString(4));
                    jq.setOptionB(rs.getString(5));
                    jq.setOptionC(rs.getString(6));
                    jq.setOptionD(rs.getString(7));
                    jq.setOptionE(rs.getString(8));
                    jq.setOptNum(rs.getInt(9));
                    jq.setSubjectId(rs.getInt(10));
                    jq.setHash(rs.getString(11));
                    jq.setStatus(rs.getInt(12));
                    jqList.add(jq);
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
        return jqList;
    }

    @Override
    public int checkUnique(String hash)
    {
        String sql = "select id from T_MULTI_QUES where hash=?";
        PreparedStatement st = null;
        ResultSet rs = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1, hash);
            rs = st.executeQuery();
            if (rs != null && rs.next())
            {
                return rs.getInt("id");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return -1;
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
        return -1;
    }

    @Override
    public List<MultiQues> getAllAvailableBySubjectId(int subjectId)
    {
        String sql = "select id,qName,qAnswer,optionA,optionB,optionC,optionD,optionE,optNum,subjectId,hash,status " +
                "from T_MULTI_QUES where subjectId=? and status=0";
        PreparedStatement st = null;
        ResultSet rs = null;
        MultiQues jq = null;
        List<MultiQues> jqList = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setInt(1, subjectId);
            rs = st.executeQuery();
            if (rs != null)
            {
                jqList = new ArrayList<MultiQues>();
                while (rs.next())
                {
                    jq = new MultiQues();
                    jq.setId(rs.getInt(1));
                    jq.setqName(rs.getString(2));
                    jq.setqAnswer(rs.getString(3));
                    jq.setOptionA(rs.getString(4));
                    jq.setOptionB(rs.getString(5));
                    jq.setOptionC(rs.getString(6));
                    jq.setOptionD(rs.getString(7));
                    jq.setOptionE(rs.getString(8));
                    jq.setOptNum(rs.getInt(9));
                    jq.setSubjectId(rs.getInt(10));
                    jq.setHash(rs.getString(11));
                    jq.setStatus(rs.getInt(12));
                    jqList.add(jq);
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
        return jqList;
    }
}
