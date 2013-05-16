package com.dao;

import java.util.List;

import com.pojo.Exam;

public interface ExamDAO extends GenericDAO {
	public int addExam(Exam exam);
	public int deleteExam(int id);
	public int updateExam(int id, Exam exam);
	public Exam queryExamById(int id);
    public List<Exam> queryExamByTime(String date, String examType);
    public List<Exam> queryExamBySubject(int subjectId, String examType);
    public List<Exam> getRecentEaxm(String examType, int days);
    public List<Exam> getAllEaxm(String examType);
    public int getMaxQuesNumByType(String type);
}
