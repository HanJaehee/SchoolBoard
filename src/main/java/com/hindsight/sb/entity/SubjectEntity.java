package com.hindsight.sb.entity;

import com.hindsight.sb.dto.subject.SubjectRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "subject")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SubjectEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "super_id")
    private UserEntity supervisor;

    @Builder
    private SubjectEntity(String name, UserEntity supervisor) {
        this.name = name;
        this.supervisor = supervisor;
    }

    public static SubjectEntity of(SubjectRequest req, UserEntity supervisor) {
        return SubjectEntity.builder()
                .name(req.getName())
                .supervisor(supervisor)
                .build();
    }
}