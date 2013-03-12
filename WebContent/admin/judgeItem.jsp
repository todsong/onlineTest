<%@page import="java.util.List"%>
<%@page import="com.dao.impl.SubjectDAOImpl"%>
<%@page import="com.dao.SubjectDAO"%>
<%@page import="com.pojo.Subject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.dao.impl.JudgeQuesDAOImpl"%>
<%@page import="com.dao.JudgeQuesDAO"%>
<%@page import="com.pojo.JudgeQues"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
    function check()
    {
    	var ques = document.upd.ques.value;
        if(ques==null||ques=="")
        {
            alert("问题不能为空");
        }
        else document.forms[0].submit();
    }
    function delAlert()
    {
    	 if(!confirm("确定要删除吗？"))
    	 {
    		 return false;
    	 }
    }
</script>
</head>
<body>

<h3>判断题管理</h3>
<hr>
<form name=upd id=upd action=JudgeQuesAction method=post onsubmit="return delAlert()">
<%

SubjectDAO sjd = new SubjectDAOImpl();
List<Subject> sjList = sjd.getAllSubject();

String para = request.getQueryString();
if(para==null || para.equals(""))
{
   
}
else
{
int id = Integer.parseInt(para.substring(3));
JudgeQuesDAO jd = new JudgeQuesDAOImpl();
JudgeQues jq = jd.queryJudgeQuesById(id);
%>

<input type="hidden" name="id" value=<%=id %> >
<table>
<%-- <tr><td>编号：</td><td colspan="2"><% out.print(id); %></td></tr> --%>

<tr><td>答案：</td><td colspan="2">
    <select name="answer">
        <option value="T"
        <%
        String defaultSelect = jq.getqAnswer();
        if(defaultSelect!=null&&defaultSelect.equals("T"))
        {
            out.print("selected='selected'");
        }
        %>>T</option>
        <option value="F"
        <%
        if(defaultSelect!=null && defaultSelect.equals("F"))
        {
            out.print("selected='selected'");
        }
        %>>F</option>
    </select>
  
  </td></tr>
<tr><td>题目：</td><td colspan="2">
  <textarea rows="4" cols="80" name="ques"><%out.print(jq.getqName());%></textarea>
  </td></tr>
</table>

        科目：<select name="subject" id="subject">
        <%
for(int i=0;i<sjList.size();i++)
{
    out.print("<option value=\""+sjList.get(i).getId()+"\" ");
    if(sjList.get(i).getId()==jq.getSubjectId())
    {
        out.print("selected=selected");
    }
    out.print(">"+sjList.get(i).getName()+"</option>");    
}
%>
</select>
<br/>
        状态：<select name="status" id="status">
        <option value="0" <%if(jq.getStatus()==0) out.print("selected=selected"); %> >正常</option>
        <option value="1" <%if(jq.getStatus()==1) out.print("selected=selected"); %> >废止</option>
        </select>
        <br/>
  <input type="hidden" name="id" value="<%out.print(id);%>" >
  <input type=button value=更新 name=up onclick="check()" id="up">
  <input type=submit value=删除 name=del >
</form>
<%} %>
</body>
</html>