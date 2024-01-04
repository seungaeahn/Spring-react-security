package springChap3googleAPI.service;

import springChap3googleAPI.model.UserGoogle;

public interface UserService {
	 UserGoogle findByUsername(String username);
	    void saveUser(UserGoogle user);
}