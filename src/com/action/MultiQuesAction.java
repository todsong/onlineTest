package com.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.ExamDAO;
import com.dao.MultiQuesDAO;
import com.dao.PracQuesDAO;
import com.dao.UserDAO;
import com.dao.UserExamDAO;
import com.dao.impl.ExamDAOImpl;
import com.dao.impl.MultiQuesDAOImpl;
import com.dao.impl.PracQuesDAOImpl;
import com.dao.impl.UserDAOImpl;
import com.dao.impl.UserExamDAOImpl;
import com.pojo.MultiQues;
import com.pojo.User;
import com.resource.Cache;
import com.util.MD5Util;

public class MultiQuesAction extends HttpServlet
{
    private static final long serialVersionUID = -15804569970928793L;

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    {
        try
        {
            MultiQuesDAO sqd = new MultiQuesDAOImpl();
            MultiQues sq = new MultiQues();
            String del = req.getParameter("del");
            String up = req.getParameter("up");
            String addOne = req.getParameter("addOne");
            String addBatch = req.getParameter("addBatch");
            String id = req.getParameter("id");
            String addM = req.getParameter("addM");

            if (addOne != null)
            {
                resp.sendRedirect("addOneMultiQues.jsp");
            } else if (addBatch != null)
            {
                resp.sendRedirect("addBatchMultiQues.jsp");
            } else if (del != null)
            {
                ExamDAO ed = new ExamDAOImpl();
                int maxNeedInExam = ed.getMaxQuesNumByType("multi");
                int maxNumInRepo = sqd.getCountSum();
                UserExamDAO ued = new UserExamDAOImpl();
                PracQuesDAO qpd = new PracQuesDAOImpl();

                if (maxNumInRepo <= maxNeedInExam)
                {
                    resp.sendRedirect("multi.jsp?delTooFew");
                } else if (!ued.queryNoQuesId("multi", id)
                        || !qpd.queryNoQuesId("multi", id))
                {
                    resp.sendRedirect("multi.jsp?delUsed");
                } else
                {
                    sqd.deleteMultiQues(Integer.parseInt(id));
                    Cache.initMultiQuesCache();
                    resp.sendRedirect("multi.jsp");
                }
            } else if (addM != null)
            {
                String[] answers = req.getParameterValues("answer");
                StringBuilder answer = new StringBuilder("");
                for (int i = 0; i < answers.length; i++)
                {
                    answer.append(answers[i]);
                }
                sq.setqAnswer(answer.toString());

                StringBuilder sb = new StringBuilder("");
                sq.setqName(req.getParameter("ques"));
                sq.setSubjectId(Integer.parseInt(req.getParameter("subject")));
                sb.append(sq.getSubjectId() + sq.getqName());
                int optNum = 0;
                if (req.getParameter("tA") != null
                        && !req.getParameter("tA").equals(""))
                {
                    sq.setOptionA(req.getParameter("tA"));
                    optNum++;
                    sb.append(sq.getOptionA());
                }
                if (req.getParameter("tB") != null
                        && !req.getParameter("tB").equals(""))
                {
                    sq.setOptionB(req.getParameter("tB"));
                    optNum++;
                    sb.append(sq.getOptionB());
                }
                if (req.getParameter("tC") != null
                        && !req.getParameter("tC").equals(""))
                {
                    sq.setOptionC(req.getParameter("tC"));
                    optNum++;
                    sb.append(sq.getOptionC());
                }
                if (req.getParameter("tD") != null
                        && !req.getParameter("tD").equals(""))
                {
                    sq.setOptionD(req.getParameter("tD"));
                    optNum++;
                    sb.append(sq.getOptionD());
                }
                if (req.getParameter("tE") != null
                        && !req.getParameter("tE").equals(""))
                {
                    sq.setOptionE(req.getParameter("tE"));
                    optNum++;
                    sb.append(sq.getOptionE());
                }
                sq.setOptNum(optNum);
                // System.out.println(sb.toString());
                sq.setHash(MD5Util.getMD5(sb.toString()));
                if (sqd.checkUnique(sq.getHash()) != -1)
                {
                    resp.sendRedirect("multi.jsp?unique");
                    return;
                }
                sq.setStatus(0);

                sqd.addMultiQues(sq);
                Cache.initMultiQuesCache();
                resp.sendRedirect("multi.jsp");
            } else
            // update
            {
                String[] answers = req.getParameterValues("answer");
                StringBuilder answer = new StringBuilder("");
                for (int i = 0; i < answers.length; i++)
                {
                    answer.append(answers[i]);
                }
                sq.setSubjectId(Integer.parseInt(req.getParameter("subject")));
                sq.setqAnswer(answer.toString());
                sq.setqName(req.getParameter("ques"));
                int optNum = 0;
                StringBuilder sb = new StringBuilder("");
                sb.append(sq.getSubjectId()).append(sq.getqName());
                if (req.getParameter("tA") != null
                        && !req.getParameter("tA").equals(""))
                {
                    sq.setOptionA(req.getParameter("tA"));
                    optNum++;
                    sb.append(sq.getOptionA());
                }
                if (req.getParameter("tB") != null
                        && !req.getParameter("tB").equals(""))
                {
                    sq.setOptionB(req.getParameter("tB"));
                    optNum++;
                    sb.append(sq.getOptionB());
                }
                if (req.getParameter("tC") != null
                        && !req.getParameter("tC").equals(""))
                {
                    sq.setOptionC(req.getParameter("tC"));
                    optNum++;
                    sb.append(sq.getOptionC());
                }
                if (req.getParameter("tD") != null
                        && !req.getParameter("tD").equals(""))
                {
                    sq.setOptionD(req.getParameter("tD"));
                    optNum++;
                    sb.append(sq.getOptionD());
                }
                if (req.getParameter("tE") != null
                        && !req.getParameter("tE").equals(""))
                {
                    sq.setOptionE(req.getParameter("tE"));
                    optNum++;
                    sb.append(sq.getOptionE());
                }
                sq.setOptNum(optNum);
                // System.out.println(sb.toString());

                sq.setHash(MD5Util.getMD5(sb.toString()));
                int unique = sqd.checkUnique(sq.getHash());
                // System.out.println(unique);
                if (unique != -1 && unique != Integer.parseInt(id))
                {
                    resp.sendRedirect("multi.jsp?unique");
                    return;
                }
                int status = Integer.parseInt(req.getParameter("status"));
                sq.setStatus(status);

                sqd.updateMultiQues(Integer.parseInt(id), sq);
                Cache.initMultiQuesCache();
                resp.sendRedirect("multi.jsp");
            }
        } catch (IOException e)
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
