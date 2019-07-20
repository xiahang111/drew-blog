package com.drew.controller;

import com.drew.entity.ResponseResult;
import com.drew.service.MapperDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mapper")
public class MapperDemoController {

    @Autowired
    private MapperDemoService mapperDemoService;

    @RequestMapping("/getAll")
    public ResponseResult getAll(){

        return new ResponseResult(mapperDemoService.getAll());

    }

}
