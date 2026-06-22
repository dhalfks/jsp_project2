package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.Board;
import domain.PagingVO;
import handler.PagingHandler;
import service.BoardService;
import service.BoardServiceImpl;

@WebServlet("/brd/*")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// 로그객체 생성 => log.info("String") / log.info(">> {}", object)
	private static final Logger log = LoggerFactory.getLogger(BoardController.class);
	
	// servlet에서 request, response 객체를 보내는 역할을 하는 객체
	// RequestDispatcher 객체  servlet <-> jsp
	private RequestDispatcher rdp;
	
	// 어느 jsp로 보낼지 주소(목적지)를 저장하는 변수
	private String destPage;
	
	// service 연결  (인터페이스 연결) 
	private BoardService bsv; // 인터페이스  => 구현체 BoardServiceImpl() 

    public BoardController() {
        // 생성자
    	bsv = new BoardServiceImpl(); //구현체
    }


	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get, post 둘다 여기서 처리
		log.info(">>> BoardController service method in Test");
		
		// request, response 인코딩 설정
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		// response가 전송하는 컨텐츠 타입 설정  (html 페이지) => 
		// contentType : 'application/json'  => json 타입
		// contentType : 'text/html'  => html 파일 (동기식 컨트롤러 일때)
		response.setContentType("text/html; charset=UTF-8");
		
		String uri = request.getRequestURI();
		log.info(">>> uri >> {}", uri);  // uri >> /brd/register
		
		// register 요청이 왔을 때 register.jsp 파일을 전송
		String path = uri.substring(uri.lastIndexOf("/")+1);
		log.info(">>> path >> {}", path);
		
		switch(path) {
		case "register": 
			destPage = "/board/register.jsp";
			break;
		
		case "insert":
			// title, writer, contents => DB로 전송
			try {
				String title = request.getParameter("title");
				String writer = request.getParameter("writer");
				String contents = request.getParameter("contents");
				
				// DB로 등록할 객체
				Board board = new Board();
				board.setTitle(title);
				board.setWriter(writer);
				board.setContents(contents);
				
				// boardService 해당 객체 전달
				int isOk = bsv.insert(board);
				
				// DB에서 insert 되고나면 1 / 0 값이 전달
				log.info(">>> insert {}", (isOk > 0) ? "성공" : "실패");
				
				// 처리 후 보내야 하는 페이지 => list 페이지로 전송 => list case 전송
				destPage="list";
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			break;
			
		case "list" :
			try {
				// DB에서 전체 리스트를 요청 (페이징 없이 처리)
				// list.jsp 페이지로 전송 
				// List<Board> list = bsv.getList();
				PagingVO pagingVO = new PagingVO(); // pageNo = 1 / qty = 10
				
				List<Board> list = bsv.getList(pagingVO);	
				
				// totalCount => DB에서 계산해오기
				// select count(bno) from board;
				int totalCount = bsv.getTotal();
				
				
				PagingHandler ph = new PagingHandler(pagingVO, totalCount);
				log.info(">>> ph >>{}", ph);
				
				// 페이징을 포함한 값으로 요청
				request.setAttribute("list", list);
				request.setAttribute("ph", ph);
				destPage="/board/list.jsp";
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			break;
			
		case "detail": case "modify":
			try {
				int bno = Integer.parseInt(request.getParameter("bno"));
				
				Board board = bsv.getDetail(bno);
				request.setAttribute("board", board);
				destPage="/board/"+path+".jsp";
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			break;
			
		case "update":
			try {
				int bno = Integer.parseInt(request.getParameter("bno"));
				String title = request.getParameter("title");
				String contents = request.getParameter("contents");
				
				Board board = new Board();
				board.setBno(bno);
				board.setTitle(title);
				board.setContents(contents);
				
				log.info(">>> update board {}", board);
				
				int isOk = bsv.update(board);
				log.info(">>> update {}", (isOk > 0) ? "성공" : "실패");
				
				// 보낼 페이지 주소 => detail case로 보내고 싶음.
				destPage = "detail?bno="+bno;
				
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			break;
			
		case "delete":
			try {
				int bno = Integer.parseInt(request.getParameter("bno"));
				
				// db 에 삭제 요청
				bsv.delete(bno);
				
				destPage = "list"; // 내부 케이스를 돌아야 함.
				
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			break;
			
	
			
			
			
			
			
			
		}
		
		// 처리가 완료된 응답객체를 보내기
		// RequestDispatcher  응답객체를 전달하는 역할  /  destPage값을 전달
		rdp = request.getRequestDispatcher(destPage);
		// 전송
		rdp.forward(request, response);
		
		
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// jsp에서 method="get"인 처리를 하는 영역
		service(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// jsp에서 method="post"인 처리를 하는 영역
		service(request, response);
	}

}
