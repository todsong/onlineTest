<%@page import="java.util.Date"%>
<%@page import="com.pojo.UserExam"%>
<%@page import="com.dao.impl.UserExamDAOImpl"%>
<%@page import="com.dao.UserExamDAO"%>
<%@page import="com.util.ExamSorter"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Collections"%>
<%@page import="com.dao.ExamDAO"%>
<%@page import="com.pojo.Exam"%>
<%@page import="com.dao.impl.ExamDAOImpl"%>
<%@page import="com.util.TimeUtil"%>

<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String login = (String)session.getAttribute("login");
if(login==null||!login.equals("user"))
{
    out.print("<script>window.parent.location.href='/onlineTest/login.jsp';</script>");
    return;
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript">
function doExam(id)
{   
    if(confirm("确定进入考试?")==true)
    {
        top.location = "/onlineTest/inTest/inExamAction?id="+id;
    }
}
</script>

<body>

<h4>考试信息</h4>
<hr>
<%
ExamDAO ed = new ExamDAOImpl();
List<Exam> todayList = null;
List<Exam> recentList = null;
ExamSorter comparator = null;
recentList = ed.getRecentEaxm("0",30);
if(recentList==null || recentList.size()==0)
{
    out.println("最近一个月没有考试");
}
else
{
	todayList = ed.queryExamByTime(TimeUtil.getTodayDate(), "0");
	comparator = new ExamSorter();
	if(todayList==null || todayList.size()==0)
	{
	    out.print("今天没有考试");
	}
	else
	{
 	    Collections.sort(todayList, comparator);
%>
<a>今天的考试</a>
<table border="1">
<tr>
  <td  width="200">名称</td>
  <td  width="80">考试日期</td>
  <td  width="65">开始时间</td>
  <td  width="65">结束时间</td>
  <td  width="200">状态</td>
</tr>
<%
        String userId = (String) session.getAttribute("id");
	    for(Iterator<Exam> iter=todayList.iterator(); iter.hasNext(); )
	    {
	        Exam exam = iter.next();   
	        out.print("<td>"+exam.getExamName()+"</td>");
	        String year = exam.getStartTime().substring(0, 4);
	        String month = exam.getStartTime().substring(4,6);
	        String day = exam.getStartTime().substring(6,8);
	        String startHour = exam.getStartTime().substring(8,10);
	        String startMinute = exam.getStartTime().substring(10,12);
	        String endhour = exam.getEndTime().substring(8,10);
	        String endMinute = exam.getEndTime().substring(10,12);
	        out.print("<td>"+year+"-"+month+"-"+day+"</td>");
	        out.print("<td>"+startHour+":"+startMinute+"</td>");
	        out.print("<td>"+endhour+":"+endMinute+"</td>");
	        Date current = new Date();
	        
	        int currentTime = current.getHours()*100+current.getMinutes();
	        int examStartTime = Integer.parseInt(startHour+startMinute);
	        int examEndTime = Integer.parseInt(endhour+endMinute);
	        //System.out.println(currentTime+", "+examStartTime+", "+examEndTime);
	        if(currentTime<examStartTime)
	        {
	            out.print("<td>未开始</td>");
	        }
	        else if(currentTime>=examStartTime && currentTime<examEndTime)
	        {
	            UserExamDAO ued = new UserExamDAOImpl();
	            UserExam ue = ued.queryUniqueUserExam(exam.getId(), userId);
	            if(ue!=null && ue.getScore()!=-1)
	            {
	                out.print("<td>考试进行中，试卷已提交</td>");
                }
	            else
	            {
	                out.print("<td>考试进行中！<a href=\"javascript:doExam("+ exam.getId() +")\">点击进入</a></td>");
	            }
	        }
	        else
	        {
	            out.print("<td>已结束</td>");
	        }
	        out.print("</tr>");
	    }
%>
</table>
<%
	}
%>
<br>
<br>
<%
%>
<a>最近一个月的考试</a>
<table border="1">
<tr>
  <td  width="200">名称</td>
  <td  width="90">考试日期</td>
  <td  width="70">开始时间</td>
  <td  width="70">结束时间</td>
</tr>
<%
    Collections.sort(recentList, comparator);
    for(Iterator<Exam> iter=recentList.iterator(); iter.hasNext(); )
    {
        Exam exam = iter.next();   
        out.print("<td>"+exam.getExamName()+"</td>");
        String year = exam.getStartTime().substring(0, 4);
        String month = exam.getStartTime().substring(4,6);
        String day = exam.getStartTime().substring(6,8);
        String startHour = exam.getStartTime().substring(8,10);
        String startMinute = exam.getStartTime().substring(10,12);
        String endhour = exam.getEndTime().substring(8,10);
        String endMinute = exam.getEndTime().substring(10,12);
        out.print("<td>"+year+"-"+month+"-"+day+"</td>");
        out.print("<td>"+startHour+":"+startMinute+"</td>");
        out.print("<td>"+endhour+":"+endMinute+"</td></tr>");
    }
%>
</table>
<%
}
%>
<br>
</body>
</html>