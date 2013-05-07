package com.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.dao.JudgeQuesDAO;
import com.dao.impl.JudgeQuesDAOImpl;
import com.datatype.UploadQuesFile;
import com.pojo.JudgeQues;
import com.resource.Cache;
import com.resource.DBConnection;
import com.util.MD5Util;

/**
 * 批量增加判断题的后台处理.
 * 
 * @author song
 */
public class JudgeAddBatch extends HttpServlet
{
    private static final long serialVersionUID = -6142120896371324521L;

    public UploadQuesFile uploadFile(HttpServletRequest req)
    {
        final DiskFileItemFactory factory = new DiskFileItemFactory();
        final ServletFileUpload upload = new ServletFileUpload(factory);
        InputStream inStrm = null;
        FileOutputStream fos = null;
        
        UploadQuesFile uploadQuesFile = new UploadQuesFile();
        List<FileItem> fileItems = null;
        try
        {
            fileItems = upload.parseRequest(req);
        } catch (FileUploadException e)
        {
            e.printStackTrace();
        }

        final FileItem subjectFileItem = fileItems.get(1);
        String subjectValue = subjectFileItem.getString();
        // 转换下字符集编码
        try
        {
            subjectValue = new String(subjectValue.getBytes("iso-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        uploadQuesFile.setSubjectId(Integer.parseInt(subjectValue));

        final FileItem fileItem = fileItems.get(0);
        String filename = fileItem.getName();
        if (filename.contains("\\"))
        {
            filename = filename.substring(filename.lastIndexOf('\\'));
        }

        final ServletContext context = getServletContext();

        // 上传的文件存放路径为...\\WebRoot\\upload\\filename
        String dir = context.getRealPath("upload");
        File file = new File(dir, filename);
        uploadQuesFile.setFile(file);
        try
        {
            file.createNewFile();
            // 获得流，读取数据写入文件
            inStrm = fileItem.getInputStream();
            fos = new FileOutputStream(file);

            final byte[] buffer = new byte[1024];
            int len = inStrm.read(buffer);
            while (len > 0)
            {
                fos.write(buffer, 0, len);
                len = inStrm.read(buffer);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            // 关闭资源文件操作
            try
            {
                fos.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            try
            {
                inStrm.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            // 删除临时文件
            fileItem.delete();
        }
        return uploadQuesFile;
    }

    public int checkQuesUniqueFromDB(List quesList)
    {
        final Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        ResultSet rst = null;
        int res = 0;
        
        try
        {
            pst = conn
                    .prepareStatement("select hash from t_judge_ques where hash=?");
            for(int i=0; i< quesList.size(); i++)
            {
                pst.setString(1, ((JudgeQues)quesList.get(i)).getHash());
                rst = pst.executeQuery();
                if (rst != null && rst.next())
                {
                    res = i+2;
                    throw new AddBatchException(); 
                } 
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        } catch (AddBatchException e)
        {
        }
        finally
        {
            try
            {
                rst.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
            try
            {
                pst.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
            try
            {
                conn.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return res;
    }
    

    public JudgeQues checkOneJudgeQues(String answer, String ques, int subjectId, Map hash) throws AddBatchException
    {
        if (answer == null || answer.length() != 1
                || !answer.matches("[TFtf]")) // 答案符合TF
        {
            throw new AddBatchException("answer");
        }
        else
        {
            if (ques == null || ques.equals("")) //题单为空
            {
                throw new AddBatchException("emptyQues");
            }
            else if(ques.length() > 300)
            {
                throw new AddBatchException("longQName");
            }
        }
        JudgeQues jq = new JudgeQues();
        jq.setSubjectId(subjectId);
        jq.setStatus(0);
        jq.setqAnswer(answer);
        jq.setqName(ques);
        jq.setHash(MD5Util.getMD5(jq.getSubjectId() + ques));

        String same = (String) hash.get(jq.getHash());
        if (same != null && !same.equals(""))
        {
            AddBatchException exp = new AddBatchException("same");
            exp.setSrcRow(same);
            throw exp;
        }
        return jq;
    }

    public String getUrl(UploadQuesFile uploadQuesFile)
    {
        String url = null;
        int subjectId = uploadQuesFile.getSubjectId();
        Workbook book = null;
        
        try
        {
            final List<JudgeQues> jqList = new ArrayList<JudgeQues>();
            final Map<String, String> hash = new HashMap<String, String>();
            book = Workbook.getWorkbook(uploadQuesFile.getFile());
            // 获得第一个工作表对象
            final Sheet sheet = book.getSheet(0);

            // 得到单元格
            for (int i = 1; i < sheet.getRows(); i++)
            {
                final String answer = sheet.getCell(0, i).getContents().trim()
                        .toUpperCase();
                final String ques = sheet.getCell(1, i).getContents();

                try
                {
                    JudgeQues jq = checkOneJudgeQues(answer,ques,subjectId,hash);
                    hash.put(jq.getHash(), (i + 1) + "");
                    jqList.add(jq);
                }
                catch(AddBatchException e)
                {
                    e.setErrorRow(i+1);
                    throw e;
                }
            }
            
            int checkUnique = checkQuesUniqueFromDB(jqList);
            if(checkUnique!=0)
            {
                throw new AddBatchException("unique", checkUnique);
            }
//            HttpSession httpSession = req.getSession();
//            httpSession.setAttribute("judgeBatch", jqList);

            JudgeQuesDAO sqd = new JudgeQuesDAOImpl();
            sqd.addJudgeQuesList(jqList);
            Cache.initJudgeQuesCache();
            
            url = "judge.jsp?batch=" + jqList.size() + "&subjectId="
                    + subjectId;
        } catch (AddBatchException e)
        {
            url = "addBatchJudgeQues.jsp?error=" + e.getErrorType() + "&row="
                    + e.getErrorRow();
            if (e.getSrcRow() != null)
            {
                url += "&src=" + e.getSrcRow();
            }
        } catch (Exception e)
        {
            url = "addBatchJudgeQues.jsp?fatal=file";
        } finally
        {
            book.close();
        }
        return url;
    }
    
    /**
     * 处理批量新增判断题请求.
     * 
     * @param req
     *            批量新增判断题页面表单
     * @param resp
     *            响应
     */
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    {
        UploadQuesFile uploadQuesFile = uploadFile(req);
        String url = getUrl(uploadQuesFile);
        try
        {
            resp.sendRedirect(url);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    {
        doPost(req, resp);
    }
}

class AddBatchException extends Exception
{
    private static final long serialVersionUID = -5862851861961604168L;
    private String errorType;
    private int errorRow;
    private String srcRow;

    public AddBatchException()
    {
    }

    public AddBatchException(String errorType)
    {
        this.errorType = errorType;
    }
    
    public AddBatchException(String errorType, int errorRow)
    {
        this.errorType = errorType;
        this.errorRow = errorRow;
    }
    public String getErrorType()
    {
        return errorType;
    }

    public void setErrorType(String errorType)
    {
        this.errorType = errorType;
    }

    public int getErrorRow()
    {
        return errorRow;
    }

    public void setErrorRow(int errorRow)
    {
        this.errorRow = errorRow;
    }

    public String getSrcRow()
    {
        return srcRow;
    }

    public void setSrcRow(String srcRow)
    {
        this.srcRow = srcRow;
    }
}