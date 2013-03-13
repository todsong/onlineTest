<%@page import="com.pojo.Paras"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="com.pojo.PagingResp"%>
<%@page import="com.pojo.PagingReq"%>
<%@page import="com.dao.impl.GenericPagingQueryDAOImpl"%>
<%@page import="com.dao.GenericPagingQueryDAO"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@ page import="java.util.List" %>    
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.pojo.User" %>
<%@ page import="com.dao.UserDAO" %>
<%@ page import="com.dao.impl.UserDAOImpl" %>
<%@page import="com.dao.impl.DeptDAOImpl"%>
<%@page import="com.pojo.Dept"%>
<%@page import="com.dao.DeptDAO"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>在线考试系统管理界面</title>
</head>

<%
request.setCharacterEncoding("utf-8");
String login = (String)session.getAttribute("login");
if(login==null||!login.equals("admin"))
{
    out.print("<script>window.parent.location.href='/onlineTest/login.jsp';</script>");
    return;
}

DeptDAO dd = new DeptDAOImpl();
List<Dept> deptList = dd.getAllDept();
Map<Integer,String> deptMap = new HashMap<Integer,String>();
for(int i=0;i<deptList.size();i++)
{
    deptMap.put(deptList.get(i).getId(), deptList.get(i).getName());
}


%>

<body>

<h3>考生管理</h3>
<hr>
<form name=query action="userAdmin.jsp" method=post>
	<select name="column">
	    <option value="id"
	    <%
	    String defaultSelect = request.getParameter("column");
	    if(defaultSelect==null||defaultSelect.equals("id"))
	    {
	        out.print("selected='selected'");
	    }
	    %>>工号</option>
	    <option value="name"
	    <%
        if(defaultSelect!=null && defaultSelect.equals("name"))
        {
            out.print("selected='selected'");
        }
        %>>姓名</option>
	    <option value="dept"
	    <%
        if(defaultSelect!=null && defaultSelect.equals("dept"))
        {
            out.print("selected='selected'");
        }
        %>>部门</option>
	</select>
    <input type=text name=condition value='<%
    String defaultText = request.getParameter("condition");
    if(defaultText!=null)
    {
        out.print(defaultText);
    }
    %>' />
	<input type="submit" value=查询 />
</form>

<br>
<table border="1">
<tr>
  <td  width="200">工号</td>
  <td  width="200">姓名</td>
  <td  width="200">部门</td>
  <td  width="200">联系电话</td>
  <td  width="80">状态</td>
</tr>
<%
	UserDAO ud = new UserDAOImpl();
	List userList = null;
	String column = request.getParameter("column");
	String condition = request.getParameter("condition");
	
	
    GenericPagingQueryDAO gpd = new GenericPagingQueryDAOImpl();
    PagingReq req = new PagingReq();
    Paras para = new Paras();
    para.setParaKey(column);
    
    
    if(column!=null)
    {
	    if(column.equals("dept"))
	    {
	        para.setParaOptr("IN");
	        List<Integer> deptIdList = new ArrayList<Integer>();
	        for(int i=0;i<deptList.size();i++)
	        {
	            if(deptList.get(i).getName().contains(condition))
	            {
	                deptIdList.add(deptList.get(i).getId());
	            }
	        }
	        StringBuilder sb = new StringBuilder("");
	        if(deptIdList==null||deptIdList.size()==0)
	        {
	            sb.append("0");
	        }
	        else
	        {
	            for(int i=0;i<deptIdList.size();i++)
	            {
	                sb.append(deptIdList.get(i));
	                if(i<deptIdList.size()-1)
	                {
	                    sb.append(",");
	                }
	            }
	        }
	        para.setParaValue(sb.toString());
	        para.setRelation("and");
	    }
	    else
	    {
	        para.setParaOptr("like");
	        para.setParaValue(condition);
	        para.setRelation("and");
	    }
	//    System.out.println(column+" "+ req.getParaOptr() +" "+condition);
    }
    
    String pageNumS = request.getParameter("pageNum");
    if(pageNumS==null||pageNumS.isEmpty())
        req.setPageNum(1);
    else
        req.setPageNum(Integer.parseInt(pageNumS));
    
    String pageSizeS = request.getParameter("pageSize");
    if(pageSizeS==null||pageSizeS.isEmpty())
        req.setPageSize(10);
    else
        req.setPageSize(Integer.parseInt(pageSizeS));
    req.setTableName("T_USER");
    List<Paras> paraList = new ArrayList<Paras>();
    if(para.getParaKey()!=null && para.getParaValue()!=null && para.getParaValue()!=null)
    {
        paraList.add(para);   
    }
    req.setParaList(paraList);
    
    PagingResp resp = gpd.pagingQuery(req);
    userList = resp.getResList();
    int totalCount = resp.getTotalCount();
    int totalPage = resp.getTotalPage();
    int pageSize = resp.getPageSize();
    int pageNum = resp.getPageNum();
//System.out.println(pageSize);
    
    for(Iterator<User> iter=userList.iterator(); iter.hasNext(); )
	{
	    User user = iter.next();   
	    out.print("<tr><td><a href=\"userScore.jsp?id="+user.getId()+"\">"+user.getId()+"</a></td>");
	    out.print("<td>"+user.getName()+"</td>");
	    out.print("<td>"+deptMap.get(user.getDept())  +"</td>");
	    String tel = user.getTelephone();
	    if(tel==null || tel.isEmpty())
	        out.print("<td>&nbsp;</td>");
	    else
           out.print("<td>"+tel+"</td>");
           
           if(user.getStatus().equals("0"))
           {
               out.print("<td>已激活</td></tr>");
           }
           else
           {
               out.print("<td>未激活</td></tr>");
           }
	}
    
%>
</table>
<form action="userAdmin.jsp" onsubmit="return pageCheck()"  id="page" name="page" method="post">
<table>
<tr><td width=880 align="right">
<input type="text" name="pageSize" id="pageSize" value=<%=pageSize %> style="width:20px">
行/页   共<%=totalCount %>条记录  第<%=pageNum %>/<%=totalPage %>页
<%
if(pageNum!=1)
{
%>
<a href="javascript:nextPage()">上一页</a>
<%
}
if(pageNum<totalPage)
{
%>
<a href="javascript:prevPage()">下一页</a>
<%
}
%>
跳转至第<input type="text" name="pageNum" id="pageNum" style="width:25px">页
<%
if(column!=null)
{
%>
<input type="hidden" name="column" value="<%=column %>" >
<%
}
if(condition!=null)
{
%>
<input type="hidden" name="condition" value=<%=condition %> >
<%
}
%>
<input type="submit" value="Go" >
</td></tr>
</table>
</form>
</body>
<script>
function pageCheck()
{
    with(document.all)
    {
        var regex = new RegExp("^[0-9]*$");
        if (!regex.test(pageNum.value))
        {
        	alert("显示行数只能输入数字");
        	return false;
        }
        else if (!regex.test(pageSize.value))
        {
            alert("页码只能输入数字");
            return false;
        }
        else if (parseInt(pageNum.value,10)><%=totalPage%>)
        {
            alert("输入页码错误");
            return false;
        }
        
    }
    return true;
}
function nextPage()
{
    document.page.pageNum.value=<%=pageNum-1 %>   
    document.page.submit();
}
function prevPage()
{
	with(document.all)
	{
	    pageNum.value=<%=pageNum+1 %>
	    page.submit();
	}
}
</script>
</html>