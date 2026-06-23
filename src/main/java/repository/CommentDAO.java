package repository;

import java.util.List;

import domain.Comment;

public interface CommentDAO {

	int insert(Comment comment);

	List<Comment> getList(int bno);

	int update(Comment comment);

	int delete(int cno);

}
