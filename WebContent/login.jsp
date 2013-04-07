<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="js/md5.js"></script>
<title>在线考试系统</title>
<%
String login = (String)session.getAttribute("login");
if(login!=null)
{
    if(login.equals("user"))
    {
        response.sendRedirect("examinee/examinee.jsp");
        return;
    }
    else if(login.equals("admin"))
    {
        response.sendRedirect("admin/admin.jsp");
        return;
    }
    else if(login.equals("fail"))
    {
        %>
        <script type="text/javascript">
        alert("账号或密码错误！");
        </script>
        <%
    }
    else if(login.equals("inactive"))
    {
        %>
        <script type="text/javascript">
        alert("账号未激活！");
        </script>
        <%
    }
    session.removeAttribute("login");

}
String reg = (String)session.getAttribute("reg");
if(reg!=null)
{
    session.removeAttribute("reg");
    %>
    <script type="text/javascript">
    alert("注册成功");
    </script>
    <%
}
String res = (String) session.getAttribute("login");
if(res!=null)
{
}
%>
<script>
    function check()
    { 
        document.login.action="LoginCheck";
        with(document.all)
        {
            if(id.value==null||id.value=="")
            {
                alert("工号不能为空")
            }
            else if(passWd.value==null||passWd.value=="")
            {
                alert("密码不能为空")
            }
            else 
           	{
           	   var rand=Math.random();
               document.getElementById("rand").value=rand;
           	   var tmp = MD5(document.getElementById("userId").value+document.getElementById("passwd").value);
           	   document.getElementById("token").value=MD5(tmp+rand);
            	document.getElementById("passwd").value="";
             	document.forms[0].submit();
           	}
        }
    }
    function gotoReg()
    {
    	document.login.action="register.jsp";
    	document.forms[0].submit();
    }
    function BindEnter(obj)
    {
        //使用document.getElementById获取到按钮对象
        var button = document.getElementById('log');
        if(obj.keyCode == 13)
            {
                button.click();
                obj.returnValue = false;
            }
    }    
</script>


</head>
<body onkeypress="BindEnter(event)">
<form name="login" action="" method=post>
<table border="0px" width="664" height="406" align=center cellspacing="0"
     style="margin-top:50px;" background="image/login.png">
  <tr><td height="228px">&nbsp;</td><td height="228px">&nbsp;</td></tr>
  <tr><td height="178px" width="140px">&nbsp;</td><td height="178px">
   工号：<input type=text name=id id="userId" style="width:120px">
   密码：<input type=password name=passWd id=passwd  style="width:120px">
   <input type="hidden" name=rand id=rand value="">
   <input type="hidden" name=token id=token value="">
  <input type=button value=登陆 name="log" onclick="check()" id="log">
  <input type=button value=注册 name="reg" onclick="gotoReg()" id="reg">
  </td>
  </tr>
</table>
</form>
</body>
</html>