package com.busanit501.leejj_server_project3_test.todo_test.dao;

import com.busanit501.leejj_server_project3_test.todo_test.domain.VO;
import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Log4j2
public class DAO {
    public String getTime() {
        //현재 시간을 받아둘 임시 변수.
        String now = null;
        log.info("테스트");
        try (Connection connection = ConnectionUtil.INSTANCE.getConnection();
             // 현재 시간을 조회하는 쿼리를 전달하고
             PreparedStatement preparedStatement = connection.prepareStatement("select now()");
             // 디비 서버에 전달하고, 결과를 받아와서, 담아두기.
             ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            // resultSet, 담겨진 시간을 조회
            resultSet.next();
            now = resultSet.getString(1);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("에러");
        }
        return now;
    } //getTime

    public String getTime2() throws Exception {
        // @Cleanup와 같은 효과 = connection.close()
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        // 현재 시간을 조회하는 쿼리를 전달하고
        // @Cleanup와 같은 효과 = preparedStatement.close()
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement("select now()");
        // 디비 서버에 전달하고, 결과를 받아와서, 담아두기.
        // @Cleanup와 같은 효과 = resultSet.close()
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        // resultSet, 담겨진 시간을 조회
        resultSet.next();
        String now = resultSet.getString(1);

        return now;
    }

    public void insert(VO vo) throws Exception {
        // sql 문장 작성,
        String sql = "insert into tbl_todo (title, dueDate, finished) values (?, ? ,?)";
        // 디비 서버에 연결하는 도구 설정.
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        // sql 문장을 담아 두는 기능
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        // values (?, ? ,?) 값 지정 해주기.
        preparedStatement.setString(1, vo.getTitle());
        preparedStatement.setDate(2, Date.valueOf(vo.getDueDate()));
        preparedStatement.setBoolean(3, vo.isFinished());
        // sql 문장을 디비 서버에 전달. 쓰기, 수정, 삭제 : executeUpdate()
        // 조회 : executeQuery();
        preparedStatement.executeUpdate();
    }

}//DAT class
