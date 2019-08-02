package com.drew.controller;

import com.drew.config.GlobalConfig;
import com.drew.service.VisitCountService;
import com.drew.single.PageVisitSingleQueue;
import com.drew.thread.PageVisitConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class IndexController {

    @Autowired
    private VisitCountService visitCountService;

    @RequestMapping("/")
    public ModelAndView index(HttpServletResponse response,
                              HttpServletRequest request){

        if (GlobalConfig.pageVisitConsumerCount.get() == 0){
            GlobalConfig.service.execute(new PageVisitConsumer(PageVisitSingleQueue.getInstance(),visitCountService));
            GlobalConfig.pageVisitConsumerCount.incrementAndGet();
        }

        return new ModelAndView("index");

    }

}
