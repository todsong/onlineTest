<%@page import="com.pojo.MultiQues"%>
<%@page import="com.pojo.SingleQues"%>
<%@page import="com.pojo.JudgeQues"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<form method="post" action="ClickQuesAction" target="ques">
<input type="hidden" name="quesType" id="quesType" value="" >
<input type="hidden" name="quesId" id="quesId" value="" >

</form>
<script>
function clickHRef(quesType,quesId)
{
	document.getElementById("quesId").value=quesId;
	document.getElementById("quesType").value=quesType;
	document.forms[0].submit();
}
</script>
<a id="judge" name="judge"><b>判断题</b></a><br/>

<%
List<JudgeQues> jqList = (ArrayList<JudgeQues>) session.getAttribute("judge");

for(int i=0;i<jqList.size();i++)
{
    %>
    &nbsp;&nbsp;<a href=javascript:clickHRef("judge","<%=i%>") target="ques">第<%=(i+1)%>题</a>
    <%
    String answer = (String) session.getAttribute("judge"+i);
    out.print("<a id=\"judge"+i+"\">");
    if(answer==null || answer.equals(""))
    {
        out.println("[<font color=\"red\">■</font>]");
    }
    else
    {
        out.println("[<font color=\"green\">●</font>]");
    }
    out.print("</a><br/>");
}
%>
<br/>
<a name="single"><b>单选题</b></a><br/>
<%
List<SingleQues> sqList = (ArrayList<SingleQues>) session.getAttribute("single");

for(int i=0;i<sqList.size();i++)
{
    %>
    &nbsp;&nbsp;<a href=javascript:clickHRef("judge","<%=i%>") target="ques">第<%=(i+1)%>题</a>
    <%
    String answer = (String) session.getAttribute("single"+i);
    out.print("<a id=\"single"+i+"\">");
    if(answer==null || answer.equals(""))
    {
        out.println("[<font color=\"red\">■</font>]");
    }
    else
    {
        out.println("[<font color=\"green\">●</font>]");
    }
    out.print("</a><br/>");
}
%>
<br/>
<a name="multi"><b>多选题</b></a><br/>
<%

List<MultiQues> mqList = (ArrayList<MultiQues>) session.getAttribute("multi");
for(int i=0;i<mqList.size();i++)
{
    %>
    &nbsp;&nbsp;<a href=javascript:clickHRef("judge","<%=i%>") target="ques">第 <%=(i+1)%>题</a>
    <%
    String answer = (String) session.getAttribute("multi"+i);
    out.print("<a id=\"multi"+i+"\">");
    if(answer==null || answer.equals(""))
    {
        out.println("[<font color=\"red\">■</font>]");
    }
    else
    {
        out.println("[<font color=\"green\">●</font>]");
    }
    out.print("</a><br/>");
}
%>