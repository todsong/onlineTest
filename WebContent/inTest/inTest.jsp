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
</head>

<frameset rows="100px,*"  framespacing="3">

  <frame src="top.jsp" name="top">
  <frameset cols="15%,*">
    <frame src="menu.jsp" name="menu">
    <frame src="<%
    int judgeSum = (Integer) session.getAttribute("judgeSum");
    if(judgeSum!=0)
    {
        out.println("judge");
    }
    else
    {
        int singleSum = (Integer) session.getAttribute("singleSum");
        if(singleSum!=0)
        {
            out.println("single");
        }
        else
        {
            out.println("multi");
        }
    }
    %>Ques.jsp" name="ques">
    <%
    request.getSession().setAttribute("quesId", 0);
    request.getSession().setAttribute("next", true);
    request.getSession().setAttribute("back", false);
    %>
  </frameset>
</frameset>

</html>
