<%@page import="com.pojo.UserScore"%>
<%@page import="com.dao.impl.UserScoreDAOImpl"%>
<%@page import="com.dao.UserScoreDAO"%>
<%@page import="java.util.Date"%>
<%@page import="com.pojo.UserExam"%>
<%@page import="com.dao.UserExamDAO"%>
<%@page import="com.util.ExamSorter"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Collections"%>
<%@page import="com.util.TimeUtil"%>

<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
<h4>我的考试记录</h4>
<hr>
<%
String userId = (String) session.getAttribute("id");
UserScoreDAO usd = new UserScoreDAOImpl();
List<UserScore> usList = usd.queryAllScoreByUserId(userId);
if(usList==null || usList.size()==0)
{
    out.println("没有考试记录");
}
else
{    
%>
<table border="1">
<tr>
  <td  width="200">考试名称</td>
  <td  width="90">考试日期</td>
  <td  width="55">状态</td>
  <td  width="55">分数</td>
</tr>
<%
        for(Iterator<UserScore> iter= usList.iterator(); iter.hasNext(); )
        {
            UserScore us = iter.next();   
            out.print("<td>"+us.getExamName()+"</td>");
            String year = us.getExamDate().substring(0, 4);
            String month = us.getExamDate().substring(4,6);
            String day = us.getExamDate().substring(6,8);
            out.print("<td>"+year+"-"+month+"-"+day+"</td>");
            
            if( us.getActScore() < us.getPassScore() )
            {
                out.print("<td>不及格</td>");
            }
            else
            {
                out.print("<td>通过</td>");
            }
            out.print("<td>"+us.getActScore()+"</td>");
            out.print("</tr>");
        }
%>
</table>
<%
    }
%>
</body>
</html>