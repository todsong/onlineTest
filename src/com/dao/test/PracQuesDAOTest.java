package com.dao.test;

import java.util.Iterator;
import java.util.List;

import com.dao.PracQuesDAO;
import com.dao.PracQuesDAO;
import com.dao.impl.PracQuesDAOImpl;
import com.dao.impl.PracQuesDAOImpl;
import com.pojo.PracQues;
import com.pojo.PracQues;

public class PracQuesDAOTest 
{
	private PracQues pq;
	private PracQuesDAO pqd = new PracQuesDAOImpl();
	public void addPracQuesTest(PracQues pq)
	{
	    pq = new PracQues();
		pq.setPracId(36);
		pq.setCaseId(1);
		pq.setJudgeIdList("1|12|31");
		pq.setSingleIdList("1");
		pq.setMultiIdList("");
		pqd.addPracQues(pq);
	}
	public void queryPracQuesById()
	{
		pq = pqd.getPracQuesById(36, 0);
		System.out.println(pq.getPracId()+" "+pq.getCaseId()+" "+pq.getJudgeIdList());
	}
	public void updatePracQuesById()
	{
		pq = new PracQues();
        pq.setPracId(36);
        pq.setCaseId(0);
        pq.setJudgeIdList("1|12|999");
        pq.setSingleIdList("1");
        pq.setMultiIdList("");		
		pqd.updatePracQues(pq);
	}
	
	public void deletePracQuesById()
	{
		pqd.deletePracQuesByPracId(36);
		
	}
	public static void main(String[] args)
	{
		PracQuesDAOTest udt = new PracQuesDAOTest();
		//udt.addPracQuesTest(udt.pq);
		//udt.queryPracQuesByName("张三");
		//udt.updatePracQuesById();
		udt.deletePracQuesById();
		//udt.queryPracQuesById();
        
	
	}
}
