<%@page import="java.util.Collections"%>
<%@page import="com.util.ExamSorter"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.dao.ExamDAO"%>
<%@page import="com.pojo.Exam"%>
<%@page import="com.dao.impl.ExamDAOImpl"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.dao.impl.SubjectDAOImpl"%>
<%@page import="com.pojo.Subject"%>
<%@page import="com.dao.SubjectDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
SubjectDAO sjd = new SubjectDAOImpl();
List<Subject> sjList = sjd.getAllSubject();

Map<Integer,String> subjectMap = new HashMap<Integer,String>();
for(int i=0;i<sjList.size();i++)
{
    subjectMap.put(sjList.get(i).getId(), sjList.get(i).getName());
}

%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>

<body>
<h3>练习管理</h3>
<hr>
<form name=add action="addPrac.jsp" method=post>
  <input type=submit value=新增练习 name=add id="add">
</form>
<%
String para = request.getQueryString();
if(para!=null&&para.equals("all"))
{
%>
    <a>全部练习信息。</a>
    <a href="practise.jsp">查看当前考生可见的练习点击此处</a>    
<%
}
else
{
%>
<a>当前考生可见的练习信息。</a>
<a href="practise.jsp?all">查看全部点击此处</a>

<%
}
%>
<table border="1">
<tr>
  <td  width="200">名称</td>
  <td  width="100">科目</td>
  <td  width="150">开始时间</td>
  <td  width="150">结束时间</td>
</tr>
<%
    ExamDAO ed = new ExamDAOImpl();
    List<Exam> eList = null;
    ExamSorter comparator=new ExamSorter();
    if(para!=null && para.equals("all"))
    {
        eList = ed.getAllEaxm("1");
        Collections.sort(eList, comparator);
    }
    else
    {
        eList = ed.getRecentEaxm("1",0);
        Collections.sort(eList, comparator);
    }
    for(Iterator<Exam> iter=eList.iterator(); iter.hasNext(); )
    {
        Exam exam = iter.next();
        out.print("<tr><td><a href=\"addPrac.jsp?id=" + exam.getId() + "\">"+exam.getExamName()+"</a></td>");
        out.print("<td>"+subjectMap.get(Integer.parseInt(exam.getSubjectId()))+"</td>");
        String startYear = exam.getStartTime().substring(0, 4);
        String startMonth = exam.getStartTime().substring(4,6);
        String startDay = exam.getStartTime().substring(6,8);
        String startHour = exam.getStartTime().substring(8,10);
        String startMinute = exam.getStartTime().substring(10,12);
        String endYear = exam.getEndTime().substring(0,4);
        String endMonth = exam.getEndTime().substring(4,6);
        String endDay = exam.getEndTime().substring(6,8);
        String endHour = exam.getEndTime().substring(8,10);
        String endMinute = exam.getEndTime().substring(10,12);
        out.print("<td>"+startYear+"-"+startMonth+"-"+startDay+" "+startHour+":"+startMinute+"</td>");
        out.print("<td>"+endYear+"-"+endMonth+"-"+endDay+" "+endHour+":"+endMinute+"</td></tr>");
    }
%>
</table>
</body>
</html>