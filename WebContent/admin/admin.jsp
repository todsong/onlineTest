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
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>在线考试系统管理界面</title>
</head>

<frameset rows="100px,*"  framespacing="3"><!-- bordercolor="#ED1F0B" -->
  <frame src="top.jsp" name="top">
  <frameset cols="15%,*">
    <frame src="menu.jsp" name="menu">
    <frame src="main.jsp" name="main">
  </frameset>

</frameset>
</html>
