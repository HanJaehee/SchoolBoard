package com.hindsight.sb.entity;

import com.hindsight.sb.dto.user.UserRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Arrays;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user")
@Getter
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone_no", nullable = false, unique = true)
    private String phoneNo;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole userRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id", nullable = false)
    private DeptEntity deptEntity;

    @Builder
    private UserEntity(String name, String address, String phoneNo, LocalDate birth, UserRole userRole, DeptEntity deptEntity) {
        this.name = name;
        this.address = address;
        this.phoneNo = phoneNo;
        this.birth = birth;
        this.userRole = userRole;
        this.deptEntity = deptEntity;
    }

    public static UserEntity of(UserRequest req, DeptEntity dept) {
        int[] birth = Arrays.stream(req.getBirth().split("-")).mapToInt(Integer::parseInt).toArray();
        return UserEntity.builder()
                .name(req.getName())
                .address(req.getAddress())
                .userRole(req.getType() == 0 ? UserRole.STUDENT : UserRole.PROFESSOR)
                .birth(LocalDate.of(birth[0], birth[1], birth[2]))
                .phoneNo(req.getPhoneNo())
                .deptEntity(dept)
                .build();
    }
}
