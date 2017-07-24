package repository;

import java.util.ArrayList;

import pojo.User;

public class UserRepo implements IUserRepo {
	private ArrayList<User> users;

	public UserRepo(ArrayList<User> users) {
		this.users = users;
	}

	public String saveUser(User user) {
		this.getUsers().add(user);
		int repoSize = users.size();
		User lastUserInRepo = this.getUsers().get(--repoSize);
		
		if(lastUserInRepo.getName().equals(user.getName())) {
			return "success";
		} else {
			return "failure";
		}		
	}

	public User findUser(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}
	
	
	
}
