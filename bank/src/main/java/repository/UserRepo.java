package repository;

import java.util.ArrayList;
import java.util.List;

import pojo.User;

public class UserRepo implements IUserRepo {
	private List<User> users;

	public UserRepo(List<User> users) {
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
		for(User u: this.getUsers()) {
			if(u.getName().equals(name)) {
				return u;
			}
		}
		return null;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	
	
}
