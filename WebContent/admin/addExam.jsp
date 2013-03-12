<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.dao.impl.SubjectDAOImpl"%>
<%@page import="com.dao.SubjectDAO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.dao.impl.JudgeQuesDAOImpl"%>
<%@page import="com.dao.impl.SingleQuesDAOImpl"%>
<%@page import="com.dao.impl.MultiQuesDAOImpl"%>
<%@page import="com.dao.MultiQuesDAO"%>
<%@page import="com.dao.SingleQuesDAO"%>
<%@page import="com.dao.JudgeQuesDAO"%>
<%@page import="com.pojo.Exam"%>
<%@page import="com.pojo.Subject"%>
<%@page import="com.dao.impl.ExamDAOImpl"%>
<%@page import="com.dao.ExamDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
String login = (String)session.getAttribute("login");
if(login==null||!login.equals("admin"))
{
    out.print("<script>window.parent.location.href='/onlineTest/login.jsp';</script>");
    return;
}
SubjectDAO sd = new SubjectDAOImpl();
List<Subject> sjList = sd.getAllSubject();
if(sjList==null || sjList.size()==0)
{
    %>
    <script>
    alert("请先新建科目，再尝试增加考试")
    window.parent.location.href='/onlineTest/admin/admin.jsp';
    </script>
    <%
    return;
}
Exam exam = null;
int[] subjects = new int[0];
int subjectSum=1;
int id=-1;
String error = request.getParameter("error");
if(error!=null)
{
    String errorSubject = request.getParameter("subject");
    //System.out.println(errorSubject);
    if(error.equals("jFew"))
    {
        %>
        <script type="text/javascript">
        alert("课目"+<%=errorSubject%>+"没有足够的判断题，操作失败");
        </script>
        <%
    }
    else if(error.equals("sFew"))
    {
        %>
        <script type="text/javascript">
        alert("课目"+<%=errorSubject%>+"没有足够的选择题，操作失败");
        </script>
        <%
    }
    else if(error.equals("mFew"))
    {
        %>
        <script type="text/javascript">
        alert("课目"+<%=errorSubject%>+"没有足够的多选题，操作失败");
        </script>
        <%
    }
    exam = (Exam)session.getAttribute("errorExam");
    if(exam!=null)
    {
        session.removeAttribute("errorExam");
    }
    String[] tmp = exam.getSubjectId().split("\\|");
    //System.out.println(exam.getSubjectId());
    subjects = new int[tmp.length];
    for(int i=0;i<tmp.length;i++)
    {
        subjects[i] = Integer.parseInt(tmp[i]);
    }
    subjectSum = subjects.length;
}
String idStr = request.getParameter("id");
if(idStr!=null)
{
    id = Integer.parseInt(request.getParameter("id"));
    if(error==null)
    {
        ExamDAO ed = new ExamDAOImpl();
        exam = ed.queryExamById(id);
        if(exam==null)
        {
            response.sendRedirect("exam.jsp");
            return;
        }
    }
    String[] tmp = exam.getSubjectId().split("\\|");
    //System.out.println(exam.getSubjectId());
    subjects = new int[tmp.length];
    for(int i=0;i<tmp.length;i++)
    {
        subjects[i] = Integer.parseInt(tmp[i]);
    }
    subjectSum = subjects.length;
}


String deYear = null;
String deMonth = null;
String deDay = null;
String deSHour = null;
String deSMinute = null;
String deEHour = null;
String deEMinute = null;
String deSubjectNum = null;
int deSubjectNumInt = 1;
SimpleDateFormat formatterFull = new SimpleDateFormat("yyyyMMddHHmm");
String currentTime = formatterFull.format(new Date());
String deSubject = null;
String subject0 = "XX";
String subject1 = "XX";
String subject2 = "XX";

