package com.busanit501.leejj_server_project3_test.todo_test.controller;

import com.busanit501.leejj_server_project3_test.todo_test.dao.DAO;
import org.modelmapper.ModelMapper;

public enum Service {
    INSTANCE;

    private DAO dao;
    private ModelMapper modelMapper;
}
