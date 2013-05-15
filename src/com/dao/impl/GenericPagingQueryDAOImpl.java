package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dao.GenericPagingQueryDAO;
import com.datatype.PagingReq;
import com.datatype.PagingResp;
import com.datatype.Paras;
import com.pojo.Exam;
import com.pojo.JudgeQues;
import com.pojo.MultiQues;
import com.pojo.SingleQues;
import com.pojo.User;
import com.pojo.UserExam;
import com.resource.DBConnection;

public class GenericPagingQueryDAOImpl implements GenericPagingQueryDAO
{

    private Connection conn;
    @Override
    public PagingResp pagingQuery(PagingReq req)
    {
        StringBuilder sql = new StringBuilder("");//SELECT a FROM foo WHERE b = 1 LIMIT 100,10;
        int bindCount=0;
        String[] inValues=null;
        StringBuilder condition = new StringBuilder("");
        if(req.getTableName()==null)
        {
            return null;
        }
        sql.append("select * from "+req.getTableName());
        List<Paras> paraList = req.getParaList();
        if(paraList!=null && !paraList.isEmpty())
        {
            condition.append(" where ");
            for(int i=0;i<paraList.size();i++)
            {
                if(i!=0)
                {
                    condition.append(paraList.get(i).getRelation()).append(" ");
                }
                condition.append(paraList.get(i).getParaKey()).append(" ").append(paraList.get(i).getParaOptr()).append(" ");
                if(!paraList.get(i).getParaOptr().toUpperCase().equals("IN"))
                {
                    condition.append(" ? ");
                    bindCount++;
                }
                else
                {
                    inValues = paraList.get(i).getParaValue().split(",");
                    condition.append("(");
                    for(int j=0;j<inValues.length;j++)
                    {
                        condition.append("?");
                        bindCount++;
                        if(j!=inValues.length-1)
                        {
                            condition.append(",");
                        }
                    }
                    condition.append(") ");
                }
            }
            sql.append(condition);
        }
        if(req.getOrderKey()!=null && req.getOrderValue()!=null)
        {
            sql.append(" order by "+req.getOrderKey()+" "+req.getOrderValue());
        }
        boolean page = false;
        if(req.getPageNum()!=-1 && req.getPageSize()!=-1)
        {
            sql.append(" limit ?,?");
            page=true;
        }

        //System.out.println(sql.toString());
        PreparedStatement st = null;
        ResultSet rs = null;
        conn = DBConnection.getConnection();

        PagingResp resp = null;
        try
        {
            st = conn.prepareStatement(sql.toString());
            if(bindCount!=0)
            {
                for(int i=0,idx=1;i<paraList.size();i++,idx++)
                {
                    if(paraList.get(i).getParaOptr().equals("like"))
                        st.setString(idx, "%"+paraList.get(i).getParaValue()+"%");
                    else if(paraList.get(i).getParaOptr().toUpperCase().equals("IN"))
                    {
                        for(int j=0;j<inValues.length;j++,idx++)
                        {
                            st.setInt(idx, Integer.parseInt(inValues[j]));
                        }
                    }
                    else
                        st.setString(idx, paraList.get(i).getParaValue());    
                }
            }
            if(page==true)
            {
                int startNum=(req.getPageNum()-1)*req.getPageSize();
                int endNum=req.getPageSize();
                //System.out.println(startNum+","+endNum);
                st.setInt(bindCount+1, startNum);
                st.setInt(bindCount+2, endNum);
                //System.out.println(startNum+","+endNum);
            }
//            System.out.println(sql.toString());
            
            rs = st.executeQuery();
            if (rs != null)
            {
                resp = new PagingResp();
                if(req.getTableName().toUpperCase().equals("T_USER"))
                {
                    List<User> userList = new ArrayList<User>();
                    while (rs.next())
                    {
                        User user = new User();
                        user.setId(rs.getString("id"));
                        user.setPasswd(rs.getString("passwd"));
                        user.setName(rs.getString("name"));
                        user.setDept(rs.getInt("dept"));
                        user.setTelephone(rs.getString("telephone"));
                        user.setStatus(rs.getString("status"));
                        userList.add(user);
                    }
                    resp.setResList(userList);
                }
                else if(req.getTableName().toUpperCase().equals("T_EXAM"))
                {
                    List<Exam> examList = new ArrayList<Exam>();
                    while(rs.next())
                    {
                        Exam exam = new Exam();
                        exam.setId(rs.getInt("id"));
                        exam.setExamName(rs.getString("examName"));
                        exam.setStartTime(rs.getString("startTime"));
                        exam.setEndTime(rs.getString("endTime"));
                        exam.setSingleNum(rs.getString("singleNum"));
                        exam.setSingleScore(rs.getInt("singleScore"));
                        exam.setMutliNum(rs.getString("multiNum"));
                        exam.setMutliScore(rs.getInt("multiscore"));
                        exam.setJudgeNum(rs.getString("judgeNum"));
                        exam.setJudgeScore(rs.getInt("judgeScore"));
                        exam.setPassScore(rs.getInt("passScore"));
                        exam.setExamType(rs.getString("examType"));
                        examList.add(exam);
                    }
                    resp.setResList(examList);
                }
                else if(req.getTableName().toUpperCase().equals("T_JUDGE_QUES"))
                {
                    List<JudgeQues> jqList = new ArrayList<JudgeQues>();
                    while (rs.next())
                    {
                        JudgeQues jq = new JudgeQues();
                        jq.setId(rs.getInt("id"));
                        jq.setqName(rs.getString("qName"));
                        jq.setqAnswer(rs.getString("qAnswer"));
                        jq.setSubjectId(rs.getInt("subjectId"));
                        jq.setHash(rs.getString("hash"));
                        jq.setStatus(rs.getInt("status"));
                        jqList.add(jq);
                    }
                    resp.setResList(jqList);
                }
                else if(req.getTableName().toUpperCase().equals("T_SINGLE_QUES"))
                {
                    List<SingleQues> sqList = new ArrayList<SingleQues>();
                    while (rs.next())
                    {
                        SingleQues sq = new SingleQues();
                        sq.setId(rs.getInt("id"));
                        sq.setqName(rs.getString("qName"));
                        sq.setqAnswer(rs.getString("qAnswer"));
                        sq.setOptionA(rs.getString("optionA"));
                        sq.setOptionB(rs.getString("optionB"));
                        sq.setOptionC(rs.getString("optionC"));
                        sq.setOptionD(rs.getString("optionD"));
                        sq.setOptionE(rs.getString("optionE"));
                        sq.setOptNum(rs.getInt("optNum"));
                        sq.setSubjectId(rs.getInt("subjectId"));
                        sq.setStatus(rs.getInt("status"));
                        sq.setHash(rs.getString("hash"));
                        sqList.add(sq);
                    }
                    resp.setResList(sqList);
                }
                else if(req.getTableName().toUpperCase().equals("T_MULTI_QUES"))
                {
                    List<MultiQues> mqList = new ArrayList<MultiQues>();
                    while (rs.next())
                    {
                        MultiQues mq = new MultiQues();
                        mq.setId(rs.getInt("id"));
                        mq.setqName(rs.getString("qName"));
                        mq.setqAnswer(rs.getString("qAnswer"));
                        mq.setOptionA(rs.getString("optionA"));
                        mq.setOptionB(rs.getString("optionB"));
                        mq.setOptionC(rs.getString("optionC"));
                        mq.setOptionD(rs.getString("optionD"));
                        mq.setOptionE(rs.getString("optionE"));
                        mq.setOptNum(rs.getInt("optNum"));
                        mq.setSubjectId(rs.getInt("subjectId"));
                        mq.setStatus(rs.getInt("status"));
                        mqList.add(mq);
                    }
                    resp.setResList(mqList);
                }
                else if(req.getTableName().toUpperCase().equals("T_USER_EXAM"))
                {
                    List<UserExam> ueList = new ArrayList<UserExam>();
                    if (rs.next())
                    {
                        UserExam ue = new UserExam();
                        ue.setUserId(rs.getString("userId"));
                        ue.setExamId(rs.getInt("examId"));
                        ue.setScore(rs.getInt("score"));
                        ueList.add(ue);
                    }
                    resp.setResList(ueList);
                }
            }

            resp.setPageNum(req.getPageNum());
            resp.setPageSize(req.getPageSize());
            
            rs.close();
            st.close();
            sql = new StringBuilder("select count(1) from " + req.getTableName());
            sql.append(condition);
//            System.out.println(sql.toString());
            st = conn.prepareStatement(sql.toString());
            if(paraList!=null && !paraList.isEmpty())
            {
                for(int i=0,idx=1;i<paraList.size();i++,idx++)
                {
                    if(paraList.get(i).getParaOptr().equals("like"))
                        st.setString(idx, "%"+paraList.get(i).getParaValue()+"%");
                    else if(paraList.get(i).getParaOptr().toUpperCase().equals("IN"))
                    {
                        for(int j=0;j<inValues.length;j++,idx++)
                        {
                            st.setInt(idx, Integer.parseInt(inValues[j]));
                        }
                    }
                    else
                        st.setString(idx, paraList.get(i).getParaValue());    
                }
            }
            rs = st.executeQuery();
            if (rs != null && rs.next())
            {
                int total = rs.getInt(1);
                resp.setTotalCount(total);
                int tmp = 0;
                if(total%resp.getPageSize()!=0)
                    tmp = 1;
                resp.setTotalPage(total/resp.getPageSize()+tmp);
            }
            
        }
        catch (SQLException e)
        {
            e.printStackTrace();
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
        return resp;
    }
}
