<%@page import="com.pojo.UserExam"%>
<%@page import="com.pojo.Exam"%>
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
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>在线考试系统</title>
<script type="text/javascript">

<%
String examType = (String) session.getAttribute("examType"); 

int empty = 0;
int right = 0;
int wrong = 0;
Exam exam = null;
UserExam ue = null;
String res = null;
if(examType.equals("0"))
{
    exam = (Exam) session.getAttribute("exam");
    ue = (UserExam) session.getAttribute("userExam");
    res = "考试得分"+ue.getScore()+"，及格分数为"+exam.getPassScore();
}
else
{
    empty = (Integer) session.getAttribute("empty");
    right = (Integer) session.getAttribute("right");
    wrong = (Integer) session.getAttribute("wrong");
    res = "答对"+right+"题，答错"+wrong+"题，"+empty+"题未答";
}

%>
function resAlert()
{
	var str = "<%= res%>";
    alert(str);
    top.location = ("/onlineTest/examinee/examinee.jsp");
}
</script>

</head>

<body onload="resAlert()">
</body>
</html>
