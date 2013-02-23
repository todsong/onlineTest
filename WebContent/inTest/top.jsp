<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.pojo.Exam"%>
<%@page import="java.util.Date"%>
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
<%
request.setAttribute("decorator", "none");
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server

Exam exam = (Exam) session.getAttribute("exam");
String examType = (String) session.getAttribute("examType");
%>

<script type="text/javascript">
<%if(examType.equals("0")){%>
var serverTime = new Date();
var clientTime = new Date();
serverTime = <%=new Date().getTime() %>;
var delta = serverTime - clientTime;
<%
SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
Date deadLine = formatter.parse(exam.getEndTime());
%>
var deadLine = new Date();
deadLine.setTime(<%=deadLine.getTime() %>);

function startTime()
{
    var now=new Date();
    now.setTime(now.getTime()+delta);
    if(now<deadLine)
    {
        var leftMinute = (deadLine.getHours()-now.getHours())*60+deadLine.getMinutes()-now.getMinutes();
        document.getElementById('time').innerHTML="考试剩余"+leftMinute+"分钟";
    }
    else
    {
        document.getElementById('time').innerHTML="时间到";
        alert("时间到，自动交卷！");
        handInPrac.submit();
    }
    t=setTimeout('startTime()',1000);
}
<%}%>
function check()
{   
    if(confirm("确定交卷?")==true)
    {
    	handInPrac.submit();
    }
}
</script>

<body topmargin="0" leftmargin="0" bottommargin="0" rightmargin="0" style="overflow:hidden" scroll="no" 
<%if(examType.equals("0")){%>onload="startTime()" <%}%> >

<table border="0px" cellspacing="0" >
<tr style="vertical-align:top">
<td width="1800" height="100" align="right" valign="bottom"  background="/onlineTest/image/top.png" >
      
      <form name="handInPrac" action="HandInPrac" method="post" >
      <%
      String name = (String) session.getAttribute("userName");
      out.print("用户:"+name);
      %>
<%if(examType.equals("0")) {%>
        &nbsp;&nbsp;&nbsp;&nbsp;<a id="time"></a>
<%} %>
        &nbsp;&nbsp;<input type=button value="交卷" name=handIn onclick="check()" id="handIn">&nbsp;&nbsp;
      </form>
      <br>
      </td>
    </tr>
    </table>
</body>
</html>