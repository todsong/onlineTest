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

import javax.servlet.http.HttpServletRequest;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.dao.JudgeQuesDAO;
import com.dao.MultiQuesDAO;
import com.dao.SingleQuesDAO;
import com.dao.impl.JudgeQuesDAOImpl;
import com.dao.impl.MultiQuesDAOImpl;
import com.dao.impl.SingleQuesDAOImpl;
import com.datatype.QuesFile;
import com.exception.AddBatchException;
import com.pojo.JudgeQues;
import com.pojo.MultiQues;
import com.pojo.SelectionQues;
import com.pojo.SingleQues;
import com.resource.Cache;
import com.resource.DBConnection;
import com.resource.DBResManager;
import com.util.MD5Util;

public class AddBatchBase
{
    public static QuesFile uploadFile(HttpServletRequest req, String dir)
    {
        final DiskFileItemFactory factory = new DiskFileItemFactory();
        final ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> fileItems = null;
        try
        {
            fileItems = upload.parseRequest(req);
        } catch (FileUploadException e)
        {
            e.printStackTrace();
        }

        if (fileItems == null || fileItems.size() != 2
                || fileItems.get(0) == null || fileItems.get(1) == null)
        {
            return null;
        }

        // get(1)是用于获取subjectId
        final FileItem subjectFileItem = fileItems.get(1);
        String subjectValue = subjectFileItem.getString();
        // 转换下字符集编码
        try
        {
            subjectValue = new String(subjectValue.getBytes("iso-8859-1"),
                    "utf-8");
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        QuesFile uploadQuesFile = new QuesFile();
        uploadQuesFile.setSubjectId(Integer.parseInt(subjectValue));

        // get(0)是用于获取上传的excel文件
        final FileItem fileItem = fileItems.get(0);
        String filename = fileItem.getName();
        if (filename.contains("\\"))
        {
            filename = filename.substring(filename.lastIndexOf('\\'));
        }

        File file = new File(dir, filename);
        uploadQuesFile.setFile(file);

        InputStream inStrm = null;
        FileOutputStream fos = null;
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
        } finally
        {
            // 关闭资源文件操作
            if (fos != null)
            {
                try
                {
                    fos.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (inStrm != null)
            {
                try
                {
                    inStrm.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            // 删除临时文件
            fileItem.delete();
        }
        return uploadQuesFile;
    }

    public static int checkQuesUniqueFromDB(List quesList, String quesType)
    {
        final Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        ResultSet rst = null;
        int res = 0;

        try
        {
            String tableName = "t_" + quesType + "_ques";
            String className = "com.pojo."+quesType.substring(0, 1).toUpperCase()
                    + quesType.substring(1) + "Ques";
            Class quesClazz = Class.forName(className);
            pst = conn.prepareStatement("select hash from " + tableName
                    + " where hash=?");
            String hash = null;
            for (int i = 0; i < quesList.size(); i++)
            {
                Object ques = quesList.get(i);
                if (quesType.equals("judge"))
                {
                    hash = ((JudgeQues) ques).getHash();
                } else
                {
                    hash = ((SelectionQues) ques).getHash();
                }
                pst.setString(1, hash);
                rst = pst.executeQuery();
                if (rst != null && rst.next())
                {
                    res = i + 2;
                    throw new AddBatchException();
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (AddBatchException e)
        {
        } finally
        {
            DBResManager.closeResultSet(rst);
            DBResManager.closePreStatement(pst);
            DBResManager.closeConnection(conn);
        }
        return res;
    }

    public static JudgeQues checkOneJudgeQues(String answer, String ques,
            int subjectId, Map hash) throws AddBatchException
    {
        if (answer == null || answer.length() != 1 || !answer.matches("[TFtf]")) // 答案符合TF
        {
            throw new AddBatchException("answer");
        } else
        {
            if (ques == null || ques.equals("")) // 题单为空
            {
                throw new AddBatchException("emptyQues");
            } else if (ques.length() > 300)
            {
                throw new AddBatchException("longQName");
            }
        }
        JudgeQues jq = new JudgeQues();
        jq.setSubjectId(subjectId);
        jq.setStatus(0);
        jq.setqAnswer(answer);
        jq.setqName(ques);
        jq.calculateHash();

        String same = (String) hash.get(jq.getHash());
        if (same != null && !same.equals(""))
        {
            AddBatchException exp = new AddBatchException("same");
            exp.setSrcRow(same);
            throw exp;
        }
        return jq;
    }

    public static SelectionQues checkOneSelectionQues(String type, String answer,
            String ques, int subjectId, Map hash) throws AddBatchException
    {
        String regex = null;
        if(type.equals("judge"))
        {
            regex = "[A-Ea-e]";
        }
        else
        {
            regex = "[A-Ea-e]+";
        }
        boolean answerError = false;
        if (answer == null || !answer.matches(regex)) // 答案符合A-E
        {
            answerError = true;
        }
        for (int k = 0; k < answer.length() - 1; k++)// 多选答案必须单调有序
        {
            if (answer.charAt(k) >= answer.charAt(k + 1))
            {
                answerError = true;
            }
        }
        if(answerError==true)
        {
            throw new AddBatchException("answer");
        }

        if (ques == null || ques.equals("")) // 题单为空
        {
            throw new AddBatchException("emptyQues");
        }

        String[] qName = ques.split("[\r\n]+"); // 题干以第一个换行分割
        if (qName[0] == null || qName[0].equals(""))// 题干为空
        {
            throw new AddBatchException("emptyQName");
        } else if (qName[0].length() > 300)
        {
            throw new AddBatchException("longQName");
        } else if (qName.length == 1) // 不能区分题目和题干
        {
            throw new AddBatchException("oneLine");
        }

        char option = 'A';
        String optionSrc = ques;
        String[] options = new String[5];
        int optNum = 0;
        for (int i = 0; i < 5; i++)
        {
            String[] splited = optionSrc.split("\\s*"+option + "((、)|(．))");
            if (splited.length != 2)
            {
                if (option == 'E')
                {
                    optNum = 4;
                    options[3] = optionSrc;
                    break;
                } else
                {
                    throw new AddBatchException(option + "");
                }
            }
            if (option > 'A')
            {
                options[i - 1] = splited[0];
            }
            if (option == 'E')
            {
                options[i] = splited[1];
                optNum = 5;
            }
            optionSrc = splited[1];
            option = (char) (option+1);
        }
        for (int i = 0; i < optNum; i++)
        {
            if (options[i] == null || options[i].equals(""))
            {
                throw new AddBatchException("empty" + (char) ('A' + i));
            } else if (options[i].length() > 300)
            {
                throw new AddBatchException("long" + (char) ('A' + i));
            }
        }
        SelectionQues selectionQues = new SelectionQues();
        selectionQues.setqAnswer(answer);
        selectionQues.setqName(qName[0]);
        selectionQues.setSubjectId(subjectId);
        selectionQues.setStatus(0);
        selectionQues.setOptNum(optNum);
        selectionQues.setOptionA(options[0]);
        selectionQues.setOptionB(options[1]);
        selectionQues.setOptionC(options[2]);
        selectionQues.setOptionD(options[3]);
        if (optNum == 5)
        {
            selectionQues.setOptionE(options[4]);
        }
        selectionQues.calculateHash();
        return selectionQues;
    }

    public static String getUrl(QuesFile uploadQuesFile)
    {
        String type = uploadQuesFile.getQuesType();
        String typeUpcase = type.substring(0, 1).toUpperCase()
                + type.substring(1);
        String url = null;
        int subjectId = uploadQuesFile.getSubjectId();
        Workbook book = null;
        Class clazz = getClazz(type);
        try
        {
            final List qList = new ArrayList();
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
                    if (type.equals("judge"))
                    {
                        JudgeQues jq = checkOneJudgeQues(answer, ques,
                                subjectId, hash);
                        hash.put(jq.getHash(), (i + 1) + "");
                        qList.add(jq);
                    } else
                    {
                        SelectionQues sq = checkOneSelectionQues(type, answer, ques,
                                subjectId, hash);
                        hash.put(sq.getHash(), (i + 1) + "");
                        qList.add(sq);
                    }
                } catch (AddBatchException e)
                {
                    e.setErrorRow(i + 1);
                    throw e;
                }
            }

            int checkUnique = checkQuesUniqueFromDB(qList, type);
            if (checkUnique != 0)
            {
                throw new AddBatchException("unique", checkUnique);
            }

            if (type.equals("judge"))
            {

                JudgeQuesDAO jqd = new JudgeQuesDAOImpl();
                jqd.addJudgeQuesList(qList);
            } else if (type.equals("single"))
            {
                SingleQuesDAO sqd = new SingleQuesDAOImpl();
                sqd.addSingleQuesList(qList);
            } else
            {
                MultiQuesDAO sqd = new MultiQuesDAOImpl();
                sqd.addMultiQuesList(qList);
            }
            Cache.initJudgeQuesCache();

            url = type + ".jsp?batch=" + qList.size() + "&subjectId="
                    + subjectId;
        } catch (AddBatchException e)
        {

            url = "addBatch" + typeUpcase + "Ques.jsp?error=" + e.getErrorType()
                    + "&row=" + e.getErrorRow();
            if (e.getSrcRow() != null)
            {
                url += "&src=" + e.getSrcRow();
            }
        } catch (Exception e)
        {
            url = "addBatch"+typeUpcase+"Ques.jsp?fatal=file";
        } finally
        {
            if (book != null)
            {
                book.close();
            }
        }
        return url;
    }

    public static Class getClazz(String type)
    {
        Class clazz = null;
        if (type.equals("judge"))
        {
            clazz = JudgeQues.class;
        } else if (type.equals("single"))
        {
            clazz = SingleQues.class;
        } else
        {
            clazz = MultiQues.class;
        }
        return clazz;
    }
}
