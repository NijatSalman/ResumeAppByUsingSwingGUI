package com.company.dao.inter;

import java.util.List;

import com.company.entity.User;

public interface UserDaoInter {
	
	public List<User> getAll();
	
	public User getById(int id);
	
	public boolean addUser(User u);
	
	public boolean updateUser(User u);
	
	public boolean removeUser(int id);
}
