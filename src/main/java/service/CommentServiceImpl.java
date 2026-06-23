package service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.Comment;
import repository.CommentDAO;
import repository.CommentDAOImpl;

public class CommentServiceImpl implements CommentService {
	private static final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);
	
	// DAO 연결
	private CommentDAO cdao;
	
	public CommentServiceImpl() {
		cdao = new CommentDAOImpl();
	}

	@Override
	public int insert(Comment comment) {
		// TODO Auto-generated method stub
		return cdao.insert(comment);
	}

	@Override
	public List<Comment> getList(int bno) {
		// TODO Auto-generated method stub
		return cdao.getList(bno);
	}

}
