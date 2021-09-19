package com.hindsight.sb.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "department")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DeptEntity extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "dept_id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "deptEntity")
    private List<UserEntity> users = new ArrayList<>();

    @Builder
    private DeptEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
