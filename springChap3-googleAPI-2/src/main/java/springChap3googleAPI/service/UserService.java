package springChap3googleAPI.service;

import java.util.Optional;

import springChap3googleAPI.model.UserGoogle;

public interface UserService {
	 Optional<UserGoogle> findByUsername(String username);
	    void saveUser(UserGoogle user);
}