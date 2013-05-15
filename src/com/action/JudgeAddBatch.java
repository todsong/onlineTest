package com.action;

import java.io.IOException;
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
import jxl.Sheet;
import jxl.Workbook;
import com.dao.JudgeQuesDAO;
import com.dao.impl.JudgeQuesDAOImpl;
import com.datatype.QuesFile;
import com.exception.AddBatchException;
import com.pojo.JudgeQues;
import com.resource.Cache;
import com.resource.CloseDBRes;
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
        final ServletContext context = getServletContext();
        // 上传的文件存放路径为...\\WebRoot\\upload\\filename
        String dir = context.getRealPath("upload");
        QuesFile quesFile = AddBatchBase.uploadFile(req,dir);
        //quesFile.setContextDir(dir);
        quesFile.setQuesType("judge");
        String url = AddBatchBase.getUrl(quesFile);
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
