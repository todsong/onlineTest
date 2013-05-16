package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dao.UserScoreDAO;
import com.pojo.Exam;
import com.pojo.UserScore;
import com.resource.DBConnection;

public class UserScoreDAOImpl extends GenericDAOImpl implements UserScoreDAO
{
	private Connection conn;

	public List<UserScore> queryAllScoreByUserId(String userId)
	{
        String sql = "select t1.examName, t1.starttime, t2.score, t1.passscore " +
        		"from t_exam t1, t_user_exam t2" +
        		" where t1.id=t2.examId and t2.userId=?";
        PreparedStatement st = null;
        ResultSet rs = null;
        List<UserScore> usList = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1, userId);
            rs = st.executeQuery();
            if (rs != null)
            {
                usList = new ArrayList<UserScore>();
                while(rs.next())
                {
                    UserScore us = new UserScore();
                    us.setExamName(rs.getString(1));
                    us.setExamDate(rs.getString(2));
                    us.setActScore(rs.getInt(3));
                    us.setPassScore(rs.getInt(4));
                    usList.add(us);
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
        return usList;
	}

}
