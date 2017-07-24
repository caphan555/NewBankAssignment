package repository;

import java.util.ArrayList;
import java.util.List;

import pojo.User;

public interface IUserRepo {
	public String saveUser(User user);
	public User findUser(String name);
	public List<User> getUsers();
	public void setUsers(List<User> users);
}
