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

import com.dao.MultiQuesDAO;
import com.dao.SubjectDAO;
import com.dao.impl.MultiQuesDAOImpl;
import com.dao.impl.SubjectDAOImpl;
import com.pojo.MultiQues;
import com.resource.Cache;
import com.resource.DBConnection;
import com.util.MD5Util;

public class MultiAddBatch extends HttpServlet
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

        // A． A、
        try
        {
            Connection conn = DBConnection.getConnection();
            String sql = "select hash from t_multi_ques where hash=?";
            PreparedStatement st = conn.prepareStatement(sql);
            List<MultiQues> sqList = new ArrayList<MultiQues>();
            Map<String, String> hash = new HashMap<String, String>();
            String answerRegex = "[A-Ea-e]+";
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
                if (answer == null)
                {
                    resp.sendRedirect("addBatchMultiQues.jsp?error=answer&row="
                            + (i + 1));
                    return;
                } else if (!answer.matches(answerRegex))
                {
                    resp.sendRedirect("addBatchMultiQues.jsp?error=answer&row="
                            + (i + 1));
                    return;
                } else
                {
                    for (int k = 0; k < answer.length() - 1; k++)// 多选答案必须单调有序
                    {
                        if (answer.charAt(k) >= answer.charAt(k + 1))
                        {
                            resp.sendRedirect("addBatchMultiQues.jsp?error=answer&row="
                                    + (i + 1));
                            return;
                        }
                    }
                }
                if (ques == null)
                {
                    resp.sendRedirect("addBatchMultiQues.jsp?error=emptyQues&row="
                            + (i + 1));
                    return;
                } else
                {
                    String[] qName = ques.split("[\r\n]+"); // 题干以第一个换行分割
                    if (qName[0] == null || qName[0].equals(""))// 题干为空
                    {
                        resp.sendRedirect("addBatchMultiQues.jsp?error=emptyQName&row="
                                + (i + 1));
                        return;
                    } else if (qName.length == 1)
                    {
                        resp.sendRedirect("addBatchMultiQues.jsp?error=oneLine&row="
                                + (i + 1));
                        return;
                    }
                    String[] optionSrc = ques.split("A((、)|(．))");
                    if (optionSrc.length != 2)
                    {
                        resp.sendRedirect("addBatchMultiQues.jsp?error=A&row="
                                + (i + 1));
                        return;
                    }

                    String[] optionA = optionSrc[1].split("B((、)|(．))");
                    if (optionA.length != 2)
                    {
                        resp.sendRedirect("addBatchMultiQues.jsp?error=B&row="
                                + (i + 1));
                        return;
                    }
                    String[] optionB = optionA[1].split("C((、)|(．))");
                    if (optionB.length != 2)
                    {
                        resp.sendRedirect("addBatchMultiQues.jsp?error=C&row="
                                + (i + 1));
                        return;
                    }
                    String[] optionC = optionB[1].split("D((、)|(．))");
                    if (optionC.length != 2)
                    {
                        resp.sendRedirect("addBatchMultiQues.jsp?error=D&row="
                                + (i + 1));
                        return;
                    }
                    String[] optionD = optionC[1].split("E((、)|(．))");
                    String optionE = null;
                    if (optionD.length >= 2)
                        optionE = optionD[1];

                    if (optionA[0] == null || optionA[0].equals(""))
                    {
                        resp.sendRedirect("addBatchMultiQues.jsp?error=emptyA&row="
                                + (i + 1));
                        return;
                    }
                    if (optionB[0] == null || optionB[0].equals(""))
                    {
                        resp.sendRedirect("addBatchMultiQues.jsp?error=emptyB&row="
                                + (i + 1));
                        return;
                    }
                    if (optionC[0] == null || optionC[0].equals(""))
                    {
                        resp.sendRedirect("addBatchMultiQues.jsp?error=emptyC&row="
                                + (i + 1));
                        return;
                    }
                    if (optionD[0] == null || optionD[0].equals(""))
                    {
                        resp.sendRedirect("addBatchMultiQues.jsp?error=emptyD&row="
                                + (i + 1));
                        return;
                    }
                    if (optionE != null && optionE.equals(""))
                    {
                        resp.sendRedirect("addBatchMultiQues.jsp?error=emptyE&row="
                                + (i + 1));
                        return;
                    } else if (answer.equals("E"))
                    {
                        resp.sendRedirect("addBatchMultiQues.jsp?error=answer&row="
                                + (i + 1));
                        return;
                    }
                    MultiQues sq = new MultiQues();
                    sq.setSubjectId(subjectId);
                    sq.setStatus(0);
                    sq.setqAnswer(answer);
                    sq.setqName(qName[0]);
                    if (sq.getqName().length() > 300)
                    {
                        resp.sendRedirect("addBatchMultiQues.jsp?error=longQName&row="
                                + (i + 1));
                        return;
                    }
                    sq.setOptionA(optionA[0]);
                    if (sq.getOptionA().length() > 300)
                    {
                        resp.sendRedirect("addBatchMultiQues.jsp?error=longA&row="
                                + (i + 1));
                        return;
                    }
                    sq.setOptionB(optionB[0]);
                    if (sq.getOptionB().length() > 300)
                    {
                        resp.sendRedirect("addBatchMultiQues.jsp?error=longB&row="
                                + (i + 1));
                        return;
                    }
                    sq.setOptionC(optionC[0]);
                    if (sq.getOptionC().length() > 300)
                    {
                        resp.sendRedirect("addBatchMultiQues.jsp?error=longC&row="
                                + (i + 1));
                        return;
                    }
                    sq.setOptionD(optionD[0]);
                    if (sq.getOptionD().length() > 300)
                    {
                        resp.sendRedirect("addBatchMultiQues.jsp?error=longD&row="
                                + (i + 1));
                        return;
                    }

                    StringBuilder sb = new StringBuilder(sq.getSubjectId()
                            + sq.getqName() + sq.getOptionA() + sq.getOptionB()
                            + sq.getOptionC() + sq.getOptionD());
                    if (optionE != null)
                    {
                        if (optionE.length() > 300)
                        {
                            resp.sendRedirect("addBatchMultiQues.jsp?error=longE&row="
                                    + (i + 1));
                            return;
                        }
                        sq.setOptionE(optionE);
                        sq.setOptNum(5);
                        sb.append(sq.getOptionE());
                    } else
                    {
                        sq.setOptNum(4);
                        if (answer.contains("E") || answer.contains("e"))
                        {
                            resp.sendRedirect("addBatchMultiQues.jsp?error=answer&row="
                                    + (i + 1));
                            return;
                        }
                    }

                    sq.setHash(MD5Util.getMD5(sb.toString()));
                    st.setString(1, sq.getHash());

                    String same = hash.get(sq.getHash());
                    if (same != null && !same.equals(""))
                    {
                        resp.sendRedirect("addBatchMultiQues.jsp?error=same&row="
                                + (i + 1) + "&src=" + same);
                        return;
                    }

                    ResultSet rs = st.executeQuery();
                    if (rs != null && rs.next())
                    {
                        rs.close();
                        st.close();
                        conn.close();
                        resp.sendRedirect("addBatchMultiQues.jsp?error=unique&row="
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
            hs.setAttribute("multiBatch", sqList);

            MultiQuesDAO sqd = new MultiQuesDAOImpl();
            sqd.addMultiQuesList(sqList);
            resp.sendRedirect("multi.jsp?batch=" + sqList.size()
                    + "&subjectId=" + subjectId);
            Cache.initMultiQuesCache();
        } catch (Exception e)
        {
            try
            {
                resp.sendRedirect("addBatchMultiQues.jsp?fatal=file");
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