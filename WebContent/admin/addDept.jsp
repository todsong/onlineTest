<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
</head>
<body>

<h3>新增部门</h3>
<hr>
<script language="javascript">
function check()
{ 
    with(document.all)
    {
        if(document.addDept.deptName.value==null||document.addDept.deptName.value=="")
        {
            alert("请输入部门名称");
            return false;
        }
        else if(document.addDept.deptName.value.length>=30)
        {
            alert("部门名称不能超过30字");
            return false;
        }
        else return true;
  }
}

</script>
<form id=addDept name=addDept action="DeptAction" method=post onsubmit="return check()" >
    部门名称：<input type="text" style="width:200px" name="deptName" id="deptName" >
    <input type="submit" value="完成新增" name="addD" id="addD">
</form>
    
</body>
</html>