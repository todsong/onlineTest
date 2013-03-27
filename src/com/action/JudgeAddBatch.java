package com.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.dao.JudgeQuesDAO;
import com.dao.SubjectDAO;
import com.dao.impl.JudgeQuesDAOImpl;
import com.dao.impl.SubjectDAOImpl;
import com.pojo.JudgeQues;
import com.resource.Cache;
import com.resource.DBConnection;
import com.util.MD5Util;

public class JudgeAddBatch extends HttpServlet
{
    private static final long serialVersionUID = -6142120896371324521L;

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    {
        try
        {
            req.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e1)
        {
            e1.printStackTrace();
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        String filename = null;
        String dir = null;
        int subjectId = 0;

        try
        {
            List<FileItem> fileitems = upload.parseRequest(req);
            for (FileItem item : fileitems)
            {
                if (item.isFormField() && item.getFieldName().equals("subject"))
                {
                    String value = item.getString();

                    // 转换下字符集编码
                    value = new String(value.getBytes("iso-8859-1"), "utf-8");
                    // System.out.println(name + "=" + value);
                    subjectId = Integer.parseInt(value);
                } else
                {
                    filename = item.getName();
                    if (filename.contains("\\"))
                    {
                        filename = filename.substring(filename
                                .lastIndexOf("\\"));
                    }

                    ServletContext context = getServletContext();

                    // 上传的文件存放路径为...\\WebRoot\\upload\\filename
                    dir = context.getRealPath("upload");
                    // System.out.println(dir+"  , "+filename);
                    File file = new File(dir, filename);
                    file.createNewFile();

                    // 获得流，读取数据写入文件
                    InputStream in = item.getInputStream();
                    FileOutputStream fos = new FileOutputStream(file);

                    int len;
                    byte[] buffer = new byte[1024];
                    while ((len = in.read(buffer)) > 0)
                        fos.write(buffer, 0, len);

                    // 关闭资源文件操作
                    fos.close();
                    in.close();
                    // 删除临时文件
                    item.delete();
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            Connection conn = DBConnection.getConnection();
            String sql = "select hash from t_judge_ques where hash=?";
            PreparedStatement st = conn.prepareStatement(sql);
            List<JudgeQues> sqList = new ArrayList<JudgeQues>();
            Map<String, String> hash = new HashMap<String, String>();
            String answerRegex = "[TFtf]";
            // System.out.println(dir+"\\"+filename);
            Workbook book = Workbook
                    .getWorkbook(new File(dir + "\\" + filename));
            // 获得第一个工作表对象
            Sheet sheet = book.getSheet(0);
            // 得到单元格
            for (int i = 1; i < sheet.getRows(); i++)
            {
                String answer = sheet.getCell(0, i).getContents().trim()
                        .toUpperCase();
                ;
                String ques = sheet.getCell(1, i).getContents();
                if (answer == null || answer.length() != 1
                        || !answer.matches(answerRegex)) // 答案符合TF
                {
                    resp.sendRedirect("addBatchJudgeQues.jsp?error=answer&row="
                            + (i + 1));
                    return;
                }
                if (ques == null)
                {
                    resp.sendRedirect("addBatchJudgeQues.jsp?error=emptyQues&row="
                            + (i + 1));
                    return;
                } else
                {
                    if (ques == null || ques.equals(""))// 题干为空
                    {
                        resp.sendRedirect("addBatchJudgeQues.jsp?error=emptyQName&row="
                                + (i + 1));
                        return;
                    }
                    JudgeQues sq = new JudgeQues();
                    sq.setSubjectId(subjectId);
                    sq.setStatus(0);
                    sq.setqAnswer(answer);
                    sq.setqName(ques);
                    if (sq.getqName().length() > 300)
                    {
                        resp.sendRedirect("addBatchJudgeQues.jsp?error=longQName&row="
                                + (i + 1));
                        return;
                    }
                    sq.setHash(MD5Util.getMD5(sq.getSubjectId() + ques));
                    st.setString(1, sq.getHash());

                    String same = hash.get(sq.getHash());
                    if (same != null && !same.equals(""))
                    {
                        resp.sendRedirect("addBatchJudgeQues.jsp?error=same&row="
                                + (i + 1) + "&src=" + same);
                        return;
                    }

                    ResultSet rs = st.executeQuery();
                    if (rs != null && rs.next())
                    {
                        rs.close();
                        st.close();
                        conn.close();
                        resp.sendRedirect("addBatchJudgeQues.jsp?error=unique&row="
                                + (i + 1));
                        return;
                    } else
                    {
                        hash.put(sq.getHash(), (i + 1) + "");
                        sqList.add(sq);
                        rs.close();
                    }
                }

            }
            book.close();
            st.close();
            conn.close();

            HttpSession hs = req.getSession();
            hs.setAttribute("judgeBatch", sqList);

            JudgeQuesDAO sqd = new JudgeQuesDAOImpl();
            sqd.addJudgeQuesList(sqList);
            resp.sendRedirect("judge.jsp?batch=" + sqList.size()
                    + "&subjectId=" + subjectId);
            Cache.initJudgeQuesCache();
        }
        catch (Exception e)
        {
            try
            {
                resp.sendRedirect("addBatchJudgeQues.jsp?fatal=file");
            } catch (IOException e1)
            {
                e1.printStackTrace();
            }
            //e.printStackTrace();
        }

    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    {
        doPost(req, resp);
    }
}