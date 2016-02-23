package com.jadework.controller;

import com.jadework.service.TaskService;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(HttpResponseController.class);

    @Resource
    HttpServletRequest request;


    @RequestMapping("/callback")
    public String callback(){
        String name = request.getParameter("name");
        String message = request.getParameter("message");

        logger.info("---TaskScheduleServer: 收到来自 {} 的Job执行状态信息-M-{}",
                name, message);


        return "收到返回信息";
    }

}
