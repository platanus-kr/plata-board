package org.platanus.webboard.config.datasource;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.domain.*;
import org.platanus.webboard.domain.jdbctemplate.*;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * JDBCTemplate 를 Repository 에 주입하기 위한 설정
 */
//@Configuration
@RequiredArgsConstructor
public class RepositoryConfig {
    public final JdbcTemplate jdbcTemplate;
//    public final BoardMapper boardMapper;
//    public final UserMapper userMapper;
//    public final ArticleMapper articleMapper;
//    public final CommentMapper commentMapper;
//    public final ArticleRecommendMapper articleRecommendMapper;

    @Bean
    public BoardRepository boardRepository() {
        return new JdbcTemplateBoardRepository(jdbcTemplate);
//        return boardMapper;
    }

    @Bean
    public UserRepository userRepository() {
        return new JdbcTemplateUserRepository(jdbcTemplate);
//        return userMapper;
    }

    @Bean
    public ArticleRepository articleRepository() {
        return new JdbcTemplateArticleRepository(jdbcTemplate);
//        return articleMapper;
    }

    @Bean
    public CommentRepository commentRepository() {
        return new JdbcTemplateCommentRepository(jdbcTemplate);
//        return commentMapper;
    }

    @Bean
    public ArticleRecommendRepository articleRecommendRepository() {
        return new JdbcTemplateArticleRecommendRepository(jdbcTemplate);
//        return articleRecommendMapper;
    }

}
