package com.hindsight.sb.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class CourseSubject extends BaseEntity{

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_Id")
    private UserEntity userEntity;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "subject_id")
    private SubjectEntity subjectEntity;

}
