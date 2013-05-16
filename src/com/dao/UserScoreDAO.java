package com.dao;

import java.util.List;

import com.pojo.UserScore;

public interface UserScoreDAO extends GenericDAO
{
	public List<UserScore> queryAllScoreByUserId(String userId);
}
