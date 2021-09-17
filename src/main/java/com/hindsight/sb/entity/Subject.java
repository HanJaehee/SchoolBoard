package com.hindsight.sb.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "subject")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Subject extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "sub_id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;
}