<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
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
<body topmargin="0" leftmargin="0" bottommargin="0" rightmargin="0" style="overflow:hidden" scroll="no">
<table border="0px" cellspacing="0" >
<tr style="vertical-align:top">
<td width="1800" height="100" align="right" valign="bottom"  background="/onlineTest/image/top.png" >
用户:
<%
      String name = (String) session.getAttribute("userName");
      out.print(name);
%>
&nbsp;&nbsp;<a href="passwd.jsp" target="main">修改密码</a>
&nbsp;&nbsp;<a href="/onlineTest/LogOutAction"><font size=2 face="verdana">退出</font></a>&nbsp;&nbsp;&nbsp;<br/>&nbsp;
</td>
</tr>
</table>
<%--     <table >
    <tr>
      <td align="left" width="200" height="100">
      <%
      String name = (String) session.getAttribute("userName");
      out.print(name);
      %> &nbsp;&nbsp;你好&nbsp;&nbsp;<a href="/onlineTest/LogOutAction"><font size=2 face="verdana">退出</font></a>
      </td>
      <td align="right" width="400"></td>
    </tr>
    </table>
 --%></body>
</html>