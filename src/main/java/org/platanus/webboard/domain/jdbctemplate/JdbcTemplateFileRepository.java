package org.platanus.webboard.domain.jdbctemplate;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.domain.File;
import org.platanus.webboard.domain.FileRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class JdbcTemplateFileRepository implements FileRepository {
    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @PostConstruct
    public void init() {
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("FILES").usingGeneratedKeyColumns("id");
    }

    @Override
    public File upload(File file) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_id", file.getUserId());
        parameters.put("original_filename", file.getOriginalFilename());
        parameters.put("original_extension", file.getOriginalExtension());
        parameters.put("management_filename", file.getManagementFilename());
        parameters.put("store_path_prefix", file.getStorePathPrefix());
        parameters.put("size", file.getSize());
        parameters.put("create_date", file.getCreateDate());
        parameters.put("update_date", file.getUpdateDate());
        parameters.put("deleted", file.getDeleted());
        parameters.put("expire_date", file.getExpireDate());
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        file.setId(key.longValue());
        return file;
    }

    @Override
    public int delete(File file) {
        return 0;
    }

    @Override
    public int deleteByUserId(long userId) {
        return 0;
    }

    @Override
    public int updateDeleteFlag(File file) {
        return 0;
    }

    @Override
    public int findById(long id) {
        return 0;
    }

    @Override
    public int findByManagementFilename(String mgntFilename) {
        return 0;
    }

    @Override
    public int findByExpireFromSourceDatetimeToDestinationDatetime(LocalDateTime srcDatetime, LocalDateTime destDatetime) {
        return 0;
    }

    @Override
    public int findAll() {
        return 0;
    }

    public RowMapper<File> fileRowMapper() {
        return (rs, rowNum) -> File.builder()
                .id(rs.getLong("id"))
                .userId(rs.getLong("user_id"))
                .originalFilename(rs.getString("original_filename"))
                .originalExtension(rs.getString("original_extension"))
                .managementFilename(rs.getString("managementFilename"))
                .storePathPrefix(rs.getString("storePathPrefix"))
                .size(rs.getLong("size"))
                .createDate(rs.getTimestamp("created_date").toLocalDateTime())
                .updateDate(rs.getTimestamp("update_date").toLocalDateTime())
                .deleted(rs.getBoolean("deleted"))
                .expireDate(rs.getTimestamp("expire_date").toLocalDateTime())
                .build();
    }
}
