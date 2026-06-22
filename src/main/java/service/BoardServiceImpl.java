package service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.Board;
import domain.PagingVO;
import repository.BoardDAO;
import repository.BoardDAOImpl;

public class BoardServiceImpl implements BoardService {
	
	private static final Logger log = LoggerFactory.getLogger(BoardServiceImpl.class);
	
	private BoardDAO bdao;
	
	public BoardServiceImpl() {
		bdao = new BoardDAOImpl();
	}

	@Override
	public int insert(Board board) {
		// TODO Auto-generated method stub
		log.info(">>> insert serviceImpl in!!");
		return bdao.insert(board);
	}

//	@Override
//	public List<Board> getList() {
//		// TODO Auto-generated method stub
//		return bdao.getList();
//	}

	@Override
	public Board getDetail(int bno) {
		// TODO Auto-generated method stub
		return bdao.getDetail(bno);
	}

	@Override
	public int update(Board board) {
		// TODO Auto-generated method stub
		return bdao.update(board);
	}

	@Override
	public void delete(int bno) {
		// TODO Auto-generated method stub
		bdao.delete(bno);		
	}

	@Override
	public List<Board> getList(PagingVO pagingVO) {
		// TODO Auto-generated method stub
		return bdao.getList(pagingVO);
	}

	@Override
	public int getTotal(PagingVO pagingVO) {
		// TODO Auto-generated method stub
		return bdao.getTotal(pagingVO);
	}

}
