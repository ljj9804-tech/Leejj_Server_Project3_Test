package com.busanit501.leejj_server_project3_test.todo_test.domain;


import lombok.*;

import java.time.LocalDate;

@Getter //getter 직접 생성 안하고, 메모리에 만들어놓기
@Builder //객체 생성 편하게 하기(체인방식)
@ToString //String 직접 생산 안하고...
@NoArgsConstructor //기본생성자 생성
@AllArgsConstructor //모든 멤버를 매개변수로 가지는 생성자를 생성

public class VO {
    private Long tno;
    private String title;
    private LocalDate dueDate;
    private  boolean finished;
}

/*vo에 DB 테이블 원본을 담아둠
* 이걸 DAO가 가져가서 DTO에서 JAVA가 읽을 수 있게 변환시킬거임(맵퍼 이용예정)
* 데이터 밸류 오브젝트*/