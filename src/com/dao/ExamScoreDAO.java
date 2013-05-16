package com.dao;

import java.util.List;

import com.pojo.ExamScore;

public interface ExamScoreDAO extends GenericDAO
{
	public List<ExamScore> queryExamScoreByExamId(int examId);
}
