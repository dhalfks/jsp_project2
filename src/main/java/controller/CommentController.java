package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.CommentService;
import service.CommentServiceImpl;


@WebServlet("/cmt/*")
public class CommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(CommentController.class);
    
	// 비동기 방식의 요청을 하는 컨트롤러
	// 데이터를 요청한 곳으로 결과를 보냄 (페이지를 보내지 않음)
	// 객체형태(JSON), 또는 텍스트(String) 형태로 보냄
	// RequestDispatcher / destPage / setContentType => 필요없음.
	
	// commentService => interface
	private CommentService csv; 

    public CommentController() {
        csv = new CommentServiceImpl();
    }


	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 모든 처리는 서비스에서 처리
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		String uri = request.getRequestURI();
		log.info(">>> comment uri >> {}",uri);
		
		
		
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get으로 들어오는 요청을 처리 => Service를 호출하여 처리
		service(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Post으로 들어오는 요청을 처리 => Service를 호출하여 처리
		service(request, response);
	}

}