if(exam==null)
{
    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
    long current = date.getTime();
    long dayLSecond = (long)(24*3600)*(long)1000;
    String tomorrow = formatter.format(new Date(current + dayLSecond));
    deYear = tomorrow.substring(0, 4);
    deMonth = tomorrow.substring(4, 6);
    if(deMonth.charAt(0)=='0')
    {
        deMonth = deMonth.charAt(1)+"";   
    }
    deDay = tomorrow.substring(6, 8);
    if(deDay.charAt(0)=='0')
    {
        deDay = deDay.charAt(1)+"";   
    }
    deSHour = "8";
    deSMinute = "00";
    deEHour = "9";
    deEMinute = "00";
    deSubjectNum = "1";
    deSubjectNumInt = 1;
}
else
{
    String startTime = exam.getStartTime();
    String endTime = exam.getEndTime();
    deYear = startTime.substring(0, 4);
    deMonth = startTime.substring(4, 6);
    
    if(deMonth.charAt(0)=='0')
    {
        deMonth = deMonth.charAt(1)+"";   
    }
    deDay = startTime.substring(6, 8);
    if(deDay.charAt(0)=='0')
    {
        deDay = deDay.charAt(1)+"";   
    }
    deSHour = startTime.substring(8, 10);
    if(deSHour.charAt(0)=='0')
    {
        deSHour = deSHour.charAt(1)+"";
    }
    deSMinute = startTime.substring(10 ,12);
    deEHour = endTime.substring(8,10);
    if(deEHour.charAt(0)=='0')
    {
        deEHour = deEHour.charAt(1)+"";
    }
    deEMinute = endTime.substring(10,12);
    deSubject = exam.getSubjectId();
    //System.out.println(deSubject);
    deSubjectNum = deSubject.split("\\|").length+"";
    deSubjectNumInt = Integer.parseInt(deSubjectNum);
    subject0 = deSubject.split("\\|")[0];
    if(deSubjectNumInt>=2)
        subject1 = deSubject.split("\\|")[1];
    if(deSubjectNumInt>=3)
        subject2 = deSubject.split("\\|")[2];
    //System.out.println(subject0+","+subject1+", "+subject2);
}
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>

