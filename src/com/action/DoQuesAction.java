package com.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DoQuesAction extends HttpServlet
{
    private static final long serialVersionUID = 176354166115021807L;
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    {
        try
        {
            req.setCharacterEncoding("utf-8");
        }
        catch (UnsupportedEncodingException e1)
        {
            e1.printStackTrace();
        }
        String id = req.getParameter("id");
        String type = req.getParameter("type");
        String answer = null;
        if(type.equals("multi"))
        {
            String[] answers = req.getParameterValues("answer");
            StringBuilder sb = new StringBuilder("");
            if(answers!=null)
            {
                for(int i=0; i<answers.length; i++)
                {
                    sb.append(answers[i]);
                }
            }
            answer = new String(sb.toString());
        }
        else
        {
            answer = req.getParameter("answer");
        }
        HttpSession hs = req.getSession();
        hs.setAttribute(type+id, answer);

        Boolean nextButton=false;
        Boolean backButton=false;
        
        int judgeSum = (Integer) hs.getAttribute("judgeSum");
        int singleSum = (Integer) hs.getAttribute("singleSum");
        int multiSum = (Integer) hs.getAttribute("multiSum");
        
        int jsLine = judgeSum;
        int smLine = judgeSum+singleSum;
        
        int totleSum = judgeSum+singleSum+multiSum;
        int globalId = Integer.parseInt(id);
        if(type.equals("single"))
        {
            globalId += jsLine;
        }
        else if(type.equals("multi"))
        {
            globalId += smLine;
        }
        
        
        String resType = null;
        int resId = 0;
        if(req.getParameter("next")!=null)//点击下一题跳转而来
        {
            globalId++;
            //System.out.println(globalId);
            backButton=true;
            if(globalId==totleSum-1)
            {
                nextButton = false;
            }
            else
            {   
                nextButton = true;
            }
            if(globalId<jsLine)
            {
                resType="judge";
                resId = globalId;
            }
            if(globalId>=jsLine&&globalId<smLine)
            {
                resType="single";
                resId = globalId - jsLine;
            }
            if(globalId>=smLine)
            {
                resType="multi";
                resId = globalId - smLine;
            }
        }
        else if(req.getParameter("handIn")!=null)//点击保存
        {
            resType=type;
            resId = Integer.parseInt(id);
            if(globalId==totleSum-1)
            {
                nextButton = false;
            }
            else
            {   
                nextButton = true;
            }
            if(globalId==0)
                backButton=false;
            else
                backButton=true;
        }
        else//点击上一题
        {
            globalId--;
            nextButton=true;
            if(globalId!=0)
            {
                backButton=true;
            }
            else
            {
                backButton=false;
            }
            if(globalId<jsLine)
            {
                resType="judge";
                resId = globalId;
            }
            if(globalId>=jsLine&&globalId<smLine)
            {
                resType="single";
                resId = globalId - jsLine;
            }
            if(globalId>=smLine)
            {
                resType="multi";
                resId = globalId - smLine;
            }
        }
        try
        {
            StringBuffer url = new StringBuffer(resType+"Ques.jsp?id="+resId);
            if(nextButton)
                url.append("&next");
            if(backButton)
                url.append("&back");
            resp.sendRedirect(url.toString());
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    
        
    }
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    {
        doGet(req, resp);
    }    
}
