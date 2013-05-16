package com.dao;

import com.pojo.PracQues;

public interface PracQuesDAO extends GenericDAO
{
    public int addPracQues(PracQues pq);
    public int deletePracQuesByPracId(int pracId);
    public int updatePracQues(PracQues pq);
    public PracQues getPracQuesById(int pracId, int caseId);
    public boolean queryNoQuesId(String type, String id);
}
