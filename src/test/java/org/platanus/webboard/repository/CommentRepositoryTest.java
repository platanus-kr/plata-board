package org.platanus.webboard.repository;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.platanus.webboard.domain.Article;
import org.platanus.webboard.domain.ArticleRepository;
import org.platanus.webboard.domain.Board;
import org.platanus.webboard.domain.BoardRepository;
import org.platanus.webboard.domain.Comment;
import org.platanus.webboard.domain.CommentRepository;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.domain.UserRepository;
import org.platanus.webboard.domain.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CommentRepositoryTest {

	public static final String TEST_USER1 = "test20";
	public static final String TEST_BOARD1 = "board20";

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Test
	@Order(1)
	public void save_최초등록() {
		//give
		User user = User.builder()
				.username(TEST_USER1)
				.password("password")
				.nickname("testNickname")
				.email("test1@gmail.com")
				.role(UserRole.ROLE_USER)
				.deleted(false)
				.build();
		userRepository.save(user);

		Board board = Board.builder()
				.name(TEST_BOARD1)
				.description("설명")
				.build();
		boardRepository.save(board);

		Board boardByName = boardRepository.findByName(TEST_BOARD1).get();
		User userByUsername = userRepository.findByUsername(TEST_USER1).get();

		Article article = Article.builder()
				.authorId(userByUsername.getId())
				.boardId(boardByName.getId())
				.title("제목제목")
				.content("내용내용")
				.recommend(0L)
				.viewCount(0L)
				.createdDate(LocalDateTime.now())
				.modifiedDate(LocalDateTime.now())
				.build();
		article = articleRepository.save(article);

		Comment comment = Comment.builder()
				.articleId(article.getId())
				.authorId(user.getId())
				.content("댓글내용")
				.createdDate(LocalDateTime.now())
				.modifiedDate(LocalDateTime.now())
				.deleted(false)
				.build();

		//when
		comment = commentRepository.save(comment);

		//then
		log.info(comment.toString());
		Assertions.assertNotNull(comment.getId());
	}

	@Test
	@Order(2)
	public void save_코멘트수정() {
		//give
		User user = User.builder()
				.username(TEST_USER1)
				.password("password")
				.nickname("testNickname")
				.email("test1@gmail.com")
				.role(UserRole.ROLE_USER)
				.deleted(false)
				.build();
		userRepository.save(user);

		Board board = Board.builder()
				.name(TEST_BOARD1)
				.description("설명")
				.build();
		boardRepository.save(board);

		Board boardByName = boardRepository.findByName(TEST_BOARD1).get();
		User userByUsername = userRepository.findByUsername(TEST_USER1).get();

		Article article = Article.builder()
				.authorId(userByUsername.getId())
				.boardId(boardByName.getId())
				.title("제목제목")
				.content("내용내용")
				.recommend(0L)
				.viewCount(0L)
				.createdDate(LocalDateTime.now())
				.modifiedDate(LocalDateTime.now())
				.build();
		article = articleRepository.save(article);

		Comment comment = Comment.builder()
				.articleId(article.getId())
				.authorId(user.getId())
				.content("댓글내용")
				.createdDate(LocalDateTime.now())
				.modifiedDate(LocalDateTime.now())
				.deleted(false)
				.build();
		comment = commentRepository.save(comment);

		//when
		Comment updateComment = Comment.builder()
				.id(comment.getId())
				.content("수정내용")
				.modifiedDate(LocalDateTime.now())
				.build();
		commentRepository.update(updateComment);
		Comment commentById = commentRepository.findById(comment.getId()).get();

		//then
		log.info(commentById.toString());
		Assertions.assertEquals("수정내용", commentById.getContent());
	}
}
