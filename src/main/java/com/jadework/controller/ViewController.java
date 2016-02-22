package com.jadework.controller;

import com.jadework.dao.TaskDao;
import com.jadework.model.User;
import com.jadework.service.TaskService;
import org.codehaus.jackson.map.ObjectMapper;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * @Copyright: Copyright (c) 2016,${year},
 * @Description: ${todo}
 */

@RestController
public class ViewController {



    @RequestMapping("/index")
    public ModelAndView toIndexPage(){
        return new ModelAndView("main");
    }







}
