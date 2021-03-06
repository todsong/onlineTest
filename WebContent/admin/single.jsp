<%@page import="com.pojo.PagingResp"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.pojo.Paras"%>
<%@page import="com.pojo.PagingReq"%>
<%@page import="com.dao.impl.GenericPagingQueryDAOImpl"%>
<%@page import="com.dao.GenericPagingQueryDAO"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.dao.impl.SubjectDAOImpl"%>
<%@page import="com.pojo.Subject"%>
<%@page import="com.dao.SubjectDAO"%>
<%@page import="java.util.List"%>
<%@page import="com.dao.impl.SingleQuesDAOImpl"%>
<%@page import="com.dao.SingleQuesDAO"%>
<%@page import="com.pojo.SingleQues"%>
<%@page import="java.util.Iterator"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
String para = request.getQueryString();
if(para!=null)
{
    if(para.equals("delTooFew"))
    {
%>
        <script type="text/javascript">
        alert("删除失败。如果删除成功，题库中的题数就会小于考试的要求");
        </script>
<%
    }
    else if(para.equals("delUsed"))
    {
%>
        <script type="text/javascript">
        alert("删除失败。该题目已经有练习或考试使用");
        </script>
<%
    }
    else if(para.equals("unique"))
    {
%>
        <script type="text/javascript">
        alert("操作失败。已经存在完全一样的题目");
        </script>
<%
    }
    else
    {
        String batch = request.getParameter("batch");
        String subjectId =request.getParameter("subjectId");
   
        if(batch!=null && subjectId!=null)
        {
            SubjectDAO sd = new SubjectDAOImpl();
            String subjectName = sd.quertSubjectById(Integer.parseInt(subjectId)).getName();

            %>
            <script type="text/javascript">
            alert("成功在“<%=subjectName%>”中添加<%=batch%>道单选题");
            </script>
    <%        
        }
    }

}
%>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>单选题题库</h3>
<hr>

<form name=one action=SingleQuesAction method=post>
  <input type=submit value=新增 name=addOne id="addOne">
  <input type=submit value=批量导入 id="addBatch" name="addBatch">
</form>
<form name=query action="single.jsp" method=post>
<input type=text name=condition value='<%
    String defaultText = request.getParameter("condition");
    if(defaultText!=null)
    {
        out.print(defaultText);
    }
    %>' />
    <input type="submit" value=搜索 />
</form>

<table border="1">
<tr>
  <td  width="40">编号</td>
  <td  width="40">答案</td>
  <td  width="800">题目</td>
  <td  width="100">科目</td>
  <td  width="50">状态</td>
</tr>
  <%

    SubjectDAO sjd = new SubjectDAOImpl();
    List<Subject> sjList = sjd.getAllSubject();
    
    Map<Integer,String> subjectMap = new HashMap<Integer,String>();
    for(int i=0;i<sjList.size();i++)
    {
        subjectMap.put(sjList.get(i).getId(), sjList.get(i).getName());
    }

    GenericPagingQueryDAO gpd = new GenericPagingQueryDAOImpl();
    PagingReq req = new PagingReq();
    
    String condition = request.getParameter("condition");
    //System.out.println(condition);
    if(condition!=null && !condition.isEmpty())
    {
        List<Paras> paraList = new ArrayList<Paras>();
        for(int i=0;i<6;i++)
        {
            Paras paras = new Paras();
            if(i==0)
            {
                paras.setParaKey("qName");
            }
            else
            {
                paras.setParaKey("option"+(char)('A'+(i-1)));        
            }
            paras.setParaValue(condition);
            paras.setParaOptr("like");
            paras.setRelation("or");
            paraList.add(paras);
        }
        req.setParaList(paraList);
    }
    req.setTableName("T_SINGLE_QUES");
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
    
    PagingResp resp = gpd.pagingQuery(req);
    List sList = resp.getResList();
    int totalCount = resp.getTotalCount();
    int totalPage = resp.getTotalPage();
    int pageSize = resp.getPageSize();
    int pageNum = resp.getPageNum();
    
    int index = (pageNum-1)*pageSize+1; 
    for(Iterator<SingleQues> iter=sList.iterator(); iter.hasNext(); )
    {
        SingleQues sq = iter.next();
        out.print("<tr><td><a href=\"singleItem.jsp?id=" + sq.getId() + "\">"+index+"</a></td>");
        index++;
        out.print("<td>"+sq.getqAnswer()+"</td>");
        StringBuilder sb = new StringBuilder(sq.getqName());
        String optionA = sq.getOptionA();
        String optionB = sq.getOptionB();
        String optionC = sq.getOptionC();
        String optionD = sq.getOptionD();
        String optionE = sq.getOptionE();
        if(optionA!=null && !optionA.equals(""))
        {
        	sb.append("\tA.");
            sb.append(optionA);
        }
        if(optionB!=null && !optionB.equals(""))
        {
            sb.append("\tB.");
            sb.append(optionB);
        }
        if(optionC!=null && !optionC.equals(""))
        {
            sb.append("\tC.");
            sb.append(optionC);
        }
        if(optionD!=null && !optionD.equals(""))
        {
            sb.append("\tD.");
            sb.append(optionD);
        }
        if(optionE!=null && !optionE.equals(""))
        {
            sb.append("\tE.");
            sb.append(optionE);
        }
        out.print("<td>"+sb.toString()+"</td>");
        out.print("<td>"+subjectMap.get(sq.getSubjectId())+"</td>");
        if(sq.getStatus()==0)
            out.print("<td>正常</td>");
        else out.print("<td>废止</td>");
        out.print("</tr>"); 
    }
%>
</table>
<form action="single.jsp" onsubmit="return pageCheck()"  id="page" name="page" method="post">
<table>
<tr><td width=990 align="right">
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
if(condition!=null)
{
%>
<input type="hidden" name="condition" value="<%=condition%>" >

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
</body>
</html>