package org.platanus.webboard.domain.jdbctemplate;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.domain.File;
import org.platanus.webboard.domain.FileRepository;
import org.platanus.webboard.domain.jdbctemplate.constant.QueryConstant;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//@Repository
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
    public int delete(long id) {
        return 0;
    }

    @Override
    public int deleteByUserId(long userId) {
        return 0;
    }

    @Override
    public int updateDeleteFlag(File file) {
        return jdbcTemplate.update(QueryConstant.FILE_UPDATE_DELETE_FLAG_BY_ID, file.getDeleted(), file.getId());
    }

    @Override
    public Optional<File> findById(long id) {
        List<File> query = jdbcTemplate.query(QueryConstant.FILE_FIND_BY_ID, fileRowMapper(), id);
        return query.stream().findAny();
    }

    @Override
    public Optional<File> findByManagementFilename(String managementFilename) {
        List<File> query = jdbcTemplate.query(QueryConstant.FILE_FIND_BY_MANAGEMENT_FILENAME, fileRowMapper(), managementFilename);
        return query.stream().findAny();
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
                .managementFilename(rs.getString("management_filename"))
                .storePathPrefix(rs.getString("store_path_prefix"))
                .size(rs.getLong("size"))
                .createDate(rs.getTimestamp("create_date").toLocalDateTime())
                .updateDate(rs.getTimestamp("update_date").toLocalDateTime())
                .deleted(rs.getBoolean("deleted"))
                .expireDate(rs.getTimestamp("expire_date").toLocalDateTime())
                .build();
    }
}
