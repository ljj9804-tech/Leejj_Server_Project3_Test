package com.busanit501.leejj_server_project3_test.todo_test.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;

public enum MapperUtil {
    INSTANCE;

    // 외부 기능 추가, 가져와서 사용하기.
    private ModelMapper modelMapper;

    // 생성자 작성.
    MapperUtil() {
        // 위에서 선언만 한 modelMapper, 생성자 호출 할 때, 초기화를 사용할수 있게 만들기.
        modelMapper = new ModelMapper();
        // 모델 맵퍼 사용하기 위한 , 기본 설정, (초기설정)한번만 설정 후, 재사용.
        this.modelMapper.getConfiguration()
                // dto <-> vo 클래스의 멤버의 일치 여부를 체크함.
                .setFieldMatchingEnabled(true)
                // 접근은 private 까지 가능하게
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                // 매칭시, 검사시, 엄격하게 검사함.
                .setMatchingStrategy(MatchingStrategies.STRICT);
    }
    // 외부에서, 해당 기능을 쉽게 이용하기 위해서, 메서드를 만들기.
    // 사용법: _0204_3_MapperUtil.INSTANCE.get()
    public ModelMapper get() {
        return modelMapper;
    }

}
