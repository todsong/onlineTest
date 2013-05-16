package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dao.ExamScoreDAO;
import com.pojo.ExamScore;
import com.pojo.UserScore;
import com.resource.DBConnection;

public class ExamScoreDAOImpl extends GenericDAOImpl implements ExamScoreDAO
{
	private Connection conn;
	
	public List<ExamScore> queryExamScoreByExamId(int examId)
	{
        String sql = "select t2.id, t2.name, t2.dept, t1.score " +
        		"from t_user_exam t1, t_user t2 " +
        		"where t1.examid=? and t1.userid=t2.id;";
        PreparedStatement st = null;
        ResultSet rs = null;
        List<ExamScore> esList = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setInt(1, examId);
            rs = st.executeQuery();
            if (rs != null)
            {
                esList = new ArrayList<ExamScore>();
                while(rs.next())
                {
                    ExamScore es = new ExamScore();
                    es.setUserId(rs.getString(1));
                    es.setUserName(rs.getString(2));
                    es.setDept(rs.getInt(3));
                    es.setScore(rs.getInt(4));
                    esList.add(es);
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
        return esList;
	}

}