var globalSubjectNum=<%=deSubjectNumInt %>;
	function check() {
		with (document.all) {
			var regex = new RegExp("^[0-9]*$");
			if (examName.value == null || examName.value == "") {
				alert("考试名称不能为空")
			} else if (examName.value.length > 50) {
				alert("考试名称不能超过50字")
			} else if (passScore.value == null || passScore.value == "") {
                alert("及格分数不能为空")
            }  else if (!regex.test(passScore.value)) {
                alert("及格分数只能输入数字")
            } else if (!regex.test(judgeScore.value)) {
				alert("判断题分值只能输入数字")
			}  else if (!regex.test(singleScore.value)) {
				alert("单选题分值只能输入数字")
			} else if (!regex.test(multiScore.value)) {
				alert("多选题分值只能输入数字")
			} 
			
			else {
				
				var jn = 0;
                var sn = 0;
                var mn = 0;
                var arr = new Array();
				for(var i=0; i<globalSubjectNum; i++)
				{
					var subjectName = "subject"+i;
					var sjName = document.getElementById(subjectName).value;
					if(sjName=="")
			        {
			            alert("请选择科目名称");
			            return;
			        }
					for(var j=0;j<i;j++)
					{
						if(sjName==arr[j])
						{
							  alert("科目名称重复选择");
							  return;
						}
					}
					arr[i] = sjName;
                    var jname = "judgeNum"+i;
                    var jvalue = document.getElementById(jname);
                    var sname = "singleNum"+i;
                    var svalue = document.getElementById(sname);
                    var mname = "multiNum"+i;
                    var mvalue = document.getElementById(mname);
					if (jvalue.value == null || jvalue.value == "") {
		                alert("判断题数不能为空");
		                return;
		            } else if (svalue.value == null || svalue.value == "") {
		                alert("单选题数不能为空");
		                return;
		            } else if (svalue.value == null || svalue.value == "") {
		                alert("多选题数不能为空");
		                return;
		            }
		            else if (!regex.test(jvalue.value)) {
		                alert("判断题数只能输入数字");
		                return;
		            } 
		            else if (!regex.test(svalue.value)) {
		                alert("单选题数只能输入数字");
		                return;
		            }
		            else if (!regex.test(mvalue.value)) {
	                    alert("多选题数只能输入数字");
	                    return;
		            }
					jn = jn + parseInt(jvalue.value, 10);
					sn = sn + parseInt(svalue.value, 10);
					mn = mn + parseInt(mvalue.value, 10);
				}
				var ps = parseInt(passScore.value, 10);
				var js = parseInt(judgeScore.value, 10);
				var ss = parseInt(singleScore.value, 10);
				var ms = parseInt(multiScore.value, 10);
				var total = (jn * js) + (sn * ss) + (mn * ms);
				var sh = startHour.value;
				var sm = startMinute.value;
				var eh = endHour.value;
				var em = endMinute.value;
				
				var y = year.value;
				var m = month.value;
				if(m.length==1)
				{
					m="0"+m
				}
				var d = day.value;
			    if(d.length==1)
                {
                    d="0"+d
                }
            
				var smi = sh + sm;
				var emi = eh + em;
				var tsmi = smi;
				if(tsmi.length==3)
                {
					tsmi="0"+tsmi;
                }	
				var st = y + m + d + tsmi;
				if (parseInt(smi) >= parseInt(emi)) {
                    alert("考试结束时间必须晚于开始时间");
                <%-- } else if (parseInt(st) < parseInt(<%=currentTime%>)) {
                    alert("考试开始时间必须晚于当前时间") --%>
                } else if (total != 100) {
					alert("题目总分不是100")
				} else if (ps > 100) {
					alert("及格分数不能大于100")
				} else
					document.forms[0].submit();
			}
		}
	}
	function jsRemoveItemFromSelect(objSelect, objItemValue) {
		// 判断是否存在  
		if (jsSelectIsExitItem(objSelect, objItemValue))
		{
			for ( var i = 0; i < objSelect.options.length; i++)
			{
				if (objSelect.options[i].value == objItemValue)
				{
					objSelect.options.remove(i);
					break;
				}
			}
		}
	}
	function jsAddItemToSelect(objSelect, objItemText, objItemValue) {
		// 判断是否存在  
		if (!jsSelectIsExitItem(objSelect, objItemValue)) {
			var varItem = new Option(objItemText, objItemValue);
			objSelect.options.add(varItem);
		}
	}
	function jsSelectIsExitItem(objSelect, objItemValue)
	{
		var isExit = false;
		for ( var i = 0; i < objSelect.options.length; i++)
		{
			if (objSelect.options[i].value == objItemValue)
			{
				isExit = true;
				break;
			}
		}
		return isExit;
	}
	function setDay()
	{
		with (document.all)
		{
	        jsRemoveItemFromSelect(day,"29");
	        jsRemoveItemFromSelect(day,"30");
	        jsRemoveItemFromSelect(day,"31");
	        if(month.value==1||month.value==3||month.value==5||month.value==7||month.value==8||month.value==10||month.value==12)
	       	{
	            jsAddItemToSelect(day,"29","29");
	        	jsAddItemToSelect(day,"30","30");
	        	jsAddItemToSelect(day,"31","31");
	       	}
	        else if(month.value==4||month.value==6||month.value==9||month.value==11)
	       	{
	            jsAddItemToSelect(day,"29","29");
	            jsAddItemToSelect(day,"30","30");
	       	}
	        else
	       	{
	            if(year.value=="2016")
            	{
	            	jsAddItemToSelect(day,"29","29");
            	}
	       	}
		}
	}
	function delAlert()
    {
         if(!confirm("确定要删除吗？"))
         {
             return false;
         }
    }
	
	function defaultSelect()
	{
		var year=<%=deYear%>
        type = document.getElementById("year");
        for( var i = 0;i<=type.options.length;i++)
        {
            if(type.options[i].value == year)
            {
                 type.options[i].selected = 'selected';
                 break;
            }
        }
        
        var month=<%=deMonth%>
        type = document.getElementById("month");
        for( var i = 0;i<=type.options.length;i++)
        {
            if(type.options[i].value == month)
            {
                 type.options[i].selected = 'selected';
                 break;
            }
        }
        var day=<%=deDay%>
        type = document.getElementById("day");
        for( var i = 0;i<=type.options.length;i++)
        {
            if(type.options[i].value == day)
            {
                 type.options[i].selected = 'selected';
                 break;
            }
        }

        var sHour=<%=deSHour%>
        type = document.getElementById("startHour");
        for( var i = 0;i<=type.options.length;i++)
        {
            if(type.options[i].value == sHour)
            {
                 type.options[i].selected = 'selected';
                 break;
            }
        }
        
        var sMinute=<%=deSMinute%>
        type = document.getElementById("startMinute");
        for( var i = 0;i<=type.options.length;i++)
        {
            if(type.options[i].value == sMinute)
            {
                 type.options[i].selected = 'selected';
                 break;
            }
        }
                
        var eHour=<%=deEHour%>
        type = document.getElementById("endHour");
        for( var i = 0;i<=type.options.length;i++)
        {
            if(type.options[i].value == eHour)
            {
                 type.options[i].selected = 'selected';
                 break;
            }
        }

        var eMinute=<%=deEMinute%>
        type = document.getElementById("endMinute");
        for( var i = 0;i<=type.options.length;i++)
        {
            if(type.options[i].value == eMinute)
            {
                 type.options[i].selected = 'selected';
                 break;
            }
        }
        
        var subjectSum = <%=subjectSum %>;
        if(subjectSum==0)
        	subjectNum=1;
        document.getElementById('subjectNum').options[subjectSum-1].selected = 'selected';
        
        var sj=<%=deSubject%>
        if(sj!=null)
        {
            var sbnum = <%=subjectSum%>
            for(var sid = 0; sid < sbnum; sid++)
            {
            	var val;
                if(sid==0)
                    val = <%=subject0%>;
                else if(sid==1)
                    val = <%=subject1%>;
                else if(sid==2)
                    val = <%=subject2%>;
                type = document.getElementById("subject"+sid);
            	for( var i = 0;i<=type.options.length;i++)
                {
                    if(type.options[i].value == val)
                    {
                         type.options[i].selected = 'selected';
                         break;
                    }
                }
           	}
        }
        if(subjectSum==1)
       	{
        	document.getElementById('t1').style.display = "none";
            document.getElementById('t2').style.display = "none";
       	}
        else if(subjectSum==2)
       	{
            document.getElementById('t2').style.display = "none";
       	}
        
	}
	function setSubject()
	{
        document.getElementById('t1').style.display = "none";
        document.getElementById('t2').style.display = "none";
		var snum = document.getElementById("subjectNum").value;
		globalSubjectNum = snum;
	    for( var i = 0;i<snum;i++)
        {
            var name = "t" + i;
            //alert(name);
            document.getElementById(name).style.display = "block";
        }

	}
	
