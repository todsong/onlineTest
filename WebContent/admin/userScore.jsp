<%@page import="com.dao.impl.DeptDAOImpl"%>
<%@page import="com.pojo.Dept"%>
<%@page import="com.dao.DeptDAO"%>
<%@page import="com.dao.impl.UserDAOImpl"%>
<%@page import="com.dao.UserDAO"%>
<%@page import="com.pojo.User"%>
<%@page import="com.pojo.UserScore"%>
<%@page import="com.dao.impl.UserScoreDAOImpl"%>
<%@page import="com.dao.UserScoreDAO"%>
<%@page import="java.util.Date"%>
<%@page import="com.pojo.UserExam"%>
<%@page import="com.dao.impl.UserExamDAOImpl"%>
<%@page import="com.dao.UserExamDAO"%>
<%@page import="com.util.ExamSorter"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Collections"%>
<%@page import="com.dao.ExamDAO"%>
<%@page import="com.pojo.Exam"%>
<%@page import="com.dao.impl.ExamDAOImpl"%>
<%@page import="com.util.TimeUtil"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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
    function deleteAlert()
    {
    	if(alert("确定删除用户吗？")==true)
    		document.forms[0].submit();
    }
</script>

</head>
<body>
<%
String login = (String)session.getAttribute("login");
if(login==null||!login.equals("admin"))
{
    out.print("<script>window.parent.location.href='/onlineTest/login.jsp';</script>");
    return;
}
String userId = request.getQueryString().substring(3);
if(userId!=null || !userId.equals(""))
{
    out.println("<h4>个人基本信息</h4>");
}
else
{
    return;
}
UserDAO ud = new UserDAOImpl();
User user = ud.queryUserById(userId);
UserScoreDAO usd = new UserScoreDAOImpl();
List<UserScore> usList = usd.queryAllScoreByUserId(userId);
%>
<form name=up action="/onlineTest/InfoUpdateCheck" method=post>
 <table>
 <tr>
 <td>工号</td>
 <td colspan="2"><% out.print(userId); %></td>
 </tr>
 <tr>
 <td>姓名</td>
 <td colspan="2"><input type=text name=userName value=<%=user.getName() %>>
  </td>
 </tr>
 <tr>
 <td>部门</td>
 <td colspan="2">
  <select name="dept" id="dept">
 <%
 DeptDAO dd = new DeptDAOImpl();
 List<Dept> deptList = dd.getAllDept();
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
 <input type="hidden" value=<%=userId %> name="userId" id="userId" />
 <input type=button value="修改" onclick="check()" id="update" name="update" />
 <%
 if(usList==null || usList.size()==0)
 {
  %>
 <input type=submit value="删除"  onclick="deleteAlert()" id="delete" name="delete" />
  <%   
 }
 %>
 <%
 if(user.getStatus().equals("0"))
 {
 %>
 <input type=submit value="冻结"  id="inactive" name="inactive" />
 <%
 }
 else if(user.getStatus().equals("1"))
 {
  %>
  <input type=submit value="激活" id="active" name="active" />
  <%  
 }
 %>
  <input type=submit value="重置密码" id="repwd" name="repwd" />
</form>


<hr/>
<%
if(userId!=null || !userId.equals(""))
{
    out.println("<h4>考试成绩表</h4>");
}

if(usList==null || usList.size()==0)
{
    out.println("没有考试记录");
}
else
{    
%>
<table border="1">
<tr>
  <td  width="200">考试名称</td>
  <td  width="90">考试日期</td>
  <td  width="55">状态</td>
  <td  width="55">分数</td>
</tr>
<%
        for(Iterator<UserScore> iter= usList.iterator(); iter.hasNext(); )
        {
            UserScore us = iter.next();   
            out.print("<td>"+us.getExamName()+"</td>");
            String year = us.getExamDate().substring(0, 4);
            String month = us.getExamDate().substring(4,6);
            String day = us.getExamDate().substring(6,8);
            out.print("<td>"+year+"-"+month+"-"+day+"</td>");
            
            if( us.getActScore() < us.getPassScore() )
            {
                out.print("<td>不及格</td>");
            }
            else
            {
                out.print("<td>通过</td>");
            }
            out.print("<td>"+us.getActScore()+"</td>");
            out.print("</tr>");
        }
%>
</table>
<%
    }
%>
</body>
</html>