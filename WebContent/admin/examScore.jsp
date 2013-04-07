<%@page import="com.pojo.Exam"%>
<%@page import="com.dao.impl.ExamDAOImpl"%>
<%@page import="com.dao.ExamDAO"%>
<%@page import="com.dao.impl.DeptDAOImpl"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.pojo.ExamScore"%>
<%@page import="java.util.List"%>
<%@page import="com.dao.impl.ExamScoreDAOImpl"%>
<%@page import="com.dao.ExamScoreDAO"%>
<%@page import="com.pojo.Dept"%>
<%@page import="com.dao.DeptDAO"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
String login = (String)session.getAttribute("login");
if(login==null||!login.equals("admin"))
{
    out.print("<script>window.parent.location.href='/onlineTest/login.jsp';</script>");
    return;
}
DeptDAO dd = new DeptDAOImpl();
List<Dept> deptList = dd.getAllDept();
Map<Integer,String> deptMap = new HashMap<Integer,String>();
for(int i=0;i<deptList.size();i++)
{
    deptMap.put(deptList.get(i).getId(), deptList.get(i).getName());
}
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>考试成绩表</title>
<script type="text/javascript">
function printpage()
  {
  window.print()
  }
</script>
</head>
<body>
	<%
	//System.out.println(request.getQueryString());
	ExamScoreDAO esd = new ExamScoreDAOImpl();
    int examId = Integer.parseInt(request.getQueryString().split("&")[0].substring(3));
    ExamDAO ed = new ExamDAOImpl();
    Exam exam = ed.queryExamById(examId);
    int passScore = exam.getPassScore();
    String examName = exam.getExamName();
    
    List<ExamScore> esList = esd.queryExamScoreByExamId(examId);
    out.print("<h4>\""+examName+"\"成绩表</h4>");
    if(esList==null || esList.size()==0)
    {
    	out.print("没有考生成绩");
    }
    else
    {
    	%>
    	<hr>
    	<input type="button" value="打印本页" onclick="printpage()" />
	<table border="1">
		<tr>
			<td width="100">工号</td>
			<td width="90">姓名</td>
			<td width="150">部门</td>
			<td width="70">成绩</td>
			<td width="70">状态</td>
		</tr>
		<%
	    for(Iterator<ExamScore> iter=esList.iterator(); iter.hasNext(); )
	    {
	    	ExamScore es = iter.next();
	        out.print("<tr><td>"+es.getUserId()+"</a></td>");
	        out.print("<td>"+es.getUserName()+"</td>");
	        out.print("<td>"+deptMap.get(es.getDept())+"</td>");
	        out.print("<td>"+es.getScore()+"</td>");
	        if(es.getScore()<passScore)
	        {
	            out.print("<td>不及格</td>");
	        }
	        else
	        {
	            out.print("<td>通过</td>");
	        }
	        out.print("</tr>");
	    }
    }
%>
	</table>
</body>
</html>