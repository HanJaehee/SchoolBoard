package com.hindsight.sb.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostEntity extends BaseEntity {

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false)
    private final PostStatus postStatus = PostStatus.NORMAL;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_id")
    private SubjectEntity subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "view_count", nullable = false)
    @ColumnDefault("0")
    private final Long viewCount = 0L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Builder
    private PostEntity(SubjectEntity subject, UserEntity user, String title, String content) {
        this.subject = subject;
        this.user = user;
        this.title = title;
        this.content = content;
    }
}
