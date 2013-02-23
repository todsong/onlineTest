<%@page import="java.util.List"%>
<%@page import="com.dao.impl.DeptDAOImpl"%>
<%@page import="com.pojo.Dept"%>
<%@page import="com.dao.DeptDAO"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
DeptDAO dd = new DeptDAOImpl();
List<Dept> deptList = dd.getAllDept();
if(deptList==null || deptList.size()==0)
{
    %>
    <script>
    alert("管理员尚未录入部门信息，暂无法注册");
    window.parent.location.href='/onlineTest/login.jsp';
    </script>
    <%
    return;
}
%>
<html>
<script>
	function check()
	{ 
		with(document.all)
		{
			if(id.value==null||id.value=="")
            {
                alert("工号不能为空")
            }
			else if(userName.value==null||userName.value=="")
			{
				alert("姓名不能为空")
			}
			else if(passWd.value==null||passWd.value=="")
            {
                alert("密码不能为空")
            }
			else if(passWd.value.length<6)
            {
                alert("密码长度至少6位")
            }
			else if(passWd.value!=passWdAg.value)
			{
				alert("您的密码不一致，请重新输入！")
				passWd.value = "";
				passWdAg.value = "";
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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>在线考试系统</title>
</head>
<body onkeydown="BindEnter(event)">
  <h1>用户注册</h1>
  <form name=login action=regCheck method=post>
 <table>
 <tr>
 <td>工号</td>
 <td colspan="2"><input type=text name=id>
  <%
  String res = (String) session.getAttribute("reg");
  if(res!=null && res.equals("fail"))
  {
    out.print("工号已存在！<br>");
    session.removeAttribute("reg");
  }
  %></td>
 </tr>
 <tr>
 <td>姓名</td>
 <td colspan="2"><input type=text name=userName>
  </td>
 </tr>
 <tr>
 <td>密码</td>
 <td colspan="2"><input type=password name=passWd></td>
 </tr>
 <tr>
 <td>重复密码</td>
 <td colspan="2"><input type=password name=passWdAg></td>
 </tr>
 <tr>
 <td>部门</td>
 <td colspan="2">
 <select name="dept" id="dept">
	<option value="">--请选择--</option>
	<%
	for(int i=0;i<deptList.size();i++)
	{
	    out.print("<option value=\""+deptList.get(i).getId()+"\">"+deptList.get(i).getName()+"</option>");
	}
	%>
  </select>
</td>
 </tr>
 <tr>
 <td>电话</td>
 <td colspan="2"><input type=text name=telNo></td>
 </tr>
 <tr>
 <td></td>
 <td><input type=button value=提交 onclick="check()" id="reg"></td>
 <td></td>
 </tr>
 
 </table>
 </form>  
</body>
</html>