<%@page import="com.pojo.Subject"%>
<%@page import="com.dao.impl.SubjectDAOImpl"%>
<%@page import="java.util.List"%>
<%@page import="com.dao.SubjectDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
String error = request.getParameter("error");
String fatal = request.getParameter("fatal");
if(error!=null&&!error.equals(""))
{
    String row = request.getParameter("row");
    String alert=null;
    if(row==null || row.isEmpty())
    {
        alert="系统异常";
    }
    else
    {
        if(error.equals("answer"))
        {
            alert="第"+row+"行答案有误";
        }
        else if(error.equals("emptyQues"))
        {
            alert="第"+row+"行题目为空";
        }
        else if(error.equals("emptyQName"))
        {
            alert="第"+row+"行题干为空";
        }
        else if(error.equals("oneLine"))
        {
            alert="第"+row+"行题干与选项之间没有分行";
        }
        else if(error.equals("A")||error.equals("B")||error.equals("C")||error.equals("D"))
        {
            alert="第"+row+"行"+error+"选项有误";
        }
        else if(error.contains("empty"))
        {
            String opt = error.split("empty")[1];
            if(opt!=null)
            {
                alert="第"+row+"行选项"+opt+"为空";
            }
            else alert="系统异常";
        }
        else if(error.contains("long"))
        {
            String opt = error.split("long")[1];
            if(opt!=null && opt.equals("QName"))
            {
                alert="第"+row+"行题干过长（超过300字符）";
            }
            else if(opt!=null)
            {
                alert="第"+row+"行选项"+opt+"过长（超过300字符）";
            }
        }
        else if(error.equals("unique"))
        {
            alert = "系统中已存在第"+row+"行内容";       
        }
        else if(error.equals("same"))
        {
            String src = request.getParameter("src");
            alert = "文件中第"+src+"和第"+row+"行内容相同";       
        }
    }
    %>
    <script type="text/javascript">
    alert("<%=alert%>");
    </script>
    <%
}
else if(fatal!=null && fatal.equals("file"))
{
    %>
    <script type="text/javascript">
    alert("文件解析失败。只能导入excel 2003文件");
    </script>
    <%
}

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
<body>
<h3>批量导入多选题</h3>
<hr>
<script language="javascript">
function check()
{ 
    with(document.all)
    {
        if(file.value==null||file.value=="")
        {
            alert("请选择要上传的文件")
            return false;
        }
        else if(subject.value=="")
        {
            alert("请选择所属科目")
            return false;
        }
        return true;
    }
}
</script>
<form id=addBatch name=addBatch action="MultiAddBatch" method=post enctype="multipart/form-data" onsubmit="return check()">
选择文件：<input type="file" name="file" />
<br/>
所属科目：<select name="subject" id="subject">
<option value="">---请选择---</option>
<%

for(int i=0;i<sjList.size();i++)
{
    out.print("<option value=\""+sjList.get(i).getId()+"\">"+sjList.get(i).getName()+"</option>");    
}
%>
</select>

    <br>
    <input type="submit" value="提交" />
</form>

</body>
</html>