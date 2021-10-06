package org.platanus.webboard.config;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.domain.*;
import org.platanus.webboard.domain.JdbcTemplate.*;
import org.platanus.webboard.domain.MyBatis.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    public final JdbcTemplate jdbcTemplate;
    public final BoardMapper boardMapper;
    public final UserMapper userMapper;
    public final ArticleMapper articleMapper;
    public final CommentMapper commentMapper;
    public final ArticleRecommendMapper articleRecommendMapper;

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
