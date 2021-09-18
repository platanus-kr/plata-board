package org.platanus.webboard.config;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.domain.*;
import org.platanus.webboard.domain.MyBatis.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    public final JdbcTemplate jdbcTemplate;
    public final MyBatisBoardMapper boardMapper;
    public final MyBatisUserMapper userMapper;
    public final MyBatisArticleMapper articleMapper;
    public final MyBatisCommentMapper commentMapper;
    public final MyBatisArticleRecommendMapper articleRecommendMapper;

    @Bean
    public BoardRepository boardRepository() {
//        return new JdbcTemplateBoardRepository(jdbcTemplate);
        return boardMapper;
    }

    @Bean
    public UserRepository userRepository() {
//        return new JdbcTemplateUserRepository(jdbcTemplate);
        return userMapper;
    }

    @Bean
    public ArticleRepository articleRepository() {
//        return new JdbcTemplateArticleRepository(jdbcTemplate);
        return articleMapper;
    }

    @Bean
    public CommentRepository commentRepository() {
//        return new JdbcTemplateCommentRepository(jdbcTemplate);
        return commentMapper;
    }

    @Bean
    public ArticleRecommendRepository articleRecommendRepository() {
//        return new JdbcTemplateArticleRecommendRepository(jdbcTemplate);
        return articleRecommendMapper;
    }

}
