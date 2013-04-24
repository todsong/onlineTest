<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.dao.impl.MultiQuesDAOImpl"%>
<%@page import="com.dao.MultiQuesDAO"%>
<%@page import="com.pojo.MultiQues"%>
<%@page import="com.pojo.Subject"%>
<%@page import="com.dao.impl.SubjectDAOImpl"%>
<%@page import="java.util.List"%>
<%@page import="com.dao.SubjectDAO"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%

SubjectDAO sjd = new SubjectDAOImpl();
List<Subject> sjList = sjd.getAllSubject();
int optNum = 0;
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

</head>
<body>
<h3>多选题管理</h3>
<hr>
	<form name=upd id=upd action=MultiQuesAction method=post
		onsubmit="return delAlert()">
            <%
                String para = request.getQueryString();
                        if (para == null || para.equals(""))
                        {

                        } else
                        {
                            int id = Integer.parseInt(para.substring(3));
                            MultiQuesDAO sd = new MultiQuesDAOImpl();
                            MultiQues sq = sd.queryMultiQuesById(id);
            %>
		      <input type="hidden" name="id" value=<%=id %> >
		<table>
			<%-- <tr>
				<td>编号：</td>
				<td colspan="2">
					<%
						out.print(id);
					%>
				</td>
			</tr> --%>
			<tr>
				<td>题目：</td>
				<td colspan="2"><textarea rows="4" cols="80" name="ques" id="ques"><%
							out.print(sq.getqName());
						%></textarea></td>
			</tr>
		<%
			
		    String[] option = new String[5];
		    if(sq.getOptionA()!=null && !sq.getOptionA().equals(""))
            {
                option[0] = sq.getOptionA();
                optNum++;
            }
		    if(sq.getOptionB()!=null && !sq.getOptionB().equals(""))
	        {
	            option[1] = sq.getOptionB();
	            optNum++;
	        }
		    if(sq.getOptionC()!=null && !sq.getOptionC().equals(""))
            {
                option[2] = sq.getOptionC();
                optNum++;
            }
		    if(sq.getOptionD()!=null && !sq.getOptionD().equals(""))
            {
                option[3] = sq.getOptionD();
                optNum++;
            }
		    if(sq.getOptionE()!=null && !sq.getOptionE().equals(""))
            {
                option[4] = sq.getOptionE();
                optNum++;
            }
            String defaultSelect = sq.getqAnswer();
            for (int i = 0; i < optNum; i++)
            {
                String opt = (char) ('A' + i) + "";
                out.print("<tr><td>");
                out.print("<input type=checkbox name=answer value="+opt);
                
                if (defaultSelect != null && defaultSelect.contains(opt))
                {
                    out.print(" checked=\"checked\"");
                }
                out.print(" >"+opt+". </td>");
                out.print("<td><textarea rows=2 cols=80 name=t"+opt+" id=t"+opt+">"+option[i]+"</textarea></td>");
                out.print("</tr>");
            }
		%>
		</table>
		科目：<select name="subject" id="subject">
<%

for(int i=0;i<sjList.size();i++)
{
    out.print("<option value=\""+sjList.get(i).getId()+"\" ");
    if(sjList.get(i).getId()==sq.getSubjectId())
    {
        out.print("selected=selected");
    }
    out.print(">"+sjList.get(i).getName()+"</option>");    
}
%>
</select>
<br/>
		        状态：<select name="status" id="status">
        <option value="0" <%if(sq.getStatus()==0) out.print("selected=selected"); %> >正常</option>
        <option value="1" <%if(sq.getStatus()==1) out.print("selected=selected"); %> >废止</option>
        </select>
        <br/>
		
		<input type="hidden" name="id" value="<%out.print(id);%>"> <input
			type=button value=更新 name=up onclick="check()" id="up"> <input
			type=submit value=删除 name=del>
	</form>
	<%
		}
	%>
</body>
<script>
    function check() {
        var ques = document.getElementById('ques').value;
        var tA = document.getElementById('tA');
        var tB = document.getElementById('tB');
        var tC = document.getElementById('tC');
        var tD = document.getElementById('tD');
        var tE = document.getElementById('tE');
        
        if (ques == null || ques == "") {
            alert("题目不能为空");
        }
        else if(ques.length>=300)
        {
            alert("题目长度不能超过300")
        }
        else if(tA!=null && (tA.value==null || tA.value==""))
        {
            alert("选项A不能为空")
        }
        else if(tA!=null && tA.value.length>=300)
        {
            alert("选项A长度不能超过300")
        }
        else if(tB!=null && (tB.value==null || tB.value==""))
        {
            alert("选项B不能为空")
        }
        else if(tB!=null && tB.value.length>=300)
        {
            alert("选项B长度不能超过300")
        }
        else if(tC!=null && (tC.value==null || tC.value==""))
        {
            alert("选项C不能为空")
        }
        else if(tC!=null && tC.value.length>=300)
        {
            alert("选项C长度不能超过300")
        }
        else if(tD!=null && (tD.value==null || tD.value==""))
        {
            alert("选项D不能为空")
        }
        else if(tD!=null && tD.value.length>=300)
        {
            alert("选项D长度不能超过300")
        }
        else if(tE!=null && (tE.value==null || tA.value==""))
        {
            alert("选项E不能为空")
        }
        else if(tE!=null && tE.value.length>=300)
        {
            alert("选项E长度不能超过300")
        }
        
        else
        {
            var obj = document.getElementsByName('answer')
            var count=0;

            var nums = <%=optNum %>;
            for(var i=0; i<nums ; i++)
            {
                if(obj[i].checked)
                {
                    count++;
                }
            }
            if(count==0)
            {
                alert("答案未选择")
            }
            else document.forms[0].submit();
        }
    }
    function delAlert() {
        if (!confirm("确定要删除吗？")) {
            return false;
        }
    }
</script>
</html> 