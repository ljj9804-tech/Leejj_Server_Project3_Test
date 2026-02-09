package com.busanit501.leejj_server_project3_test.todo_test.controller;

import com.busanit501.leejj_server_project3_test.todo_test.service.Service;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@WebServlet(name = "DeleteController", urlPatterns = "/todo_test/delete")
public class DeleteController extends HttpServlet {

    private Service todoService = Service.INSTANCE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 삭제를 할 때, 어떤 tno를 삭제할지 알고 있음. 그래서, tno 번호도 알고 있음.
        String tnoStr = req.getParameter("tno");
        Long tno = Long.parseLong(tnoStr);

        try {
            todoService.remove(tno);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServletException("삭제 오류");
        }
        resp.sendRedirect("/todo_test/list");
    }

}
