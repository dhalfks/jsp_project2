package handler;

import domain.PagingVO;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class PagingHandler {
	// DB 제외, 화면에 필요한 정보를 계산
	// DB에서 필요한 객체 VO => domain
	// 데이터 전달용 DTO => domain
	// 그외 화면에서 필요한 처리는 handler 패키지로 저장
	
	// < 1 2 3 4 5 6 7 8 9 10 >
	private int startPage; // 현재 페이지네이션의 시작번호 1  11  21  31 ...
	private int endPage; // 현재 페이지네이션의 끝번호 10  20  30  40...
	private boolean prev; // 이전 페이지의 존재여부
	private boolean next; // 다음 페이지의 존재여부
	private int realEndPage; // 정말 마지막 페이지
	
	// 컨트롤러에서 받아오기
	private PagingVO pagingVO; 
	private int totalCount; // 전체 게시글 수 DB에서 검색해오기
	
	public PagingHandler(PagingVO pagingVO, int totalCount) {
		this.pagingVO = pagingVO;
		this.totalCount = totalCount;
		
		// pageNo : 1~10 => 10
		// pageNo : 11~20 => 20
		// pageNo / 10 => 0.1  0.2  0.3  => 올림 1 *10 => 10
		
		// 정수 / 정수 => 정수
		this.endPage = (int)Math.ceil((double)pagingVO.getPageNo() / 10)*10;
		this.startPage = this.endPage - 9;
		
		
		// 정수 / 정수 = 정수  => 남는값을 버리지 않기 위해 ceil
		// 소수점 값이 남으면 무조건 한페이지 더 필요
		this.realEndPage = (int)Math.ceil((double)totalCount / pagingVO.getQty());
		
		// 실제 리얼 마지막페이지가 13 이라면  endPage = 13로 변경되어야 함. 
		// realEndPage = 13  / endPage = 20
		if(this.realEndPage < this.endPage) {
			this.endPage = this.realEndPage;
		}
		
		// 이전 다음 유무
		this.prev = this.startPage > 1;  // startPage  1  11  21
		this.next = this.endPage < this.realEndPage; // 정말 마지막 페이지보다 endPage가 작을 때
		
	}
	
	
	
	
	
	
	
	
	
	
	

}
