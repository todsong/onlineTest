package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.dao.JudgeQuesDAO;
import com.datatype.InsertResp;
import com.pojo.JudgeQues;
import com.resource.DBConnection;

public class JudgeQuesDAOImpl extends GenericDAOImpl implements JudgeQuesDAO
{
    private Connection conn;

    @Override
    public int addJudgeQues(JudgeQues jq)
    {
        InsertResp resp = insert(jq);
        if("error".equals(resp.getResult()))
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }

    @Override
    public int addJudgeQuesList(List<JudgeQues> jqList)
    {
        String sql = "insert into T_JUDGE_QUES(qName, qAnswer, subjectId, hash, status) values(?,?,?,?,?)";
        PreparedStatement st = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            JudgeQues jq = null;
            int count=0;
            for(Iterator<JudgeQues> iter = jqList.iterator(); iter.hasNext(); count++)
            {
                jq = iter.next();
                st.setString(1, jq.getqName());
                st.setString(2, jq.getqAnswer());
                st.setInt(3, jq.getSubjectId());
                st.setString(4, jq.getHash());
                st.setInt(5, jq.getStatus());
                st.addBatch();
                if(count%500==0)
                {
                    st.executeBatch();
                    st.clearBatch();
                }
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
    public int deleteJudgeQues(int id)
    {
        String sql = "delete from T_JUDGE_QUES where id=?";
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
    public int updateJudgeQues(int id, JudgeQues jq)
    {
        String sql = "update T_JUDGE_QUES SET qName=?, qAnswer=?, subjectId=?, hash=?, status=? where id=?";
        PreparedStatement st = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1, jq.getqName());
            st.setString(2, jq.getqAnswer());
            st.setInt(3, jq.getSubjectId());
            st.setString(4, jq.getHash());
            st.setInt(5, jq.getStatus());
            st.setInt(6, id);
            st.execute();
            //conn.commit();
        }// UPDATE t_user SET passwd = 'Fred' WHERE passwd = 'asd';
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
    public JudgeQues queryJudgeQuesById(int id)
    {
        String sql = "select id,qName,qAnswer,subjectId, hash, status from T_JUDGE_QUES where id=?";
        PreparedStatement st = null;
        ResultSet rs = null;
        JudgeQues jq = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs != null && rs.next())
            {
                jq = new JudgeQues();
                jq.setId(rs.getInt(1));
                jq.setqName(rs.getString(2));
                jq.setqAnswer(rs.getString(3));
                jq.setSubjectId(rs.getInt(4));
                jq.setHash(rs.getString(5));
                jq.setStatus(rs.getInt(6));
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

//    @Override
//    public List<JudgeQues> getJudgeQuesRandom(int num)
//    {
//        // TODO Auto-generated method stub
//        return null;
//    }

    @Override
    public List<JudgeQues> getAllJudgeQues()
    {
        String sql = "select id,qName,qAnswer,subjectId, hash, status from T_JUDGE_QUES";
        PreparedStatement st = null;
        ResultSet rs = null;
        JudgeQues jq = null;
        List<JudgeQues> jqList = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            if (rs != null)
            {
                
            }
            if (rs != null)
            {
                jqList = new ArrayList<JudgeQues>();
                while (rs.next())
                {
                    jq = new JudgeQues();
                    jq.setId(rs.getInt(1));
                    jq.setqName(rs.getString(2));
                    jq.setqAnswer(rs.getString(3));
                    jq.setSubjectId(rs.getInt(4));
                    jq.setHash(rs.getString(5));
                    jq.setStatus(rs.getInt(6));
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
        String sql = "select count(1) from T_JUDGE_QUES";
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
    public List<JudgeQues> queryJudgeQuesBySubject(int subjectId)
    {
        String sql = "select id,qName,qAnswer, subjectId, hash, status from T_JUDGE_QUES where subjectId=?";
        PreparedStatement st = null;
        ResultSet rs = null;
        JudgeQues jq = null;
        List<JudgeQues> jqList = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setInt(1, subjectId);
            rs = st.executeQuery();
            
            if (rs != null)
            {
                jqList = new ArrayList<JudgeQues>();
                while (rs.next())
                {
                    jq = new JudgeQues();
                    jq.setId(rs.getInt(1));
                    jq.setqName(rs.getString(2));
                    jq.setqAnswer(rs.getString(3));
                    jq.setSubjectId(rs.getInt(4));
                    jq.setHash(rs.getString(5));
                    jq.setStatus(rs.getInt(6));
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
        String sql = "select id from T_JUDGE_QUES where hash=?";
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
    public List<JudgeQues> getAllAvailableBySubjectId(int subjectId)
    {
        String sql = "select id,qName,qAnswer, subjectId, hash, status from T_JUDGE_QUES where subjectId=? and status=0";
        PreparedStatement st = null;
        ResultSet rs = null;
        JudgeQues jq = null;
        List<JudgeQues> jqList = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setInt(1, subjectId);
            rs = st.executeQuery();
            
            if (rs != null)
            {
                jqList = new ArrayList<JudgeQues>();
                while (rs.next())
                {
                    jq = new JudgeQues();
                    jq.setId(rs.getInt(1));
                    jq.setqName(rs.getString(2));
                    jq.setqAnswer(rs.getString(3));
                    jq.setSubjectId(rs.getInt(4));
                    jq.setHash(rs.getString(5));
                    jq.setStatus(rs.getInt(6));
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
