package org.platanus.webboard.domain;

import java.time.LocalDateTime;

public interface FileRepository {

    File upload(File file);

    int delete(File file);

    int deleteByUserId(long userId);

    int updateDeleteFlag(File file);

    int findById(long id);

    int findByManagementFilename(String mgntFilename);

    int findByExpireFromSourceDatetimeToDestinationDatetime(LocalDateTime srcDatetime, LocalDateTime destDatetime);

    int findAll();
}
