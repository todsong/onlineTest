<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="../js/md5.js"></script>
<title>在线考试系统</title>
<%
String login = (String)session.getAttribute("login");
if(login==null||!login.equals("user"))
{
    out.print("<script>window.parent.location.href='/onlineTest/login.jsp';</script>");
    return;
}
%>
<script>
    function passwdCheck()
    {
    	var passWdOld = document.getElementById("passwdOld");
        var passWdNew = document.getElementById("passwdNew");
        var passWdAg = document.getElementById("passwdAg");
        	if(passWdOld.value==null||passWdOld.value=="")
            {
                alert("旧密码不能为空")
            }
        	else if(passWdNew.value==null||passWdNew.value=="")
            {
                alert("新密码不能为空")
            }
        	else if(passWdNew.value.length>30)
       		{
        	    alert("新密码长度过长");
       		}
        	else if(passWdNew.value.length<6)
            {
                alert("新密码长度不能小于6位")
            }
        	else if(passWdNew.value!=passWdAg.value)
            {
                alert("输入的两个新密码不一致，请重新输入！")
            }
            else
            {
                var rand=<%=Math.random()%>;
                var userId = "<%=session.getAttribute("id")%>";
                var pNew = document.getElementById("passwdNew").value;
                var pOld = document.getElementById("passwdOld").value;
                
                document.getElementById("rand").value=rand;
                var tmp = MD5(userId+pOld);
                document.getElementById("verifyToken").value=MD5(tmp+rand);
                 document.getElementById("updateToken").value=MD5(userId+pNew);
                 document.getElementById("passwdNew").value="";
                 document.getElementById("passwdAg").value="";
                 document.getElementById("passwdOld").value="";
                 
            	document.forms[0].submit();
            }
    }
    function failCheck()
    {
     <%
     Object fail = session.getAttribute("passUp");
     if(fail!=null)
     {
         session.removeAttribute("passUp");
         %>
         alert("旧密码错误");
         <%
     }
     %>   
    }
</script>
</head>
<body onload=failCheck()>

<h4>修改密码</h4>
<hr>
  <form name=passUp action="/onlineTest/PassWdCheck" method=post>
 <table>
 <tr>
 <td>旧密码</td>
 <td colspan="2"><input type=password name=passWdOld id="passwdOld"></td>
 </tr>
 <tr>
 <td>新密码</td>
 <td colspan="2"><input type=password name=passWdNew id="passwdNew"></td>
 </tr>
 <tr>
 <td>重复密码</td>
 <td colspan="2"><input type=password name=passWdAg id="passwdAg"></td>
 </tr>
 <tr>
  <td>
  
  <input type="hidden" name=rand id=rand value="">
   <input type="hidden" name=updateToken id=updateToken value="">
   <input type="hidden" name=verifyToken id=verifyToken value="">
   <input type=button value=提交 onclick="passwdCheck()" id="passUp">
   </td>
 </tr>
 </table>
 </form>  
</body>
</html>