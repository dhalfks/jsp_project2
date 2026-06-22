package test;

import java.util.Random;

import org.junit.Test;

import domain.Board;
import repository.BoardDAOImpl;

public class InsertTest {
	
	@Test
	public void insertDummyData() {
		
		BoardDAOImpl boardDAOImpl = new BoardDAOImpl();
		Random random = new Random();
		
		for(int i=0; i<300; i++) {
			Board board = new Board();
			board.setTitle("test"+i);
			board.setWriter("user"+random.nextInt(50));
			board.setContents("test content!! \n 테스트 입니다.");
			
			boardDAOImpl.insert(board);
		}
		
	}

}
