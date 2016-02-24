package com.jadework.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
