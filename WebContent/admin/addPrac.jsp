<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.dao.impl.JudgeQuesDAOImpl"%>
<%@page import="com.dao.impl.SingleQuesDAOImpl"%>
<%@page import="com.dao.impl.MultiQuesDAOImpl"%>
<%@page import="com.dao.MultiQuesDAO"%>
<%@page import="com.dao.SingleQuesDAO"%>
<%@page import="com.dao.JudgeQuesDAO"%>
<%@page import="com.pojo.Exam"%>
<%@page import="com.dao.impl.ExamDAOImpl"%>
<%@page import="com.dao.ExamDAO"%>
<%@page import="com.dao.impl.SubjectDAOImpl"%>
<%@page import="com.dao.SubjectDAO"%>
<%@page import="com.pojo.Subject"%>
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
int id=-1;
String error = request.getParameter("error");
if(error!=null)
{
	if(error.equals("jFew"))
	{
	    %>
	    <script type="text/javascript">
	    alert("没有足够的判断题，操作失败");
	    </script>
	    <%
	}
	else if(error.equals("sFew"))
	{
	    %>
	    <script type="text/javascript">
	    alert("没有足够的选择题，操作失败");
	    </script>
	    <%
	}
	else if(error.equals("mFew"))
	{
	    %>
	    <script type="text/javascript">
	    alert("没有足够的多选题，操作失败");
	    </script>
	    <%
	}
	exam = (Exam)session.getAttribute("errorExam");
    if(exam!=null)
    {
        session.removeAttribute("errorExam");
    }
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
		    response.sendRedirect("practise.jsp");
		    return;
		}
	}
}

String deSYear = null;
String deSMonth  =null;
String deSDay    =null;
String deSHour   =null;
String deEYear   =null;
String deEMonth  =null;
String deEDay    =null;
String deEHour   =null;

SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
SimpleDateFormat formatterFull = new SimpleDateFormat("yyyyMMddHH");
String currentTime = formatterFull.format(new Date());
if(exam==null)
{
    Date date = new Date();
    long current = date.getTime();
    long dayLSecond = (long)(24*3600)*(long)1000;
    String tomorrow = formatter.format(new Date(current + dayLSecond));
    deSYear = tomorrow.substring(0,4);
    deSMonth = tomorrow.substring(4,6);
    deSDay = tomorrow.substring(6,8);
    deSHour = "08";
    deEYear  = tomorrow.substring(0,4);
    deEMonth = tomorrow.substring(4,6);
    deEDay = tomorrow.substring(6,8);
    deEHour = "20";
}
else
{
    String startTime = exam.getStartTime();
    String endTime = exam.getEndTime();
    deSYear = startTime.substring(0,4);
    deSMonth = startTime.substring(4,6);
    deSDay = startTime.substring(6,8);
    deSHour = startTime.substring(8,10);
    deEYear  = endTime.substring(0,4);
    deEMonth = endTime.substring(4,6);
    deEDay = endTime.substring(6,8);
    deEHour = endTime.substring(8,10);
}
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
	function check() {
		with (document.all) {
			var regex = new RegExp("^[0-9]*$");
			if (examName.value == null || examName.value == "") {
				alert("练习名称不能为空")
			} else if (examName.value.length > 50) {
				alert("练习名称不能超过50字")
			} else if (judgeNum.value == null || judgeNum.value == "") {
                alert("判断题数不能为空")
            } else if (singleNum.value == null || singleNum.value == "") {
				alert("单选题数不能为空")
			} else if (multiNum.value == null || multiNum.value == "") {
				alert("多选题数不能为空")
			} else if (!regex.test(judgeNum.value)) {
				alert("判断题数只能输入数字")
			} else if (!regex.test(singleNum.value)) {
				alert("单选题数只能输入数字")
			} else if (!regex.test(multiNum.value)) {
				alert("多选题数只能输入数字")
			} else {
				var sy = syear.value;
				var sm = smonth.value;
				var sd = sday.value;
				var sh = shour.value;
				var ey = eyear.value;
                var em = emonth.value;
                var ed = eday.value;
				var eh = ehour.value;
				
				var smi = sy + sm + sd + sh;
				var emi = ey + em + ed + eh;
				if (parseInt(smi) >= parseInt(emi)) {
                    alert("练习结束时间必须晚于开始时间")
                } else if (parseInt(emi) < parseInt(<%=currentTime%>)) {
                    alert("练习结束时间必须晚于当前时间")
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
	function setsDay()
	{
		with (document.all)
        {
	        jsRemoveItemFromSelect(sday,"29");
	        jsRemoveItemFromSelect(sday,"30");
	        jsRemoveItemFromSelect(sday,"31");
	        if(smonth.value==1||smonth.value==3||smonth.value==5||smonth.value==7||smonth.value==8||smonth.value==10||smonth.value==12)
	       	{
	            jsAddItemToSelect(sday,"29","29");
	        	jsAddItemToSelect(sday,"30","30");
	        	jsAddItemToSelect(sday,"31","31");
	       	}
	        else if(smonth.value==4||smonth.value==6||smonth.value==9||smonth.value==11)
	       	{
	            jsAddItemToSelect(sday,"29","29");
	            jsAddItemToSelect(sday,"30","30");
	       	}
	        else
	       	{
	            if(syear.value=="2016")
	            	{
	            	jsAddItemToSelect(sday,"29","29");
	            	}
	       	}
        }
	}
	function seteDay()
    {
        jsRemoveItemFromSelect(eday,"29");
        jsRemoveItemFromSelect(eday,"30");
        jsRemoveItemFromSelect(eday,"31");
        if(emonth.value==1||emonth.value==3||emonth.value==5||emonth.value==7||emonth.value==8||emonth.value==10||emonth.value==12)
        {
            jsAddItemToSelect(eday,"29","29");
            jsAddItemToSelect(eday,"30","30");
            jsAddItemToSelect(eday,"31","31");
        }
        else if(emonth.value==4||emonth.value==6||emonth.value==9||emonth.value==11)
        {
            jsAddItemToSelect(eday,"29","29");
            jsAddItemToSelect(eday,"30","30");
        }
        else
        {
            if(eyear.value=="2016")
                {
                jsAddItemToSelect(eday,"29","29");
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
        var val=<%=deSYear%>
        var type = document.getElementById("syear");
        for( var i = 0;i<=type.options.length;i++)
        {
            if(type.options[i].value == val)
            {
                 type.options[i].selected = 'selected';
                 break;
            }
        }
        val=<%=deSMonth%>
        type = document.getElementById("smonth");
        for( var i = 0;i<=type.options.length;i++)
        {
            if(type.options[i].value == val)
            {
                 type.options[i].selected = 'selected';
                 break;
            }
        }
        
        val=<%=deSDay%>
        type = document.getElementById("sday");
        for( var i = 0;i<=type.options.length;i++)
        {
            if(type.options[i].value == val)
            {
                 type.options[i].selected = 'selected';
                 break;
            }
        }
        val=<%=deSHour%>
        type = document.getElementById("shour");
        for( var i = 0;i<=type.options.length;i++)
        {
            if(type.options[i].value == val)
            {
                 type.options[i].selected = 'selected';
                 break;
            }
        }
        val=<%=deEYear%>
        type = document.getElementById("eyear");
        for( var i = 0;i<=type.options.length;i++)
        {
            if(type.options[i].value == val)
            {
                 type.options[i].selected = 'selected';
                 break;
            }
        }
        val=<%=deEMonth%>
        type = document.getElementById("emonth");
        for( var i = 0;i<=type.options.length;i++)
        {
            if(type.options[i].value == val)
            {
                 type.options[i].selected = 'selected';
                 break;
            }
        }
        val=<%=deEDay%>
        type = document.getElementById("eday");
        for( var i = 0;i<=type.options.length;i++)
        {
            if(type.options[i].value == val)
            {
                 type.options[i].selected = 'selected';
                 break;
            }
        }
        val=<%=deEHour%>
        type = document.getElementById("ehour");
        for( var i = 0;i<=type.options.length;i++)
        {
            if(type.options[i].value == val)
            {
                 type.options[i].selected = 'selected';
                 break;
            }
        }
	}
	
</script>

</head>
<body onload="defaultSelect()">

<%
if(idStr==null)
{
%>
<h3>新增练习</h3>
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
	<form id="addP" name="addP" action="PracAction" method=post onsubmit="return delAlert()">
		<table>
			<tr>
				<td>练习名称</td>
				<td colspan="2"><input type=text name=examName value="<%
				if(exam!=null)
				{
				    out.print(exam.getExamName());
				}
				%>"
					style="width: 200px"></td>
			</tr>
			<tr>
             <td>科目</td>
            <td colspan="2">
            <select name="subject" id="subject">
            
            <%
            for(int i=0;i<sjList.size();i++)
            {
            %>
            <option value="<%=sjList.get(i).getId() %>" <%
            if(exam!=null && exam.getSubjectId()==sjList.get(i).getId())
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
				<td>开始时间</td>
				<td colspan="2"><select name="syear" id="syear">
						<option value="2013">2013</option>
						<option value="2014">2014</option>
						<option value="2015">2015</option>
						<option value="2016">2016</option>
                        <option value="2017">2017</option>
                        <option value="2018">2018</option>
				</select>年
				<select name="smonth" id="smonth">
						<option value="01">1</option>
						<option value="02">2</option>
						<option value="03">3</option>
						<option value="04">4</option>
						<option value="05">5</option>
						<option value="06">6</option>
						<option value="07">7</option>
						<option value="08">8</option>
						<option value="09">9</option>
						<option value="10">10</option>
						<option value="11">11</option>
						<option value="12">12</option>
				</select>月
				<select name="sday" id="sday" onFocus="setsDay()">
						<option value="01">1</option>
						<option value="02">2</option>
                        <option value="03">3</option>
                        <option value="04">4</option>
                        <option value="05">5</option>
                        <option value="06">6</option>
                        <option value="07">7</option>
                        <option value="08">8</option>
                        <option value="09">9</option>
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
				</select>日 <select name="shour" id="shour">
						<option value="08">8</option>
						<option value="09">9</option>
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
						<option value="01">1</option>
						<option value="02">2</option>
						<option value="03">3</option>
						<option value="04">4</option>
						<option value="05">5</option>
						<option value="06">6</option>
						<option value="07">7</option>
				</select>时
			</tr>
			<tr>
				<td>结束时间</td>
				<td colspan="2"><select name="eyear" id="eyear">
                        <option value="2013">2013</option>
                        <option value="2014">2014</option>
                        <option value="2015">2015</option>
                        <option value="2016">2016</option>
                        <option value="2017">2017</option>
                        <option value="2018">2018</option>
                </select>年
                <select name="emonth" id="emonth">
                        <option value="01">1</option>
                        <option value="02">2</option>
                        <option value="03">3</option>
                        <option value="04">4</option>
                        <option value="05">5</option>
                        <option value="06">6</option>
                        <option value="07">7</option>
                        <option value="08">8</option>
                        <option value="09">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                </select>月
                <select name="eday" id="eday" onFocus="seteDay()">
                        <option value="01">1</option>
                        <option value="02">2</option>
                        <option value="03">3</option>
                        <option value="04">4</option>
                        <option value="05">5</option>
                        <option value="06">6</option>
                        <option value="07">7</option>
                        <option value="08">8</option>
                        <option value="09">9</option>
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
                        
                </select>日 <select name="ehour" id="ehour">
						<option value="08">8</option>
						<option value="09" selected="selected">9</option>
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
						<option value="01">1</option>
						<option value="02">2</option>
						<option value="03">3</option>
						<option value="04">4</option>
						<option value="05">5</option>
						<option value="06">6</option>
						<option value="07">7</option>
				</select>时 </td>
			</tr>
			<tr>
				<td>判断题数</td>
				<td colspan="2"><input type=text name=judgeNum value="<%
                if(exam!=null)
                {
                    out.print(exam.getJudgeNum());
                }
                %>"
					style="width: 30px"></td>
			</tr>
			<tr>
				<td>单选题数</td>
				<td colspan="2"><input type=text name=singleNum value="<%
                if(exam!=null)
                {
                    out.print(exam.getSingleNum());
                }
                %>"
					style="width: 30px"></td>
			</tr>
		
			<tr>
				<td>多选题数</td>
				<td colspan="2"><input type=text name=multiNum value="<%
                if(exam!=null)
                {
                    out.print(exam.getMutliNum());
                }
                %>"
					style="width: 30px"></td>
			</tr>
			
		</table>
		<%
		if(idStr==null)
		{
		%>
          <input type="hidden" name="addP" value="addP" >
		  <input type=button onclick="check()" value="提交" id="addP" name="addP" >
		<%
	    }
		else
		{
		%>
          <input type="hidden" name="id" value="<%out.print(exam.getId());%>" >
          <input type="hidden" name="upP" value="upP" >
          <input type=button onclick="check()" value="更新" id="upP" name="upP" >
          <input type=submit value="删除" name="del" >
		<%
		}
		%>
		
	</form>
</body>
</html>