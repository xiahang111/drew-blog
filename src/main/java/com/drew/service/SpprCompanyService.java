package com.drew.service;

import com.drew.item.pojo.SpprCompany;
import com.drew.mapper.SpprCompanyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpprCompanyService {

    @Autowired
    private SpprCompanyMapper spprCompanyMapper;

    public void insertSpprCompany(SpprCompany spprCompany){

        spprCompanyMapper.insert(spprCompany);
    }

    public List<SpprCompany> getAllByKeywords(String keywords){

        List<SpprCompany> spprCompanies = spprCompanyMapper.getByKeywords(keywords);

        for (SpprCompany company:spprCompanies) {

            String phoneNum =company.getPhoneNumber();

            phoneNum = "<img src='" + phoneNum + "'>";

            company.setPhoneNumber(phoneNum);

        }

        return spprCompanies;
    }


}
