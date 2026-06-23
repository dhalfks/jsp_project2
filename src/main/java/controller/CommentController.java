package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.Comment;
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
		
		String path = uri.substring(uri.lastIndexOf("/")+1);
		
		switch(path) {
		case "post":
			try {
				// 동기방식 => request.getParameter("name"); // 파라미터 객체를 읽어들임.
				// 비동기 방식 => 파일 입출력 처럼 읽고(Reader) 쓰기(Writer)
				// request.getReader() / response.getWriter()
				
				BufferedReader br = request.getReader();

				// '{"bno": "317", "writer": "111", "contents": "1111"}'
				// string -> 객체 형태로 parser -> JSONObject
				// JSONObject => key:value => Comment 객체로 생성
				
				JSONParser parser = new JSONParser();
				// string -> key:value 형태로 변환
				JSONObject jsonobj = (JSONObject)parser.parse(br);
				log.info(">>> jsonobj>>{}", jsonobj);
				
				int bno = Integer.parseInt(jsonobj.get("bno").toString());
				String writer = jsonobj.get("writer").toString();
				String contents = jsonobj.get("contents").toString();
				
				Comment comment = new Comment();
				comment.setBno(bno);
				comment.setWriter(writer);
				comment.setContents(contents);
				
				int isOk = csv.insert(comment);
				
				log.info(">> comment insert >>{}", (isOk>0)? "성공":"실패");
				
				// 결과 보내기
				PrintWriter pw = response.getWriter();
				pw.print(isOk);
				
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			break;
		}
		
		
		
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
