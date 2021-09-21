package com.hindsight.sb.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "course_subject")
@Getter
public class CourseSubjectEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity student;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "subject_id")
    private SubjectEntity subject;

    @Builder
    private CourseSubjectEntity(UserEntity student, SubjectEntity subject) {
        this.student = student;
        this.subject = subject;
    }

    public static CourseSubjectEntity of(SubjectEntity subject, UserEntity student) {
        return CourseSubjectEntity.builder()
                .subject(subject)
                .student(student)
                .build();
    }
}
