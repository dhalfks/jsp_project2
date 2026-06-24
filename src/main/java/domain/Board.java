package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Board {
	private int bno;
	private String title;
	private String writer;
	private String contents;
	private String regdate;
	private String moddate;
	private String imagefile;
	
}
