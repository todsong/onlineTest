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
			else 
			{
		        document.login.action="RegCheck";
				document.forms[0].submit();
			}
		}
	}
	function gotoLogin()
    {
        document.login.action="login.jsp";
        document.forms[0].submit();
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
<body leftmargin="0" rightmargin="0" topmargin="0" onkeydown="BindEnter(event)">
<table border="0px" width="2000" height="100" align=center cellspacing="0" background="image/top.png" >
<tr><td></td></tr>
</table>
  <h2 style="text-indent:7%">用户注册</h2>
  <hr width="90%">  
  <form name=login action="" method=post>
 <table style="position:absolute; top:180px; left:10%">
 <tr>
 <td align="right"><font color="red">*</font>工号：</td>
 <td colspan="2"><input type=text name=id>
  <%
  String res = (String) session.getAttribute("reg");
  if(res!=null && res.equals("fail"))
  {
      %>
      <script type="text/javascript">
      alert("工号已存在，注册失败！");
      </script>
      <%
    session.removeAttribute("reg");
  }
  %></td>
 </tr>
 <tr>
 <td align="right"><font color="red">*</font>姓名：</td>
 <td colspan="2"><input type=text name=userName>
  </td>
 </tr>
 <tr>
 <td align="right"><font color="red">*</font>密码：</td>
 <td colspan="2"><input type=password name=passWd></td>
 </tr>
 <tr>
 <td align="right"><font color="red">*</font>重复密码：</td>
 <td colspan="2"><input type=password name=passWdAg></td>
 </tr>
 <tr>
 <td align="right"><font color="red">*</font>部门：</td>
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
 <td align="right">电话：</td>
 <td colspan="2"><input type=text name=telNo></td>
 </tr>
 <tr>
  <td></td>
  <td>
   <input type=button value=提交 onclick="check()" id="reg">&nbsp;&nbsp;
   <input type=button value=返回 onclick="gotoLogin()" id="back">
  </td>
 </tr>
 
 </table>
 </form>
</body>
</html>