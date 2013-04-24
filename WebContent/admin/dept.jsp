<%@page import="java.util.List"%>
<%@page import="com.dao.impl.DeptDAOImpl"%>
<%@page import="com.pojo.Dept"%>
<%@page import="com.dao.DeptDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
/* String login = (String)session.getAttribute("login");
if(login==null||!login.equals("admin"))
{
    out.print("<script>window.parent.location.href='/onlineTest/login.jsp';</script>");
    return;
} */
String para = request.getParameter("delError");
String addError = request.getParameter("addError");
if(para!=null && para.equals("user"))
{
    %>
    <script type="text/javascript">
    alert("该部门已存在注册用户，无法删除");
    </script>
<%
}
else if(addError!=null && addError.equals("name"))
{
    %>
    <script type="text/javascript">
    alert("该部门已存在");
    </script>
<%  
}
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>部门管理</h3>
<hr>
<form name=add action="addDept.jsp" method=post>
  <input type=submit value=新增部门 name=add id="add">
</form>
<%

DeptDAO dd = new DeptDAOImpl();
List<Dept> deptList = dd.getAllDept();
if(deptList==null || deptList.size()==0)
{
    out.print("<br/>没有部门信息");    
}
else
{
%>
<br>
<table border="1">
<tr>
  <td  width="250">部门名称</td>
</tr>
<%
for(int i=0;i<deptList.size();i++)
{
    out.print("<td><a href=\"deptItem.jsp?id="+deptList.get(i).getId()+"\">"+deptList.get(i).getName()+"</a></td></tr>");    
}
%>
</table>
<%
}
%>
</body>
</html>