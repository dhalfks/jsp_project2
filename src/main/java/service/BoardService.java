package service;

import java.util.List;

import domain.Board;
import domain.PagingVO;

public interface BoardService {

	int insert(Board board);

	//List<Board> getList();

	Board getDetail(int bno);

	int update(Board board);

	void delete(int bno);

	List<Board> getList(PagingVO pagingVO);

	int getTotal();

}
