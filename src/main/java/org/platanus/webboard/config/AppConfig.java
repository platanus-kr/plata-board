package org.platanus.webboard.config;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.domain.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    public final JdbcTemplate jdbcTemplate;

    @Bean
    public BoardRepository boardRepository() {
        return new JdbcTemplateBoardRepository(jdbcTemplate);
    }

    @Bean
    public UserRepository userRepository() {
        return new JdbcTemplateUserRepository(jdbcTemplate);
    }

    @Bean
    public ArticleRepository articleRepository() {
        return new JdbcTemplateArticleRepository(jdbcTemplate);
    }

    @Bean
    public CommentRepository commentRepository() {
        return new JdbcTemplateCommentRepository(jdbcTemplate);
    }

    @Bean
    public ArticleRecommendRepository articleRecommendRepository() {
        return new JdbcTemplateArticleRecommendRepository(jdbcTemplate);
    }

}
