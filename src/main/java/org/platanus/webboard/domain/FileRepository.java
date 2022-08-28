package org.platanus.webboard.domain;

import java.time.LocalDateTime;
import java.util.Optional;

public interface FileRepository {

    File upload(File file);

    int delete(long id);

    int deleteByUserId(long userId);

    int updateDeleteFlag(File file);

    Optional<File> findById(long id);

    Optional<File> findByManagementFilename(String mgntFilename);

    int findByExpireFromSourceDatetimeToDestinationDatetime(LocalDateTime srcDatetime, LocalDateTime destDatetime);

    int findAll();
}
