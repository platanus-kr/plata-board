package org.platanus.webboard.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long articleId;

    @NotBlank
    private String content;
    private Long authorId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private boolean deleted;
    private Long recommend;

    // add join
    private String authorNickname;
}
