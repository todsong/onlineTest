<%@page import="com.pojo.Subject"%>
<%@page import="com.dao.impl.SubjectDAOImpl"%>
<%@page import="java.util.List"%>
<%@page import="com.dao.SubjectDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
SubjectDAO sd = new SubjectDAOImpl();
List<Subject> sjList = sd.getAllSubject();
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
<body onload="setHidden()">

<h3>新增多选题</h3>
<hr>
<script language="javascript">

var globalRowId=4;
function setHidden()
{
    for(var i=globalRowId; i<5; i++)
    {
        document.getElementById('optTalbe').rows[i].style.display = "none"
    }
}

function setShow()   
{   
    document.getElementById('optTalbe').rows[globalRowId].style.display = "block" 
    globalRowId++   
}
function check()
{ 
    with(document.all)
    {
        if(document.addM.ques.value==null||document.addM.ques.value=="")
        {
        	alert("请输入题目");
        	return false;
        }
        else if(document.addM.ques.value.length>=300)
        {
        	alert("题目内容不能超过300字");
            return false;
        }
    
        for(var i=0;i<globalRowId;i++)
        {
            var act = String.fromCharCode(65+i)
            var tAct = "t"+act;
            if(document.getElementById(tAct).value==null||document.getElementById(tAct).value=="")
            {
            alert("选项"+act+"为空");
            return false
            }
            else if(document.getElementById(tAct).value.length>=300)
            {
                alert("选项"+act+"内容不能超过300字");
                return false
            }
        }
        var obj = document.getElementsByName('answer')
        var count=0;

        for(var i=0; i<globalRowId; i++)
        {
            if(obj[i].checked)
            {
                count++;
            }
        }
        if(count==0)
        {
            alert("答案未选择")
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
<form id=addM name=addM action="MultiQuesAction" method=post onsubmit="return check()" >
    题目：<br><textarea rows="4" cols="80" name="ques"></textarea>
    <br>
<input type="button" onclick="setShow()" value="增加选项" />
<table id="optTalbe">
<tr><td><input type="checkbox" name="answer" value="A" />A.<textarea rows="2" cols="80" name="tA" id="tA"></textarea></td></tr>
<tr><td><input type="checkbox" name="answer" value="B" />B.<textarea rows="2" cols="80" name="tB" id="tB"></textarea></td></tr>
<tr><td><input type="checkbox" name="answer" value="C" />C.<textarea rows="2" cols="80" name="tC" id="tC"></textarea></td></tr>
<tr><td><input type="checkbox" name="answer" value="D" />D.<textarea rows="2" cols="80" name="tD" id="tD"></textarea></td></tr>
<tr><td><input type="checkbox" name="answer" value="E" />E.<textarea rows="2" cols="80" name="tE" id="tE"></textarea></td></tr>
</table>
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
<input type="submit" value="提交" name="addM" id="addM">
</form>
    
</body>
</html>