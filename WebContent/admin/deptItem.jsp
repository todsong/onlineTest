<%@page import="com.pojo.User"%>
<%@page import="java.util.List"%>
<%@page import="com.dao.impl.UserDAOImpl"%>
<%@page import="com.dao.UserDAO"%>
<%@page import="com.dao.impl.DeptDAOImpl"%>
<%@page import="com.pojo.Dept"%>
<%@page import="com.dao.DeptDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.dao.impl.JudgeQuesDAOImpl"%>
<%@page import="com.dao.JudgeQuesDAO"%>
<%@page import="com.pojo.JudgeQues"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
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
DeptDAO dd = new DeptDAOImpl();
Dept dept = dd.quertDeptById(id);
%>
<h3><%=dept.getName() %></h3>
<hr>
<script language="javascript">
function check()
{ 
    with(document.all)
    {
        if(document.addDept.deptName.value==null||document.addDept.deptName.value=="")
        {
            alert("请输入部门名称");
        }
        else if(document.addDept.deptName.value.length>=30)
        {
            alert("部门名称不能超过30字");
        }
        else
       	{
            document.addDept.action="DeptAction";
            var input = document.createElement("input");
            input.type = 'hidden';
            input.name = 'upD';
            document.getElementById("addDept").appendChild(input);
            document.forms[0].submit(); 	
       	}
    }
}

function gotoDel()
{
    document.addDept.action="DeptAction";
    var input = document.createElement("input");
    input.type = 'hidden';
    input.name = 'delD';
    document.getElementById("addDept").appendChild(input);
    document.forms[0].submit();
}
</script>
<form id=addDept name=addDept action="" method=post >
    部门名称：<input type="text" style="width:200px" name="deptName" id="deptName" value=<%=dept.getName() %>>
  <br/>
  <input type="hidden" value=<%=dept.getId() %> name="deptId" id="deptId" >
    <input type="button" value="更新" name="upD" id="upD" onclick="check()">
    <input type="button" value="删除" name="delD" id="delD" onclick="gotoDel()">
    <%-- <%
    UserDAO ud = new UserDAOImpl();
    List<User> userList = ud.queryUserByDept(id) ;
    if(userList==null || userList.size()==0)
    {
    %>
    <input type="submit" value="删除" name="delD" id="delD">
    <%
    }
    %> --%>
</form>
    
</body>
</body>
</html>