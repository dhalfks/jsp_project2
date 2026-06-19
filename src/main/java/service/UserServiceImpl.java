package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.User;
import repository.UserDAO;
import repository.UserDAOImpl;

public class UserServiceImpl implements UserService {
	// log 객체
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	// controller <-> serviceImpl <-> DAOImpl
	private UserDAO udao; // interface 생성
	
	public UserServiceImpl() {
		udao = new UserDAOImpl(); // 구현체 생성
	}

	@Override
	public int insert(User user) {
		// TODO Auto-generated method stub
		return udao.insert(user);
	}

	@Override
	public User getUser(User user) {
		// TODO Auto-generated method stub
		return udao.getUser(user);
	}

	@Override
	public int lastLoginUpdate(String id) {
		// TODO Auto-generated method stub
		return udao.lastLoginUpdate(id);
	}

}
