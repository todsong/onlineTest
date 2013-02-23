<%@page import="java.util.List"%>
<%@page import="com.pojo.User"%>
<%@page import="com.dao.impl.UserDAOImpl"%>
<%@page import="com.dao.UserDAO"%>
<%@page import="com.dao.impl.DeptDAOImpl"%>
<%@page import="com.pojo.Dept"%>
<%@page import="com.dao.DeptDAO"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String login = (String)session.getAttribute("login");
if(login==null||!login.equals("user"))
{
    out.print("<script>window.parent.location.href='/onlineTest/login.jsp';</script>");
    return;
}
DeptDAO dd = new DeptDAOImpl();
List<Dept> deptList = dd.getAllDept();

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script>
    function check()
    { 
        with(document.all)
        {
            if(userName.value==null||userName.value=="")
            {
                alert("姓名不能为空")
            }
            else if(dept.value==null||dept.value=="")
            {
                alert("部门不能为空")
            }
            else document.forms[0].submit();
        }
    }

    function BindEnter(obj)
    {
        //使用document.getElementById获取到按钮对象
        var button = document.getElementById('reg');
        if(obj.keyCode == 13)
            {
                button.click();
                obj.returnValue = false;
            }
    }
</script>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<h4>个人信息</h4>
<hr>
<%
UserDAO ud = new UserDAOImpl();
String id = (String) session.getAttribute("id");
User user = ud.queryUserById(id);
if(user!=null)
{
%>
  <form name=up action="/onlineTest/InfoUpdateCheck" method=post>
 <table>
 <tr>
 <td>工号</td>
 <td colspan="2"><% out.print(id); %></td>
 </tr>
 <tr>
 <td>姓名</td>
 <td colspan="2"><input type=text name=userName value=<%out.print(user.getName()); %>>
  </td>
 </tr>
 <tr>
 <td>部门</td>
 <td colspan="2">
  <select name="dept" id="dept">
 <%
 for(int i=0;i<deptList.size();i++)
 {
     out.print("<option value=\""+deptList.get(i).getId()+"\" ");
     if(deptList.get(i).getId()==user.getDept())
     {
         out.print("selected='selected' ");
     }
     out.print(">"+deptList.get(i).getName()+"</option>");
 }
 %>
</select> 
 
 </td>
 </tr>
 <tr>
 <td>电话</td>
 <td colspan="2"><input type=text name=telNo value=<%out.print(user.getTelephone()); %>></td>
 </tr>
</table>
 <input type="hidden" value=<%=id %> name="userId" id="userId" />
 <input type=button value=修改 onclick="check()" id="update" />
</form>
<%
}
%>  
</body>
</html>