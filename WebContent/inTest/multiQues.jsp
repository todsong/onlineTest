<%@page import="com.pojo.MultiQues"%>
<%@page import="com.pojo.JudgeQues"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%
String login = (String)session.getAttribute("login");
if(login==null||!login.equals("user"))
{
    out.print("<script>window.parent.location.href='/onlineTest/login.jsp';</script>");
    return;
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>在线考试系统</title>
<script type="text/javascript">
function back()
{
ques.action = "DoQuesAction";
ques.submit();
}  
function topicReload()
{  
    window.parent.frames["menu"].location.reload();  
}
</script>
</head>
<body onload="topicReload()">

<%
String para = request.getQueryString();
String type = "multi";
String[] url = para.split("&");
int id = Integer.parseInt(url[0].substring(3));
List<MultiQues> sqList = (ArrayList<MultiQues>) session.getAttribute("multi");
out.println("<font size=\"2\" face=\"verdana\">多选题第"+(id+1)+"题</font><br/><br/>");
MultiQues sq = sqList.get(id);
out.println(sq.getqName());
String answer = (String)session.getAttribute(type+id);
if(answer==null)
{
    answer="";
}
%>

<form name="ques" action="DoQuesAction" method="post">
<%
int optNum = sq.getOptNum();
String[] opts = new String[5];
opts[0] = sq.getOptionA();
opts[1] = sq.getOptionB();
opts[2] = sq.getOptionC();
opts[3] = sq.getOptionD();
opts[4] = sq.getOptionE();
for(int i=0; i<optNum; i++)
{
    char a = (char)('A'+i);
    out.print("<input type=checkbox name=answer value="+a+" ");
    if(answer.contains(a+""))
        out.print("checked");
    out.print("/>"+a+". "+opts[i]+"<br/>");  
}
%>

  <input type="hidden" name="id" value="<%=id %>" >
  <input type="hidden" name="type" value="<%=type %>" >
  <%
  if(para.contains("back"))
  {
  %>
  <input type="button" name="up" value="上一题" onclick="back();">
  <%
  }
  if(para.contains("next")){
  %>
  <input type="submit" name="next" value="下一题">
  <%
  }
  %>
  <input type="submit" name="handIn" value="保存">
  
</form>
</body>
</html>