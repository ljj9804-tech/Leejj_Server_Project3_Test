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
@WebServlet(name="RegController", urlPatterns = "/todo_test/register")
public class RegController extends HttpServlet {


    // 시간의 포맷 형태를 변경하는 기능을 추가. HH : 24시간제 표기 , a(오전/오후) hh : 1 ~12시 나타냄.
    private final DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    // 2가지 기능을 제공
    // 1) 화면 제공, 2) 글쓰기 작업 수행.
    // 서비스의 기능을 가지고 있는 클래스 이용 : Service
    private Service todoService = Service.INSTANCE;

    // 글쓰기 화면 제공
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        log.info("/todo_test/register, 글작성 폼 임시화면 get으로 요청 처리함. ");
        req.getRequestDispatcher("/WEB-INF/todo_test/todoReg.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 받을 때 한글 설정,
        req.setCharacterEncoding("UTF-8");

        log.info("todo/register 글쓰기 로직 처리하는 곳입니다.");

        // 화면으로부터 전달받은 데이터를, -> DTO 객체 담아서, -> 서비스에 전달.
        DTO todoDTO = DTO.builder()
                .title(req.getParameter("title"))
//                .dueDate(req.getParameter("dueDate"))
                .dueDate(LocalDate.parse(req.getParameter("dueDate"),DATEFORMATTER))
                .build();
        //서비스에 전달.
        try {
            // 보낼 때 한글 설정,
            // 나중에, 서버에 한번만 설정해서, 따로 설정 없이, 이용만 하면됨.
            resp.setContentType("text/html;charset=UTF-8");
            resp.setCharacterEncoding("UTF-8");

            todoService.register(todoDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        resp.sendRedirect("/todo_test/list");

    }

}
