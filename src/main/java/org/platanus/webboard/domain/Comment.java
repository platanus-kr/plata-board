package org.platanus.webboard.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "COMMENTS")
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
    @Transient
    private String authorNickname;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", articleId=" + articleId +
                ", content='" + content + '\'' +
                ", authorId=" + authorId +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", deleted=" + deleted +
                ", recommend=" + recommend +
                ", authorNickname='" + authorNickname + '\'' +
                '}';
    }
}
