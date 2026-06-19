package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
	// id, pw, email, phone, regdate, moddate, lastlogin
	private String id;
	private String pw;
	private String email;
	private String phone;
	private String regdate;
	private String moddate;
	private String lastlogin;
	
	public User(String id, String pw) {
		this.id = id;
		this.pw = pw;
	}
	
	

}
