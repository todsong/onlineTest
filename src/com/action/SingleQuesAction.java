package com.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.ExamDAO;
import com.dao.PracQuesDAO;
import com.dao.SingleQuesDAO;
import com.dao.UserDAO;
import com.dao.UserExamDAO;
import com.dao.impl.ExamDAOImpl;
import com.dao.impl.PracQuesDAOImpl;
import com.dao.impl.SingleQuesDAOImpl;
import com.dao.impl.UserDAOImpl;
import com.dao.impl.UserExamDAOImpl;
import com.pojo.SingleQues;
import com.pojo.User;
import com.resource.Cache;
import com.util.MD5Util;

public class SingleQuesAction extends HttpServlet
{
    private static final long serialVersionUID = -15809939970928793L;

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    {
        try
        {
            SingleQuesDAO sqd = new SingleQuesDAOImpl();
            SingleQues sq = new SingleQues();
            String del = req.getParameter("del");
            String up = req.getParameter("up");
            String addOne = req.getParameter("addOne");
            String addBatch = req.getParameter("addBatch");
            String id = req.getParameter("id");
            String addS = req.getParameter("addS");

            if (addOne != null)
            {
                resp.sendRedirect("addOneSingleQues.jsp");
            } else if (addBatch != null)
            {
                resp.sendRedirect("addBatchSingleQues.jsp");
            } else if (del != null)
            {
                ExamDAO ed = new ExamDAOImpl();
                int maxNeedInExam = ed.getMaxQuesNumByType("single");
                int maxNumInRepo = sqd.getCountSum();
                UserExamDAO ued = new UserExamDAOImpl();
                PracQuesDAO qpd = new PracQuesDAOImpl();

                if (maxNumInRepo <= maxNeedInExam)
                {
                    resp.sendRedirect("single.jsp?delTooFew");
                } else if (!ued.queryNoQuesId("single", id)
                        || !qpd.queryNoQuesId("single", id))
                {
                    resp.sendRedirect("single.jsp?delUsed");
                } else
                {
                    sqd.deleteSingleQues(Integer.parseInt(id));
                    Cache.initSingleQuesCache();
                    resp.sendRedirect("single.jsp");
                }
            } else if (addS != null)
            {
                StringBuilder sb = new StringBuilder("");
                sq.setqAnswer(req.getParameter("answerOpt"));
                sq.setqName(req.getParameter("ques"));
                sq.setSubjectId(Integer.parseInt(req.getParameter("subject")));
                sb.append(sq.getSubjectId() + sq.getqName());
                // System.out.println(sq.getqName());
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
                sq.setHash(MD5Util.getMD5(sb.toString()));
                if (sqd.checkUnique(sq.getHash()) != -1)
                {
                    resp.sendRedirect("single.jsp?unique");
                    return;
                }
                sq.setStatus(0);
                sqd.addSingleQues(sq);
                Cache.initSingleQuesCache();
                resp.sendRedirect("single.jsp");
            } else
            // update
            {
                StringBuilder sb = new StringBuilder("");
                sq.setqAnswer(req.getParameter("answerOpt"));
                sq.setqName(req.getParameter("ques"));
                sq.setSubjectId(Integer.parseInt(req.getParameter("subject")));
                sb.append(sq.getSubjectId() + sq.getqName());
                // System.out.println(sq.getqName());
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
                sq.setHash(MD5Util.getMD5(sb.toString()));
                int unique = sqd.checkUnique(sq.getHash());
                if (unique != -1 && unique != Integer.parseInt(id))
                {
                    resp.sendRedirect("single.jsp?unique");
                    return;
                }
                int status = Integer.parseInt(req.getParameter("status"));
                sq.setStatus(status);
                sqd.updateSingleQues(Integer.parseInt(id), sq);
                Cache.initSingleQuesCache();
                resp.sendRedirect("single.jsp");
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    {
        doGet(req, resp);
    }
}
