package com.drew.mapper;

import com.drew.item.pojo.DrewPageVisit;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface DrewPageVisitMapper extends Mapper<DrewPageVisit> {

    @Select("select count(*) from drew_page_visit where page_name = #{pageName}")
    public Integer selectCountByPageName(@Param("pageName") String pageName);

    @Select("select * from drew_page_visit where page_name = #{pageName} and ip_addr = #{ipAddr} and visit_time >= #{visitTime}")
    public DrewPageVisit selectOneByPageVisit(DrewPageVisit drewPageVisit);
}
