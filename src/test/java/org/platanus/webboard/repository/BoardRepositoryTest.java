package org.platanus.webboard.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.platanus.webboard.domain.Board;
import org.platanus.webboard.domain.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BoardRepositoryTest {

	@Autowired
	private BoardRepository boardRepository;

	@Test
	void save() {
		// give
		Board board = Board.builder()
				.description("설명")
				.name("board1").build();

		//when
		board = boardRepository.save(board);

		//then
		Assertions.assertNotNull(board.getId());
	}
}
