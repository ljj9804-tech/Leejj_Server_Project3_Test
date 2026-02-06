package com.busanit501.leejj_server_project3_test.todo_test.dao;

import com.busanit501.leejj_server_project3_test.todo_test.domain.VO;
import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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

    //등록하기.
    public void insert(VO vo) throws Exception {
        // sql 문장 작성,
        String sql = "insert into tbl_menu (title, dueDate, finished) values (?, ? ,?)";
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

    // 목록 기능 구현하기.
    public List<VO> selectAll() throws Exception {
        // sql 문장 작성,
        String sql = "select * from tbl_menu order by tno desc";

        // 디비 서버에 연결하는 도구 설정.(반복)
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();

        // sql 문장을 담아 두는 기능(반복)
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // sql 문장을 디비 서버에 전달.  and 결과 테이블을 받아와서, 담아두기.
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        // 데이터베이스로 받아온 내용을, 리스트로 변환 하는 작업.
        // 임시로 담아둘 리스트를 선언
        List<VO> list = new ArrayList<>();

        while (resultSet.next()) {
            // 객체에 담기 위해서, 임시 객체를 생성, builder 패턴이용함.
            VO vo = VO.builder()
                    .tno(resultSet.getLong("tno"))
                    .title(resultSet.getString("title"))
                    .dueDate(resultSet.getDate("dueDate").toLocalDate())
                    .finished(resultSet.getBoolean("finished"))
                    .build();

            list.add(vo);
        }
        return list;
    }

    // 한개만 조회, 특징, 조회할 tno 번호를 알고 있다고 가정.
    // 조회할 todo 를 클릭을 하면, 클릭한 todo tno 번호를 화면으로 부터 전달을 받음.
    public VO selectOne(Long tno) throws Exception {
        // sql 문장 작성,
        String sql = "select * from tbl_menu where tno = ?";

        // 디비 서버에 연결하는 도구 설정.(반복)
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();

        // sql 문장을 담아 두는 기능(반복)
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // 와일드 카드 값을 추가. tno = ?
        preparedStatement.setLong(1,tno);

        // sql 문장을 디비 서버에 전달.  and 결과 테이블을 받아와서, 담아두기.
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        // 데이터베이스로 받아온 내용을, 하나 객체로 변환 하는 작업.


        // 반복문을 이용해서, 데이터베이스 내용 -> 리스트의 요소의 객체에 각각 담기 놀이.
        // resultSet, 테이블, 준비는 0행부터 준비를하고, next() 실행하면, 데이터가 있으면 다음행으로 갑니다.
        // 예시) 데이터베이스의 현재 데이터의 테이블
        // 0행은 없죠?
//        예시 -> tno = 4
        //  tno,title,dueDate,finished
//     1행   4,샘플제목22,2026-02-03,0

        resultSet.next();
        // 객체에 담기 위해서, 임시 객체를 생성, builder 패턴이용함.
        VO vo = VO.builder()
                .tno(resultSet.getLong("tno"))
                .title(resultSet.getString("title"))
                // 타입을 일치 시켜주기,
                .dueDate(resultSet.getDate("dueDate").toLocalDate())
                .finished(resultSet.getBoolean("finished"))
                .build();


        return vo;
    }

    //수정하기
    // 글 등록하기와 구조가 비슷함.
    // 수정할 내용을 담을 데이터가 받아 왔다고 가정하고, 진행.
    // 나중에, 화면에서 수정할 내용을 받아오게 됩니다. 받아오면, 그 데이터를 여기 : _0203_1_TodoVO todoVO 담기.
    // 여기서, _0203_1_TodoVO todoVO에서 변경할 내용을 꺼내서, -> 데이터베이스에 수정합니다.
    public void updateOne(VO todoVO) throws Exception {

        // 글쓰기 작업과 거의 동일하므로, 위의 기능을 복붙해서, 수정하기.
        // sql 구문이 다름.
        // sql 문장 작성,
        String sql = "update tbl_menu set title = ?, dueDate = ?, finished = ? where tno = ?";
        // 디비 서버에 연결하는 도구 설정.(반복)
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        // sql 문장을 담아 두는 기능 (반복)
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        // values (?, ? ,?) 값 지정 해주기.
        preparedStatement.setString(1, todoVO.getTitle());
        preparedStatement.setDate(2, Date.valueOf(todoVO.getDueDate()));
        preparedStatement.setBoolean(3, todoVO.isFinished());
        preparedStatement.setLong(4, todoVO.getTno());
        // sql 문장을 디비 서버에 전달. 쓰기, 수정, 삭제 : executeUpdate()
        // 조회 : executeQuery();
        preparedStatement.executeUpdate();
    }

    //삭제하기.
    // 삭제할 tno 번호를 알고 있음.
    public void deleteOne(Long tno) throws Exception {
        // tno = 4 번 삭제하기.
        // 글쓰기, 수정하기, 거의 비슷, 수정하기를 코드 복붙해서, sql 수정해서 사용하기.
        String sql = "delete from tbl_todo where tno = ?";
        // 디비 서버에 연결하는 도구 설정.(반복)
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        // sql 문장을 담아 두는 기능 (반복)
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        // values (?, ? ,?) 값 지정 해주기.
        preparedStatement.setLong(1, tno);

        // sql 문장을 디비 서버에 전달. 쓰기, 수정, 삭제 : executeUpdate()
        // 조회 : executeQuery();
        preparedStatement.executeUpdate();

    }



}//DAT class
