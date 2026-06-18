package service;

import java.util.List;

import domain.Board;

public interface BoardService {

	int insert(Board board);

	List<Board> getList();

	Board getDetail(int bno);

	int update(Board board);

}
