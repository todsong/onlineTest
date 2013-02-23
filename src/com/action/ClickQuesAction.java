package com.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ClickQuesAction extends HttpServlet
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
        String para = req.getQueryString();
        String id = para.split("&")[1].substring(3);
        String type = para.split("&")[0].substring(5);
        
        Boolean nextButton=false;
        Boolean backButton=false;
        HttpSession hs = req.getSession();
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

        
        try
        {
            StringBuffer url = new StringBuffer(type+"Ques.jsp?id="+id);
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
