package com.jadework.controller;

import com.jadework.service.TaskService;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Copyright: Copyright (c) 2016,${year},
 * @Description: ${todo}
 */


@RestController
public class HttpResponseController {

    @Resource
    HttpServletRequest request;


    @RequestMapping("/callback")
    public String callback(){
        String name = request.getParameter("name");
        String message = request.getParameter("message");

        System.out.println("---TaskScheduleServer:收到来自 "+name+" 的回调信息--"+message);

        return "收到返回信息";
    }

}
