package com.drew.mapper;

import com.drew.item.pojo.SpprCompany;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SpprCompanyMapper extends Mapper<SpprCompany> {

    @Insert("INSERT INTO sppr_company  ( id,company_name,business,person_name,post_code,phone_number,address ) VALUES( #{id},#{companyName},#{business},#{personName},#{postCode},#{phoneNumber},#{address} )")
    public void insertSpprCompany(SpprCompany spprCompany);

    @Select("select * from sppr_company where 1=1 and company_name like CONCAT(CONCAT('%',#{name},'%'))")
    public List<SpprCompany> getByKeywords(String keywords);
}
