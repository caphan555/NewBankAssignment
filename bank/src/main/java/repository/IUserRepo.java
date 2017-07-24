package repository;

import java.util.ArrayList;

import pojo.User;

public interface IUserRepo {
	public String saveUser(User user);
	public User findUser(String name);
	public ArrayList<User> getUsers();
	public void setUsers(ArrayList<User> users);
}
