package com.hindsight.sb.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UserRequest {

    @NotNull(message = "이름을 입력하세요.")
    private String name;
    @NotNull(message = "주소을 입력하세요.")
    private String address;
    @NotNull(message = "전화번호를 입력하세요.")
    private String phoneNo;
    @NotNull(message = "생년월일을 입력하세요.")
    private String birth;
    @NotNull(message = "타입(학생 = 0, 교수 = 1)을 입력하세요.")
    private Integer type;
    @NotNull(message = "전공 ID를 입력하세요")
    private Long deptId;

}
