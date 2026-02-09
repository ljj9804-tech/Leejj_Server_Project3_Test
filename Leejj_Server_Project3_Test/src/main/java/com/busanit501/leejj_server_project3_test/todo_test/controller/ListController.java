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
import java.util.List;

@WebServlet(name = "ListController", urlPatterns = "/todo_test/list")
@Log4j2
public class ListController extends HttpServlet {
    //서비스 기능 가져오기
    private Service todoService = Service.INSTANCE;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        log.info("===전체 목록 조회=====");

        try {
            //db값을 담을 객체
            List<DTO> dtoList = todoService.listAll();

            //담아온 값을 화면에 띄우기
            req.setAttribute("dtoList", dtoList);

            //결과 화면 경로
            req.getRequestDispatcher("/WEB-INF/todo_test/list.jsp")
                    .forward(req,resp);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServletException("list error");
        }


    }

}
