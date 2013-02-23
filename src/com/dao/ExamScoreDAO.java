package com.dao;

import java.util.List;

import com.pojo.ExamScore;

public interface ExamScoreDAO
{
	public List<ExamScore> queryExamScoreByExamId(int examId);
}
