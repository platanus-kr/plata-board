package org.platanus.webboard.domain;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.platanus.webboard.domain.MyBatis.MyBatisBoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@MybatisTest
//@ImportAutoConfiguration(MyBatisConfig.class)
public class MyBatisBoardMapperTest {
    @Autowired
    private MyBatisBoardMapper boardMapper;
    private Board board;

    @BeforeEach
    public void beforeEach() {
        board = new Board();
    }

    @Test
    public void save() {
        String boardName = "BOARD 01";
        board.setName(boardName);
        board.setDescription("description");
        Board savedBoard = boardMapper.save(board);
        assertEquals(savedBoard.getName(), boardName);
    }

}
