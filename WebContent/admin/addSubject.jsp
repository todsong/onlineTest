<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<h3>新增试题科目</h3>
<hr>
<script language="javascript">
function check()
{ 
    with(document.all)
    {
        if(document.addSubject.subjectName.value==null||document.addSubject.subjectName.value=="")
        {
            alert("请输入科目名称");
            return false;
        }
        else if(document.addSubject.subjectName.value.length>=30)
        {
            alert("科目名称不能超过30字");
            return false;
        }
        else return true;
  }
}

</script>
<form id=addSubject name=addSubject action="SubjectAction" method=post onsubmit="return check()" >
    科目名称：<input type="text" style="width:200px" name="subjectName" id="subjectName" >
    <input type="submit" value="完成新增" name="addD" id="addD">
</form>
    
</body>
</html>