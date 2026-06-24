package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.Board;
import domain.PagingVO;
import handler.FileRemoveHandler;
import handler.PagingHandler;
import net.coobird.thumbnailator.Thumbnails;
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
				// 첨부파일이 있을 경우 수정 코드
				// image 저장 + DB 저장
				// 파일 업로드 시 사용할 물리적인 경로를 설정
				String savePath = getServletContext().getRealPath("/_fileUpload");
				
				// 파일 객체 생성
				// 파일이름은 동적으로 들어옴 / 경로는 이미 정해져 있음. => savePath
				File fileDir = new File(savePath);
				log.info(">> fileDir >> {}", fileDir.toString());
				
				DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
				// 실제 저장할 경로(저장할 file 객체)
				fileItemFactory.setRepository(fileDir);
				// 파일 저장시 사용할 메모리 공간 (임시공간)
				fileItemFactory.setSizeThreshold(1024*1024*3);
				
				Board board = new Board();  // DB 저장 객체
				
				// form 태그에서 넘어온 multipart/form-data 객체를 
				// 우리가 다루기 쉽게 변환해주는 클래스
				ServletFileUpload fileupload = new ServletFileUpload(fileItemFactory);
				
				List<FileItem> fileItem = fileupload.parseRequest(request);
				
				log.info(">> list file item >> {}", fileItem);
				
				for(FileItem item : fileItem) {
					log.info(">> item >> {}", item);
					// title, writer, contents => string
					// imagefile => image (file)
					// fieldName => form name=""
					switch(item.getFieldName()) {
					case "title": 
						// byte 형태로 풀어져서 전송 => 다시 텍스트로 조합 => utf-8로 인코딩 해서 조립
						String title = item.getString("utf-8");
						board.setTitle(title);
						break;
					case "writer": 
						board.setWriter(item.getString("utf-8"));
						break;
					case "contents": 
						board.setContents(item.getString("utf-8"));
						break;
					case "imagefile": 
						// 파일의 용량이 잘 들어왔는지 체크
						// 원래는 화면에서 (js) 체크 하고 들어옴
						if(item.getSize() > 0) {
							// 이름 추출
							String fileName = item.getName();
							// 파일 이름은 내부에서 중복확인/구분을 쉽게 하기 위해 고유번호를 붙여서 관리
							// UUID / 시스템의 현재 시간을 이용하여 구분
							fileName = System.currentTimeMillis()+"_"+fileName;
							
							log.info(">> fileName >>{}", fileName);
							
							// 파일 객체 생성
							// 경로 + 파일 구분자 + 파일이름.확장자
							// 파일구분자 (경로기호) => 운영체제마다 다름 / \
							// File.separator : 파일 경로 기호
							File uploadFile = new File(fileDir+File.separator+fileName);
							log.info(">> uploadFile >>{}", uploadFile.toString());
							
							// 저장
							try {
								item.write(uploadFile); // 저장
								board.setImagefile(fileName); // 저장 경로는 동일
								
								// 썸네일 작업
								// list 페이지에서 트래픽 과다 사용 방지 (연결 시간 지연 방지)
								// 작은 이미지로 조정 => 이미지만 가능
								Thumbnails.of(uploadFile)
									.size(75, 75)
									.toFile(new File(fileDir+File.separator+"th_"+fileName));
								
							} catch (Exception e) {
								// TODO: handle exception
								log.info(">> file upload on disk error");
								e.printStackTrace();
							}
							
						}  // if end
						break;
					} // item 내부 for 안 switch 끝
				} // 내부 for end
				
				
				// 첨부파일이 없을 경우 코드
//				String title = request.getParameter("title");
//				String writer = request.getParameter("writer");
//				String contents = request.getParameter("contents");
//				
//				// DB로 등록할 객체
//				Board board = new Board();
//				board.setTitle(title);
//				board.setWriter(writer);
//				board.setContents(contents);
				
				
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
				
				if(request.getParameter("pageNo") != null) {
					int pageNo = Integer.parseInt(request.getParameter("pageNo"));
					int qty = Integer.parseInt(request.getParameter("qty"));
					String type = request.getParameter("type");
					String keyword = request.getParameter("keyword");
					
					pagingVO = new PagingVO(pageNo, qty, type, keyword);
				}
				
				
				List<Board> list = bsv.getList(pagingVO);	
				
				// totalCount => DB에서 계산해오기
				// select count(bno) from board;
				int totalCount = bsv.getTotal(pagingVO);
				
				
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
				// file 있는 경우
				String savePath = getServletContext().getRealPath("/_fileUpload");
				File fileDir = new File(savePath);
				int size = 1024*1024*3;
				
				DiskFileItemFactory fileItemFactory = new DiskFileItemFactory(size, fileDir);
				
				Board board = new Board();
				
				ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
				
				List<FileItem> itemList = fileUpload.parseRequest(request);
				
				String old_file = null; // 기존 이미지 파일이 있다면 여기다 저장
				
				for(FileItem item : itemList) {
					// item fieldName => input name = ""
					switch(item.getFieldName()) {
					case "bno": 
						board.setBno(Integer.parseInt(item.getString("utf-8")));
						break;
					case "title": 
						board.setTitle(item.getString("utf-8"));
						break;
					case "contents":
						board.setContents(item.getString("utf-8"));
						break;
					case "imagefile": 
						old_file = item.getString("utf-8");
						break;
					case "newfile": 
						// 새로 추가되는 파일이 있다면...
						if(item.getSize() > 0) {
							if(old_file != null) {
								// 기존 파일이 존재했다면 => 기존파일 삭제
								// fileRemoveHandler를 통해서 파일 삭제 작업 진행
								FileRemoveHandler fh = new FileRemoveHandler();
								boolean isDel = fh.deleteFile(savePath, old_file);								
							}
							// 새파일 등록 작업
							String fileName = System.currentTimeMillis()+"_"+item.getName();
							// 경로 + 구분자 + 파일이름
							File uploadFile = new File(fileDir+File.separator+fileName);
							//저장
							try {
								
								item.write(uploadFile);
								board.setImagefile(fileName); // 바뀐 파일 이름
								
								Thumbnails.of(uploadFile)
									.size(75, 75)
									.toFile(new File(fileDir+File.separator+"th_"+fileName));
								
							} catch (Exception e) {
								// TODO: handle exception
								log.info("file upload update error");
								e.printStackTrace();
							}
							
						}else {
							// 새로 추가되는 파일이 없으면... 기존 파일값을 그대로 넣기
							board.setImagefile(old_file);
						}
						
						break;
					}
				}
				
				// file 없는 경우
//				int bno = Integer.parseInt(request.getParameter("bno"));
//				String title = request.getParameter("title");
//				String contents = request.getParameter("contents");
//				
//				Board board = new Board();
//				board.setBno(bno);
//				board.setTitle(title);
//				board.setContents(contents);
				
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
