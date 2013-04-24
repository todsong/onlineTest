<%@page import="java.util.List"%>
<%@page import="com.dao.impl.SubjectDAOImpl"%>
<%@page import="com.pojo.Subject"%>
<%@page import="com.dao.SubjectDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
String para = request.getParameter("delError");
if(para!=null)
{
    if(para.equals("judge"))
    {
        %>
        <script type="text/javascript">
        alert("已存在此科目的判断题，删除失败");
        </script>
<%
    }
    else if(para.equals("single"))
    {
        %>
        <script type="text/javascript">
        alert("已存在此科目的单选题，删除失败");
        </script>
<%      
    }
    else if(para.equals("multi"))
    {
        %>
        <script type="text/javascript">
        alert("已存在此科目的多选题，删除失败");
        </script>
<%      
    }
}
%>
<h3>科目管理</h3>
<hr>
<form name=add action="addSubject.jsp" method=post>
  <input type=submit value=新增科目 name=add id="add">
</form>
<%
SubjectDAO dd = new SubjectDAOImpl();
List<Subject> subjectList = dd.getAllSubject();
if(subjectList==null || subjectList.size()==0)
{
    out.print("<br/>没有试题科目信息");    
}
else
{
%>
<br>
<table border="1">
<tr>
  <td  width="250">试题科目名称</td>
</tr>
<%
for(int i=0;i<subjectList.size();i++)
{
    out.print("<td><a href=\"subjectItem.jsp?id="+subjectList.get(i).getId()+"\">"+subjectList.get(i).getName()+"</a></td></tr>");    
}
%>
</table>
<%
}
%>
</body>
</html>