package com.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 考试中, 处理点击试题列表的操作, 跳转到所点击的页面.
 * 
 * @author song
 */
public class ClickQuesAction extends HttpServlet
{
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 4664198340938178555L;

    /**
     * doGet.
     * 
     * @param req
     *            请求
     * @param resp
     *            响应
     */
    public final void doGet(final HttpServletRequest req,
            final HttpServletResponse resp)
    {
        final HttpSession httpSession = req.getSession();
        final String quesIdStr = req.getParameter("quesId");
        int quesId = 0;
        if (quesIdStr == null)
        {
            quesId = (Integer) httpSession.getAttribute("quesId");
        } else
        {
            quesId = Integer.parseInt(quesIdStr);
        }
        final String quesType = req.getParameter("quesType");

        final int judgeSum = (Integer) httpSession.getAttribute("judgeSum");
        final int singleSum = (Integer) httpSession
                .getAttribute("singleSum");
        final int multiSum = (Integer) httpSession.getAttribute("multiSum");

        final int jsLine = judgeSum;
        final int smLine = judgeSum + singleSum;

        final int totleSum = judgeSum + singleSum + multiSum;
        int globalId = quesId;
        if ("single".equals(quesType))
        {
            globalId += jsLine;
        } else if ("multi".equals(quesType))
        {
            globalId += smLine;
        }

        if (globalId == totleSum - 1)
        {
            httpSession.setAttribute("next", false);
        } else
        {
            httpSession.setAttribute("next", true);
        }
        if (globalId == 0)
        {
            httpSession.setAttribute("back", false);
        } else
        {
            httpSession.setAttribute("back", true);
        }
        // StringBuffer url = new StringBuffer(type + "Ques.jsp?id=" + id);
        final String url = quesType + "Ques.jsp";
        httpSession.setAttribute("quesId", quesId);
        // resp.sendRedirect(url.toString());
        try
        {
            req.getRequestDispatcher(url).forward(req, resp);
            return;
        } catch (ServletException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * doPost.
     * 
     * @param req
     *            请求
     * @param resp
     *            响应
     */
    public final void doPost(final HttpServletRequest req,
            final HttpServletResponse resp)
    {
        doGet(req, resp);
    }
}
