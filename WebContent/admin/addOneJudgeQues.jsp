<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.pojo.Subject"%>
<%@page import="com.dao.impl.SubjectDAOImpl"%>
<%@page import="java.util.List"%>
<%@page import="com.dao.SubjectDAO"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%

SubjectDAO sjd = new SubjectDAOImpl();
List<Subject> sjList = sjd.getAllSubject();

if(sjList==null || sjList.size()==0)
{
    %>
    <script>
    alert("请先新建科目，再尝试增加试题")
    window.parent.location.href='/onlineTest/login.jsp';
    </script>
    <%
    return;
}

%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<h3>新增判断题</h3>
<hr>
<script language="javascript">
function check()
{ 
    with(document.all)
    {
        var flag = false;
        if(document.addJ.ques.value==null||document.addJ.ques.value=="")
        {
        	alert("请输入题目");
        	return false;
        }
        else if(document.addJ.ques.value.length>=300)
        {
        	alert("题目内容不能超过300字");
            return false;
        }
        var allRadio = document.addJ.elements["answer"];
        for(var i = 0;i < allRadio.length;i++){
           if(allRadio[i].checked){
            flag = true;
           }
        }
        if (!flag){
         alert("请选择题目答案")
         return false;
        }
        if(subject.value=="")
        {
            alert("试题所属科目不能为空")
            return false;
        }
        return true;
        
  }
}

</script>
<form id=addJ name=addJ action="JudgeQuesAction" method=post onsubmit="return check()" >
    题目：<br><textarea rows="4" cols="80" name="ques"></textarea>
    <br>
    答案：
    <input type="radio" name="answer" value="T" />对
	<input type="radio" name="answer" value="F" />错
	<br/>
科目：<select name="subject" id="subject">
<option value="">---请选择---</option>
<%

for(int i=0;i<sjList.size();i++)
{
    out.print("<option value=\""+sjList.get(i).getId()+"\">"+sjList.get(i).getName()+"</option>");    
}
%>
</select>

    <br>
    <input type="submit" value="提交" name="addJ" id="addJ">
</form>
    
</body>
</html>