package service;

import java.util.List;

import domain.Comment;

public interface CommentService {

	int insert(Comment comment);

	List<Comment> getList(int bno);

	int update(Comment comment);

	int delete(int cno);

}
