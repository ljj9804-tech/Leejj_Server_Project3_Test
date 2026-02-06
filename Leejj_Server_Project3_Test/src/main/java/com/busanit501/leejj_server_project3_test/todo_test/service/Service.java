package com.busanit501.leejj_server_project3_test.todo_test.service;

import com.busanit501.leejj_server_project3_test.todo_test.dao.DAO;
import com.busanit501.leejj_server_project3_test.todo_test.domain.VO;
import com.busanit501.leejj_server_project3_test.todo_test.dto.DTO;
import com.busanit501.leejj_server_project3_test.todo_test.util.MapperUtil;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Log4j2
public enum Service {
    INSTANCE;


    private DAO dao; //db서버에 작업을 시키는 클래스(쿼리문으로 작업을 시킴)
    private ModelMapper modelMapper; //dto <-> vo 클래스를 변환 해주는 기능 클래스

    Service() {
        // 위에 전역으로 선언만 한 객체를 여기서 초기화해서, 사용할수 있게 하기.
        dao = new DAO();
        modelMapper = MapperUtil.INSTANCE.get();
    }

    // 기능구현,
    // 글쓰기
    public void register(DTO todoDTO) throws Exception {
        // todoDTO -> todoVo 변환
        VO todoVO = modelMapper.map(todoDTO, VO.class);

        // 변환 확인.
//        System.out.println("_0204_4_TodoService에서 register 작업중, 변환 결과 확인 todoVO : " + todoVO);
        // 0204,Log4j2 2
        log.info("Service에서 register 작업중, 변환 결과 확인 todoVO : " + todoVO);

        // DB 에 작업 시키는 클래스를 이용해서, DB 서버에 쓰기 작업하기.
        // 기본 메서드에, 예외처리를 가능하게 변경.
        dao.insert(todoVO);
    }

    //Todo 조회
    // 화면에서, 무엇을 조회할지는 알고 있다. 예시) tno = 1
    public DTO get(Long tno) throws Exception {
        // DB로부터 전달 받아서, 사용.
        VO todoVO = dao.selectOne(tno);
        // vo -> dto 타입으로 , 모델 맵퍼 이용해서, 변환.
        DTO todoDTO = modelMapper.map(todoVO, DTO.class);
        return todoDTO;
    }


    // 목록조회
    // 이전 코드
    public List<DTO> getList() {
        // 하드코딩, 더미 데이터로, 10개만 샘플 등록,
        // 반복문으로
        List<DTO> todoDTOS = IntStream.range(0,10).mapToObj(
                i -> {
                    // todo 하나가, todoDTO 객체. 임시 객체 생성해서, 여기에 더미 값을 담기.
                    DTO dto = new DTO();
                    dto.setTno((long)i);
                    dto.setTitle("Todo.." + i);
                    dto.setDueDate(LocalDate.now());
                    return dto;
                }
        ).collect(Collectors.toList());// mapToObj 닫기 태그, 반복문으로 각각의 todo 객체를 생성해서, 리스트로 만들기.
        return todoDTOS;
    } //getList 닫기




}
