package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dao.ExamDAO;
import com.pojo.Exam;
import com.resource.DBConnection;

public class ExamDAOImpl implements ExamDAO {
	private Connection conn;
	
	@Override
	public int addExam(Exam exam) {
		String sql = "insert into T_EXAM(examName, startTime, endTime, singleNum, singleScore," +
				" multiNum, multiScore, judgeNum, judgeScore, passScore, examType, subjectId)" +
				" values(?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement st = null;
		ResultSet rs = null;
		int res=0;
		try
		{
			conn = DBConnection.getConnection();
			st = conn.prepareStatement(sql);
			st.setString(1, exam.getExamName());
			st.setString(2, exam.getStartTime());
			st.setString(3, exam.getEndTime());
			st.setInt(4, exam.getSingleNum());
			st.setInt(5, exam.getSingleScore());
			st.setInt(6, exam.getMutliNum());
			st.setInt(7, exam.getMutliScore());
			st.setInt(8, exam.getJudgeNum());
			st.setInt(9, exam.getJudgeScore());
            st.setInt(10, exam.getPassScore());
            st.setString(11, exam.getExamType());
            st.setInt(12, exam.getSubjectId());
			st.execute();
			sql = "select @@IDENTITY";
			st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            if (rs != null && rs.next())
            {
                String resStr = rs.getString(1);
                if(resStr!=null)
                {
                    res = Integer.parseInt(resStr);
                }
            }
			conn.commit();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return -1;
		}
		finally
		{
			try {
			    rs.close();
				st.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return res;
	}

	@Override
	public int deleteExam(int id) {
		String sql = "delete from T_EXAM where id=?";
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
			try {
				st.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public int updateExam(int id, Exam exam) {
		String sql = "update T_EXAM SET examName=?, startTime=?, endTime=?," +
				" singleNum=?, singleScore=?,multiNum=?, multiScore=?," +
				" judgeNum=?, judgeScore=?, passScore=?, examType=?, subjectId=? where id=?";
		PreparedStatement st = null;
		try
		{
			conn = DBConnection.getConnection();
			st = conn.prepareStatement(sql);
			st.setString(1, exam.getExamName());
			st.setString(2, exam.getStartTime());
			st.setString(3, exam.getEndTime());
			st.setInt(4, exam.getSingleNum());
			st.setInt(5, exam.getSingleScore());
			st.setInt(6, exam.getMutliNum());
			st.setInt(7, exam.getMutliScore());
			st.setInt(8, exam.getJudgeNum());
			st.setInt(9, exam.getJudgeScore());
            st.setInt(10, exam.getPassScore());
            st.setString(11, exam.getExamType());
            st.setInt(12, exam.getSubjectId());
            st.setInt(13, id);
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
			try {
				st.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public Exam queryExamById(int id) {
		String sql = "select id, examName, startTime, endTime, singleNum, singleScore," +
				" multiNum, multiScore, judgeNum, judgeScore, passScore, examType, subjectId from T_EXAM" +
				" where id=?";
		PreparedStatement st = null;
		ResultSet rs = null;
		Exam exam = null;
		try
		{
			conn = DBConnection.getConnection();
			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs != null && rs.next())
			{
				exam = new Exam();
				exam.setId(id);
				exam.setExamName(rs.getString(2));
				exam.setStartTime(rs.getString(3));
				exam.setEndTime(rs.getString(4));
				exam.setSingleNum(rs.getInt(5));
				exam.setSingleScore(rs.getInt(6));
				exam.setMutliNum(rs.getInt(7));
				exam.setMutliScore(rs.getInt(8));
				exam.setJudgeNum(rs.getInt(9));
				exam.setJudgeScore(rs.getInt(10));
				exam.setPassScore(rs.getInt(11));
                exam.setExamType(rs.getString(12));
                exam.setSubjectId(rs.getInt(13));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		finally
		{
			try {
				rs.close();
				st.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return exam;
	}

	@Override
	public List<Exam> queryExamByTime(String date, String examType) {
		String sql = "select id, examName, startTime, endTime, singleNum, singleScore," +
				" multiNum, multiScore, judgeNum, judgeScore, passScore, examType, subjectId from T_EXAM" +
				" where startTime like ? and examType = ?";
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Exam> examList = null;
		try
		{
			conn = DBConnection.getConnection();
			st = conn.prepareStatement(sql);
			st.setString(1, date+"%");
			st.setString(2, examType);
			rs = st.executeQuery();
			if (rs != null)
			{
				examList = new ArrayList<Exam>();
				while(rs.next())
				{
					Exam exam = new Exam();
					exam = new Exam();
					exam.setId(rs.getInt(1));
					exam.setExamName(rs.getString(2));
					exam.setStartTime(rs.getString(3));
					exam.setEndTime(rs.getString(4));
					exam.setSingleNum(rs.getInt(5));
					exam.setSingleScore(rs.getInt(6));
					exam.setMutliNum(rs.getInt(7));
					exam.setMutliScore(rs.getInt(8));
					exam.setJudgeNum(rs.getInt(9));
					exam.setJudgeScore(rs.getInt(10));
                    exam.setPassScore(rs.getInt(11));
                    exam.setExamType(rs.getString(12));
                    exam.setSubjectId(rs.getInt(13));
					examList.add(exam);
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
			try {
				rs.close();
				st.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return examList;
	}

    @Override
    public List<Exam> getRecentEaxm(String examType, int days)
    {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        long current = date.getTime();
        long monthLSecond = (long)(days*24*3600)*(long)1000;
        String before = formatter.format(new Date(current - monthLSecond));
        String after = formatter.format(new Date(current + monthLSecond));
        String sql = "select id, examName, startTime, endTime, singleNum, singleScore," +
                " multiNum, multiScore, judgeNum, judgeScore, passScore, examType, subjectId from T_EXAM" +
                " where startTime <= ? and endTime >= ? and examType = ?";
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Exam> examList = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1, after);
            st.setString(2, before);
            st.setString(3, examType);
            rs = st.executeQuery();
            if (rs != null)
            {
                examList = new ArrayList<Exam>();
                while(rs.next())
                {
                    Exam exam = new Exam();
                    exam = new Exam();
                    exam.setId(rs.getInt(1));
                    exam.setExamName(rs.getString(2));
                    exam.setStartTime(rs.getString(3));
                    exam.setEndTime(rs.getString(4));
                    exam.setSingleNum(rs.getInt(5));
                    exam.setSingleScore(rs.getInt(6));
                    exam.setMutliNum(rs.getInt(7));
                    exam.setMutliScore(rs.getInt(8));
                    exam.setJudgeNum(rs.getInt(9));
                    exam.setJudgeScore(rs.getInt(10));
                    exam.setPassScore(rs.getInt(11));
                    exam.setExamType(rs.getString(12));
                    exam.setSubjectId(rs.getInt(13));
                    examList.add(exam);
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
            try {
                rs.close();
                st.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return examList;
    }

    @Override
    public List<Exam> getAllEaxm(String examType)
    {
        String sql = "select id, examName, startTime, endTime, singleNum, singleScore," +
                " multiNum, multiScore, judgeNum, judgeScore, passScore, examType, subjectId from T_EXAM" +
                " where examType = ?";
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Exam> examList = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1, examType);
            rs = st.executeQuery();
            if (rs != null)
            {
                examList = new ArrayList<Exam>();
                while(rs.next())
                {
                    Exam exam = new Exam();
                    exam.setId(rs.getInt(1));
                    exam.setExamName(rs.getString(2));
                    exam.setStartTime(rs.getString(3));
                    exam.setEndTime(rs.getString(4));
                    exam.setSingleNum(rs.getInt(5));
                    exam.setSingleScore(rs.getInt(6));
                    exam.setMutliNum(rs.getInt(7));
                    exam.setMutliScore(rs.getInt(8));
                    exam.setJudgeNum(rs.getInt(9));
                    exam.setJudgeScore(rs.getInt(10));
                    exam.setPassScore(rs.getInt(11));
                    exam.setExamType(rs.getString(12));
                    exam.setSubjectId(rs.getInt(13));
                    examList.add(exam);
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
            try {
                rs.close();
                st.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return examList;
    }

    @Override
    public int getMaxQuesNumByType(String type)
    {
        String sql = "select max("+type+"Num) from t_exam";
        Statement st = null;
        ResultSet rs = null;
        int res = 0;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            if (rs != null && rs.next())
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
            try {
                rs.close();
                st.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    @Override
    public List<Exam> queryExamBySubject(int subjectId, String examType)
    {
        String sql = "select id, examName, startTime, endTime, singleNum, singleScore," +
                " multiNum, multiScore, judgeNum, judgeScore, passScore, examType, subjectId from T_EXAM" +
                " where subjectId=? and examType = ?";
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Exam> examList = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setInt(1, subjectId);
            st.setString(2, examType);
            rs = st.executeQuery();
            if (rs != null)
            {
                examList = new ArrayList<Exam>();
                while(rs.next())
                {
                    Exam exam = new Exam();
                    exam = new Exam();
                    exam.setId(rs.getInt(1));
                    exam.setExamName(rs.getString(2));
                    exam.setStartTime(rs.getString(3));
                    exam.setEndTime(rs.getString(4));
                    exam.setSingleNum(rs.getInt(5));
                    exam.setSingleScore(rs.getInt(6));
                    exam.setMutliNum(rs.getInt(7));
                    exam.setMutliScore(rs.getInt(8));
                    exam.setJudgeNum(rs.getInt(9));
                    exam.setJudgeScore(rs.getInt(10));
                    exam.setPassScore(rs.getInt(11));
                    exam.setExamType(rs.getString(12));
                    exam.setSubjectId(rs.getInt(13));
                    examList.add(exam);
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
            try {
                rs.close();
                st.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return examList;
    }
}
