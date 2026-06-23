package repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.Comment;
import orm.DatabaseBuilder;

public class CommentDAOImpl implements CommentDAO {
	
	private static final Logger log = LoggerFactory.getLogger(CommentDAOImpl.class);
	
	private SqlSession sql;
	
	public CommentDAOImpl() {
		new DatabaseBuilder();
		sql = DatabaseBuilder.getFactory().openSession();
	}

	@Override
	public int insert(Comment comment) {
		// TODO Auto-generated method stub
		int isOk = sql.insert("commentMapper.post", comment);
		if(isOk>0) sql.commit();
		return isOk;
	}

	@Override
	public List<Comment> getList(int bno) {
		// TODO Auto-generated method stub
		return sql.selectList("commentMapper.list", bno);
	}

}
 