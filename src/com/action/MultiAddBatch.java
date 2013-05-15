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
import com.datatype.QuesFile;
import com.pojo.MultiQues;
import com.resource.Cache;
import com.resource.DBConnection;
import com.util.MD5Util;

public class MultiAddBatch extends HttpServlet
{
    private static final long serialVersionUID = -6142120896371324521L;

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    {
        final ServletContext context = getServletContext();
        // 上传的文件存放路径为...\\WebRoot\\upload\\filename
        String dir = context.getRealPath("upload");
        QuesFile quesFile = AddBatchBase.uploadFile(req,dir);
        //quesFile.setContextDir(dir);
        quesFile.setQuesType("multi");
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