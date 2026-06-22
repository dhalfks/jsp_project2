package repository;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.User;
import orm.DatabaseBuilder;

public class UserDAOImpl implements UserDAO {
	
	private static final Logger log = LoggerFactory.getLogger(UserDAOImpl.class);
	
	//sql session open
	private SqlSession sql;
	
	public UserDAOImpl() {
		new DatabaseBuilder();
		sql = DatabaseBuilder.getFactory().openSession();
	}

	@Override
	public int insert(User user) {
		// TODO Auto-generated method stub
		int isOk = sql.insert("userMapper.add", user);
		if(isOk>0) sql.commit();
		return isOk;
	}

	@Override
	public User getUser(User user) {
		// TODO Auto-generated method stub
		return sql.selectOne("userMapper.login", user);
	}

	@Override
	public int lastLoginUpdate(String id) {
		// TODO Auto-generated method stub
		int isOk = sql.update("userMapper.lastLogin", id);
		if(isOk > 0) sql.commit();
		return isOk;
	}

	@Override
	public int update(User user) {
		// TODO Auto-generated method stub
		int isOk = sql.update("userMapper.up", user);
		if(isOk > 0) sql.commit();
		return isOk;
	}

	@Override
	public int delete(String id) {
		// TODO Auto-generated method stub
		int isOk = sql.delete("userMapper.del", id);
		if(isOk > 0) sql.commit();
		return isOk;
	}
	

}
