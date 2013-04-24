<%@page import="com.pojo.JudgeQues"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
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
</script>
</head>
<body>

<%
String quesType = "judge";
int quesId = (Integer) request.getSession().getAttribute("quesId");
String src = (String)request.getSession().getAttribute("src");
Boolean mdf = (Boolean)request.getSession().getAttribute("mdf");
if(src!=null && mdf!=null)
{
    if(mdf.equals(true))
    {
        %>
        <script>
        parent.frames["menu"].document.getElementById("<%=src%>").innerHTML="[<font color=\"green\">●</font>]";
        </script>
        <%
    }
}
List<JudgeQues> jqList = (ArrayList<JudgeQues>) session.getAttribute("judge");
out.println("<font size=\"2\" face=\"verdana\">判断题第"+(quesId+1)+"题</font><br/><br/>");
out.println(jqList.get(quesId).getqName());
String answer = (String)session.getAttribute(quesType+quesId);
if(answer==null)
{
    answer="";
}
%>

<form name="ques" action="DoQuesAction" method="post">
<input type="radio" name="answer" value="T" <%if(answer.equals("T")) out.print("checked");%>/>对
<br>
<input type="radio" name="answer" value="F" <%if(answer.equals("F")) out.print("checked");%>/>错
<br>
  <%-- <input type="hidden" name="quesId" value="<%=quesId %>" > --%>
  <input type="hidden" name="quesType" value="<%=quesType %>" >
  <%
  boolean back = (Boolean)request.getSession().getAttribute("back");
  boolean next = (Boolean)request.getSession().getAttribute("next");
  //if(para.contains("back"))
  if(back==true)
  {
  %>
  <input type="button" name="up" value="上一题" onclick="back();">
  <%
  }
  //if(para.contains("next"))
  if(next==true)
  {
  %>
  <input type="submit" name="next" value="下一题">
  <%
  }
  %>
  <input type="submit" name="handIn" value="保存">
  
</form>
</body>
</html>