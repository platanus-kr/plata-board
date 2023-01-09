package org.platanus.webboard.domain;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {

    // 이거는 save로 가야할것 같은데 일단 쟁여놓기
    //File upload(File file);

    // 기본 구현 되어있음
    //int delete(long id);

    int deleteByUserId(long userId);

    // 이것도 save로 퉁쳐야할듯
    //int updateDeleteFlag(File file);

    Optional<File> findById(long id);

    // 이거 될거같은데 왜 활성화가 안되지?
    Optional<File> findByManagementFilename(String mgntFilename);

    // 이건 JPQL.
    //int findByExpireFromSourceDatetimeToDestinationDatetime(LocalDateTime srcDatetime, LocalDateTime destDatetime);

    // 고쳐요
    //int findAll();
}
