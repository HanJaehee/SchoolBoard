package com.hindsight.sb.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user")
@Getter
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "student_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone_no", nullable = false)
    private String phoneNo;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole userRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id")
    private DeptEntity deptEntity;

}
