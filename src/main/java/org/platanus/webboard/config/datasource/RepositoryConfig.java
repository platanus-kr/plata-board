package org.platanus.webboard.config.datasource;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.domain.*;
import org.platanus.webboard.domain.jdbctemplate.*;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * JDBCTemplate 를 Repository 에 주입하기 위한 설정
 */
//@Configuration
@RequiredArgsConstructor
public class RepositoryConfig {
    public final JdbcTemplate jdbcTemplate;

    //    @Bean
    public BoardRepository boardRepository() {
        //return new JdbcTemplateBoardRepository(jdbcTemplate);
        return null;
    }

    //    @Bean
    public UserRepository userRepository() {
        //return new JdbcTemplateUserRepository(jdbcTemplate);
        return null;
    }

    //    @Bean
    public ArticleRepository articleRepository() {
        //return new JdbcTemplateArticleRepository(jdbcTemplate);
        return null;
    }

    //    @Bean
    public CommentRepository commentRepository() {
        //return new JdbcTemplateCommentRepository(jdbcTemplate);
        return null;
    }

    //    @Bean
    public ArticleRecommendRepository articleRecommendRepository() {
        //return new JdbcTemplateArticleRecommendRepository(jdbcTemplate);
        return null;
    }

}
