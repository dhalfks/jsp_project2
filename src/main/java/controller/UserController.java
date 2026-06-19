package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.User;
import service.UserService;
import service.UserServiceImpl;


@WebServlet("/user/*")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//log객체
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	// 요청에 대한 응답 데이터를 jsp 형태로 만들어 전송하는 역할
	private RequestDispatcher rdp;
	// 어떤 jsp를 전송할지 경로
	private String destPage;
	
	// jsp <-> controller <-> service
	private UserService usv; // interface 로 생성
	
    public UserController() {
        usv = new UserServiceImpl();  // 구현 클래스 생성
    }


	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// doGet / doPost 오는 메서드를 여기서(service) 처리
		
		// 한글깨짐 방지 encoding 설정
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		// 동기식에서 보내는 응답객체의 contentType = "text/html; charset=UTF-8"
		response.setContentType("text/html; charset=UTF-8");
		
		// uri 경로
		String uri = request.getRequestURI();
		// path
		String path = uri.substring(uri.lastIndexOf("/")+1);
		
		log.info(">>> user path {}", path);
		
		switch(path) {
		case "register" :
			// 데이터 없는 페이지의 오픈
			destPage = "/member/register.jsp";
			break;
			
		case "insert":
			try {
				String id = request.getParameter("id");
				String pw = request.getParameter("pw");
				String email = request.getParameter("email");
				String phone = request.getParameter("phone");
				
				// user 객체로 생성 
				User user = new User();
				user.setId(id);
				user.setPw(pw);
				user.setEmail(email);
				user.setPhone(phone);
				
				// userService => 전송
				int isOk = usv.insert(user);
				
				log.info(">> user insert >> {}", (isOk > 0)? "성공": "실패");
				
				destPage = "/index.jsp";
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			break;
			
		case "login":
			// 로그인 페이지 열기
			destPage = "/member/login.jsp";
			break;
			
		case "join":
			try {
				// 실제 로그인이 이루어지는 케이스
				String id = request.getParameter("id");
				String pw = request.getParameter("pw");
				
				// 로그인 => 내 정보를 session 객체에 저장하는 것
				// user 테이블에서 id와 pw 일치하는 User 객체를 리턴
				User loginUser = usv.getUser(new User(id,pw));
				
				log.info(">> loginUser >> {}", loginUser);
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			break;
		}
		
		rdp = request.getRequestDispatcher(destPage);
		rdp.forward(request, response);
		
		
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		service(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		service(request, response);
	}

}