</script>

</head>
<body onload="defaultSelect()">

<%
if(idStr==null)
{
%>
<h3>新增考试</h3>
<%
}
else
{
%>
<h3><%=exam.getExamName() %></h3>
<%
}
%>

<hr>
	<form id="addE" name="addE" action="ExamAction" method=post onsubmit="return delAlert()">
		<table>
			<tr>
				<td>考试名称</td>
				<td colspan="2"><input type=text name=examName value="<%
				if(exam!=null)
				{
				    out.print(exam.getExamName());
				}
				%>"
					style="width: 200px"></td>
			</tr>
			<tr>
				<td>考试日期</td>
				<td colspan="2"><select name="year" id="year">
						<option value="2013">2013</option>
						<option value="2014">2014</option>
						<option value="2015">2015</option>
						<option value="2016">2016</option>
                        <option value="2017">2017</option>
                        <option value="2018">2018</option>
				</select>年
				<select name="month" id="month">
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
						<option value="6">6</option>
						<option value="7">7</option>
						<option value="8">8</option>
						<option value="9">9</option>
						<option value="10">10</option>
						<option value="11">11</option>
						<option value="12">12</option>
				</select>月
				<select name="day" id="day" onFocus="setDay()">
						<option value="1">1</option>
						<option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                        <option value="13">13</option>
                        <option value="14">14</option>
                        <option value="15">15</option>
                        <option value="16">16</option>
                        <option value="17">17</option>
                        <option value="18">18</option>
                        <option value="19">19</option>
                        <option value="20">20</option>
                        <option value="21">21</option>
                        <option value="22">22</option>
                        <option value="23">23</option>
                        <option value="24">24</option>
                        <option value="25">25</option>
                        <option value="26">26</option>
                        <option value="27">27</option>
                        <option value="28">28</option>
                        <option value="29">29</option>
                        <option value="30">30</option>
                        <option value="31">31</option>
				</select>日</td>
			<tr>
				<td>开始时间</td>
				<td colspan="2"><select name="startHour" id="startHour">
						<option value="8">8</option>
						<option value="9">9</option>
						<option value="10">10</option>
						<option value="11">11</option>
						<option value="12">12</option>
						<option value="13">13</option>
						<option value="14">14</option>
						<option value="15">15</option>
						<option value="16">16</option>
						<option value="17">17</option>
						<option value="18">18</option>
						<option value="19">19</option>
						<option value="20">20</option>
						<option value="21">21</option>
						<option value="22">22</option>
						<option value="23">23</option>
						<option value="0">0</option>
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
						<option value="6">6</option>
						<option value="7">7</option>
				</select>时 <select name="startMinute" id="startMinute">
						<option value="00">00</option>
						<option value="05">05</option>
						<option value="10">10</option>
						<option value="15">15</option>
						<option value="20">20</option>
						<option value="25">25</option>
						<option value="30">30</option>
						<option value="35">35</option>
						<option value="40">40</option>
						<option value="45">45</option>
						<option value="50">50</option>
						<option value="55">55</option>
				</select>分</td>
			</tr>
			<tr>
				<td>结束时间</td>
				<td colspan="2"><select name="endHour" id="endHour">
						<option value="8">8</option>
						<option value="9">9</option>
						<option value="10">10</option>
						<option value="11">11</option>
						<option value="12">12</option>
						<option value="13">13</option>
						<option value="14">14</option>
						<option value="15">15</option>
						<option value="16">16</option>
						<option value="17">17</option>
						<option value="18">18</option>
						<option value="19">19</option>
						<option value="20">20</option>
						<option value="21">21</option>
						<option value="22">22</option>
						<option value="23">23</option>
						<option value="0">0</option>
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
						<option value="6">6</option>
						<option value="7">7</option>
				</select>时 <select name="endMinute" id="endMinute">
						<option value="00">00</option>
						<option value="05">05</option>
						<option value="10">10</option>
						<option value="15">15</option>
						<option value="20">20</option>
						<option value="25">25</option>
						<option value="30">30</option>
						<option value="35">35</option>
						<option value="40">40</option>
						<option value="45">45</option>
						<option value="50">50</option>
						<option value="55">55</option>
				</select>分</td>
			</tr>
			<tr>
                <td>及格分数</td>
                <td colspan="2"><input type=text name=passScore value="<%
                if(exam!=null)
                {
                    out.print(exam.getPassScore());
                }
                %>"
                 style="width: 30px"></td>
            </tr>
            <tr>
                <td>判断题分值</td>
                <td colspan="2"><input type=text name=judgeScore value="<%
                if(exam!=null)
                {
                    out.print(exam.getJudgeScore());
                }
                %>"
                    style="width: 30px"></td>
            </tr>
            <tr>
                <td>单选题分值</td>
                <td colspan="2"><input type=text name=singleScore value="<%
                if(exam!=null)
                {
                    out.print(exam.getSingleScore());
                }
                %>"
                    style="width: 30px"></td>
            </tr>
            <tr>
                <td>多选题分值</td>
                <td colspan="2"><input type=text name=multiScore value="<%
                if(exam!=null)
                {
                    out.print(exam.getMutliScore());
                }
                %>"
                    style="width: 30px"></td>
            </tr>
            
			<tr>
			  <td>科目数量</td>
			  <td colspan="2">
			    <select name="subjectNum" id="subjectNum" onchange="setSubject()">
			      <option value="1">1
                  <option value="2">2
                  <option value="3">3
			    </select>
			  </td>
			</tr>
            </table>
            <%for(int tid=0;tid<3;tid++) { %>
            <table id="t<%=tid %>" align="left">			
			<tr>
             <td>科目<%=tid+1 %></td>
            <td colspan="2">
            <select name="subject<%=tid %>" id="subject<%=tid %>">
                <option value="">--请选择--
            <%
            for(int i=0;i<sjList.size();i++)
            {
            %>
            <option value="<%=sjList.get(i).getId() %>" <%
            if(exam!=null && subjects[0] ==sjList.get(i).getId())
            {
                out.print("selected='selected'");
            }
            %>><%=sjList.get(i).getName() %></option>
            <%
            }
            %>
            </select>
            </tr>
            <tr>
                <td>判断题数</td>
                <td colspan="2"><input type=text name=judgeNum<%=tid %> id=judgeNum<%=tid %> value="<%
                if(exam!=null && deSubjectNumInt > tid)
                {
                    out.print(exam.getJudgeNum().split("\\|")[tid]);
                }
                %>"
                    style="width: 30px"></td>
            </tr>
            <tr>
                <td>单选题数</td>
                <td colspan="2"><input type=text name=singleNum<%=tid %> id=singleNum<%=tid %> value="<%
                if(exam!=null  && deSubjectNumInt > tid)
                {
                    out.print(exam.getSingleNum().split("\\|")[tid]);
                }
                %>"
                    style="width: 30px"></td>
            </tr>
            <tr>
                <td>多选题数</td>
                <td colspan="2"><input type=text name=multiNum<%=tid %> id=multiNum<%=tid %> value="<%
                if(exam!=null  && deSubjectNumInt > tid)
                {
                    out.print(exam.getMutliNum().split("\\|")[tid]);
                }
                %>"
                    style="width: 30px"></td>
            </tr>
          </table>
            
		<%
            } %>
        <table height="120">
          <tr><td>&nbsp;</td></tr>
        </table>
		<%
		if(idStr==null)
		{
		%>
          <input type="hidden" name="addE" value="addE" >
		  <input type=button onclick="check()" value="提交" id="addE" name="addE" >
		<%
	    }
		else
		{
		%>
          <input type="hidden" name="id" value="<%out.print(exam.getId());%>" >
          <input type="hidden" name="upE" value="upE" >
          <input type=button onclick="check()" value="更新" id="upE" name="upE" >
          <input type=submit value="删除" name="del" >
		<%
		}
		%>
	</form>
</body>
</html>