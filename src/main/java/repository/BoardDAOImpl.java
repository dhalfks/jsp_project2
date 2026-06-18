package repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.Board;
import orm.DatabaseBuilder;

public class BoardDAOImpl implements BoardDAO {
	
	private static final Logger log = LoggerFactory.getLogger(BoardDAOImpl.class);
	private SqlSession sql;
	
	public BoardDAOImpl() {
		new DatabaseBuilder();
		sql = DatabaseBuilder.getFactory().openSession();
	}

	@Override
	public int insert(Board board) {
		// TODO Auto-generated method stub
		log.info(">>> insert daoImple in~!!!");
		// sql.method 종류 => select, insert, update, delete
		// sql.method(namespace.id, object);
		
		// insert, update, delete => return 1 or 0
		// 반드시 commit을 해주어야 반영됨. => transactionManager 자체 운영
		int isOk = sql.insert("boardMapper.add", board);
		if(isOk > 0) sql.commit();
		
		return isOk;
	}

	@Override
	public List<Board> getList() {
		// selectList, selectOne
		return sql.selectList("boardMapper.list");
	}

	@Override
	public Board getDetail(int bno) {
		// TODO Auto-generated method stub
		return sql.selectOne("boardMapper.detail", bno);
	}

	@Override
	public int update(Board board) {
		// TODO Auto-generated method stub
		int isOk = sql.update("boardMapper.update", board);
		if(isOk > 0) sql.commit();
		
		return isOk;
	}

}
