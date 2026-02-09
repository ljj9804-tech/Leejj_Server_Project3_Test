package com.busanit501.leejj_server_project3_test.todo_test.controller;

import com.busanit501.leejj_server_project3_test.todo_test.dto.DTO;
import com.busanit501.leejj_server_project3_test.todo_test.service.Service;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Log4j2
@WebServlet(name = "UpdateController", urlPatterns = "/todo_test/update")
public class UpdateController extends HttpServlet {

    //서비스 기능 가져오기
    private Service todoService = Service.INSTANCE;
    //날짜 데이터 서식 지정하기
    private final DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        //한글 깨짐 방지
        req.setCharacterEncoding("UTF-8");

        log.info("todo_test/update 수정 화면입니다.");

        try {
            // 하나 조회 했고, 수정 화면으로 넘갈 때, 우리는 조회하는 todo의 tno 번호를 알고 있다.
            // 하나조회(상세보기) , URL : http://localhost:8080/todo_test/read?tno=15
            // 여기서, tno 번호를 서버에 전달하기.
            // 서버입장에서, tno 번호를 가져오기. 문자열 -> 숫자 타입 변경, 파싱 : Long.parseLong()
            Long tno = Long.parseLong(req.getParameter("tno"));


            DTO todoDTO = todoService.get(tno);


            req.setAttribute("dto", todoDTO);
            req.getRequestDispatcher("/WEB-INF/todo_test/modify.jsp").forward(req, resp);


            //한글 깨짐 방지
            resp.setContentType("text/html;charset=UTF-8");
            resp.setCharacterEncoding("UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 받을 때 한글 설정,
        req.setCharacterEncoding("UTF-8");

        // 주의사항,
        // 수정시, 완료 여부, 체크박스 변수명 : finished 넘어옴, 문자열 체크 되면 : "on"
        // 화면으로부터 전달받은 데이터를 모두 가져오고, 이상태는 모두 문자열임.
        // 그래서, dto 에 담을 때 각 타입에 맞게 변형해서 담기.
        String finishedStr = req.getParameter("finished");
        String tno = req.getParameter("tno");
        String title = req.getParameter("title");
        String dueDate = req.getParameter("dueDate");

        // 수정할 내용을 받은 상태, 수정된 내용으로 , 서비스에게 일을 시키기.
        // 넘어온 데이터를 dto 담기.
        DTO todoDTO = DTO.builder()
                .tno(Long.parseLong(tno))
                .title(title)
                // dueDate 문자열 -> LocalDate 타입으로 변경하고, 원하는 포맷팅 :yyyy-MM-dd
                .dueDate(LocalDate.parse(dueDate,DATEFORMATTER))
                .finished(finishedStr != null && finishedStr.equals("on"))
                .build();

        log.info("화면으로 전달 받은 , 수정할 내용 확인 todoDTO: " + todoDTO);
        try {
            // 보낼 때 한글 설정,
            // 나중에, 서버에 한번만 설정해서, 따로 설정 없이, 이용만 하면됨.
            resp.setContentType("text/html;charset=UTF-8");
            resp.setCharacterEncoding("UTF-8");

            todoService.modify(todoDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.sendRedirect("/todo_test/list");
    }
}