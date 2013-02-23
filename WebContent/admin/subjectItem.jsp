<%@page import="com.pojo.Exam"%>
<%@page import="com.dao.impl.ExamDAOImpl"%>
<%@page import="com.dao.ExamDAO"%>
<%@page import="com.pojo.User"%>
<%@page import="java.util.List"%>
<%@page import="com.dao.impl.UserDAOImpl"%>
<%@page import="com.dao.UserDAO"%>
<%@page import="com.dao.impl.SubjectDAOImpl"%>
<%@page import="com.pojo.Subject"%>
<%@page import="com.dao.SubjectDAO"%>
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
<%
int id = Integer.parseInt(request.getParameter("id"));
SubjectDAO dd = new SubjectDAOImpl();
Subject subject = dd.quertSubjectById(id);
%>
<h3><%=subject.getName() %></h3>
<hr>
<script language="javascript">
function check()
{ 
    with(document.all)
    {
        if(document.addSubject.subjectName.value==null||document.addSubject.subjectName.value=="")
        {
            alert("请输入试题科目名称");
        }
        else if(document.addSubject.subjectName.value.length>=30)
        {
            alert("试题科目名称不能超过30字");
        }
        else
       	{
            document.addSubject.action="SubjectAction";
            var input = document.createElement("input");
            input.type = 'hidden';
            input.name = 'upD';
            document.getElementById("addSubject").appendChild(input);
            document.forms[0].submit();        	
       	}
  }
}

function gotoDel()
{
	document.addSubject.action="SubjectAction";
	var input = document.createElement("input");
    input.type = 'hidden';
    input.name = 'delD';
    document.getElementById("addSubject").appendChild(input);
    document.forms[0].submit();
}

</script>
<form id=addSubject name=addSubject action="" method=post >
    试题科目名称：<input type="text" style="width:200px" name="subjectName" id="subjectName" value=<%=subject.getName() %>>
  <br/>
  <input type="hidden" value=<%=subject.getId() %> name="subjectId" id="subjectId" >
    <input type="button" value="更新" name="upD" id="upD" onclick="check()">
    <input type="button" value="删除" name="delD" id="delD" onclick="gotoDel()">
</form>
</body>
</html> 