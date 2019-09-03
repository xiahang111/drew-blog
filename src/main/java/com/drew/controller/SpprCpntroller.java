package com.drew.controller;

import com.drew.entity.ResponseResult;
import com.drew.item.pojo.SpprCompany;
import com.drew.service.SpprCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sppr")
public class SpprCpntroller {

    @Autowired
    private SpprCompanyService spprCompanyService;

    @RequestMapping("/get")
    @ResponseBody
    public ResponseResult toSummaryPage(HttpServletResponse response,
                                        Model model,
                                        HttpServletRequest request){


        try {

            List<SpprCompany> companies = new ArrayList<>();
            companies = spprCompanyService.getAllByKeywords("铝");

            return new ResponseResult(companies);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseResult.error;
        }
    }

}
