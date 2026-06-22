package domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class PagingVO {
	
	// 페이지+search 처리를 위해 DB에서 필요한 객체를 만들어주는 VO
	// select * from board order by bno desc limit 번지,개수;
	// limit 0, 10 (한 화면에 출력할 게시글 목록 번지/개수)
	// 번지 => 화면에서 클릭하는 페이징 숫자에 따라 달라짐. => 계산해서 리턴
	
	private int pageNo; // 화면에서 클릭하는 페이징 숫자
	private int qty; // 한 화면에 출력할 게시글 수 (10개)
	
	public PagingVO() {
		// 파라미터 없이 첫 리스트를 호출
		this.pageNo = 1;
		this.qty = 10;
	}
	
	public PagingVO(int pageNo, int qty) {
		this.pageNo = pageNo;
		this.qty = qty;
	}
	
	// getter => 번지를 계산하는 메서드
	// 어차피 mapper 에서 호출하는 값은 getter 멤버변수가 없이도 멤버변수처럼 사용가능
	public int getPageIndex() {
		//선택한 페이지번호에 따라 변경
		// 1=>0 / 2=>10 / 3=>20 /...
		return (this.pageNo - 1) * this.qty;
		
	}
	
	
	
	
	
	
	
	
	
}
