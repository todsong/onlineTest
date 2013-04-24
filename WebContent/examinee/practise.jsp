<%@page import="java.util.Date"%>
<%@page import="com.resource.Cache"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.dao.ExamDAO"%>
<%@page import="com.pojo.Exam"%>
<%@page import="com.dao.impl.ExamDAOImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
function doPrac(id,c)
{   
	if(confirm("确定进入练习?")==true)
    {
		top.location = "/onlineTest/inTest/InPracAction?id="+id+"\&c="+c;
    }
}
</script>

</head>

<body>
<h4>练习信息</h4>
<hr>
<table border="1">
<tr>
  <td  width="200">名称</td>
  <td  width="150">开始时间</td>
  <td  width="150">结束时间</td>
</tr>
<%
if (Cache.getPracCacheDate().getDate() != (new Date().getDate()))
{
    Cache.initPracCache();
}
    List<Exam> eList = Cache.getAvailablePrac();
    for(Iterator<Exam> iter=eList.iterator(); iter.hasNext(); )
    {
        Exam exam = iter.next();
        String startYear = exam.getStartTime().substring(0, 4);
        String startMonth = exam.getStartTime().substring(4,6);
        String startDay = exam.getStartTime().substring(6,8);
        String startHour = exam.getStartTime().substring(8,10);
        String startMinute = exam.getStartTime().substring(10,12);
        String endYear = exam.getEndTime().substring(0,4);
        String endMonth = exam.getEndTime().substring(4,6);
        String endDay = exam.getEndTime().substring(6,8);
        String endHour = exam.getEndTime().substring(8,10);
        String endMinute = exam.getEndTime().substring(10,12);
        for(int i=0;i<3;i++)
        {
            out.print("<tr><td><a href=\"javascript:doPrac("+ exam.getId()+","+ i +")\">"+exam.getExamName()+"-练习"+(i+1)+"</a></td>");
            out.print("<td>"+startYear+"-"+startMonth+"-"+startDay+" "+startHour+":"+startMinute+"</td>");
            out.print("<td>"+endYear+"-"+endMonth+"-"+endDay+" "+endHour+":"+endMinute+"</td></tr>");        
        }
    }
%>
</table>
</body>
</html>