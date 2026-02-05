package com.busanit501.leejj_server_project3_test.todo_test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder //객체 생성을 체인기법으로
@NoArgsConstructor //기본 생성자를 생성함
@AllArgsConstructor //모든 멤버를 매개변수로 가지는 생성자를 생성함
public class DTO {
    private Long tno;
    private String title;
    private LocalDate dueDate;
    private  boolean finished;
}

/*VO에 담긴 DB테이블 값을 JAVA가 처리할 수 있게 타입 변환할 때 사용되는 객체
* 데이터 트랜스 폼 오브젝트*/