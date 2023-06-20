package org.platanus.webboard.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    /* 변경감지를 통해 JPA가 영속성을 관리 할 수 있도록 제거 할 것. 일단은 코드가 돌아야 하니.. */
    @Deprecated
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update File f set f.deleted = :#{#file.deleted} where f.id = :#{#file.id}")
    int updateDeleteFlag(@Param("file") File file);

    Optional<File> findById(long id);

    // 두고 봅시다.
    // Optional<File> findByManagementFilename(String mgntFilename);

    // 두고 봅시다.
    //int findByExpireFromSourceDatetimeToDestinationDatetime(LocalDateTime srcDatetime, LocalDateTime destDatetime);
}
