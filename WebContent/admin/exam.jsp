<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Collections"%>
<%@page import="com.util.ExamSorter"%>
<%@page import="java.util.Comparator"%>
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
String login = (String)session.getAttribute("login");
if(login==null||!login.equals("admin"))
{
    out.print("<script>window.parent.location.href='/onlineTest/login.jsp';</script>");
    return;
}
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

<h3>考试管理</h3>
<hr>
<form name=add action="addExam.jsp" method=post>
  <input type=submit value=新增考试 name=add id="add">
</form>
<%
String para = request.getQueryString();
String error = request.getParameter("error");
if(error!=null&&error.equals("sjNull"))
{
%>
    <script type="text/javascript">
    alert("还没有可选择的考试科目，请先新建一个科目");
    window.parent.location.href='/onlineTest/admin/admin.jsp';
    </script>
<%
}
if(para!=null&&para.equals("all"))
{
%>
    <a>全部考试信息。</a>
    <a href="exam.jsp">查看最近一个月点击此处</a>    
<%
}
else
{
%>
<a>最近一个月的考试信息。</a>
<a href="exam.jsp?all">查看全部点击此处</a>

<%
}
%>
<table border="1">
<tr>
  <td  width="200">考试名称</td>
  <td  width="100">科目</td>
  <td  width="90">考试日期</td>
  <td  width="70">开始时间</td>
  <td  width="70">结束时间</td>
  <td  width="70">状态</td>
</tr>
<%
    ExamDAO ed = new ExamDAOImpl();
    List<Exam> eList = null;
    ExamSorter comparator=new ExamSorter();
    if(para!=null && para.equals("all"))
    {
        eList = ed.getAllEaxm("0");
        Collections.sort(eList, comparator);
    }
    else
    {
        eList = ed.getRecentEaxm("0",30);
        Collections.sort(eList, comparator);
    }
    for(Iterator<Exam> iter=eList.iterator(); iter.hasNext(); )
    {
        Exam exam = iter.next();
        Date current = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
        long currentTime = Integer.parseInt((formatter.format(current)).substring(2));
        long examStartTime = Integer.parseInt(exam.getStartTime().substring(2));
        long examEndTime = Integer.parseInt(exam.getEndTime().substring(2));
        //System.out.println(currentTime+", "+examStartTime+", "+examEndTime);
        
        if(currentTime<=examEndTime)
        {
        	out.print("<tr><td><a href=\"addExam.jsp?id=" + exam.getId() + "\">"+exam.getExamName()+"</a></td>");	
        }
        else
        {
        	out.print("<tr><td><a href=\"examScore.jsp?id=" + exam.getId() + "&pass=" +exam.getPassScore()+"&name="+exam.getExamName()+"\">"+exam.getExamName()+"</a></td>");
        }
        String subjectIdStr = exam.getSubjectId();
        String[] subjectId = subjectIdStr.split("\\|");
        String subjectNameStr = "";
        for(int i=0; i< subjectId.length; i++)
        {
            if(i==0)
            {
                subjectNameStr += subjectMap.get(Integer.parseInt(subjectId[i]));
            }
            else
            {
                subjectNameStr += ","+subjectMap.get(Integer.parseInt(subjectId[i]));
            }
        }
        out.print("<td>"+ subjectNameStr +"</td>");
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
        
        if(currentTime<examStartTime)
        {
            out.print("<td>未开始</td>");
        }
        else if(currentTime>=examStartTime && currentTime<examEndTime)
        {
        	out.print("<td>进行中</td>");
        }
        else
        {
        	out.print("<td>已结束</td>");
        }
        out.print("</tr>");
    }
%>
</table>
</body>
</html>