package repository;

import domain.User;

public interface UserDAO {

	int insert(User user);

	User getUser(User user);

}
