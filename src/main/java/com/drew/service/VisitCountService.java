package com.drew.service;

import com.drew.item.pojo.DrewPageVisit;
import com.drew.mapper.DrewPageVisitMapper;
import com.drew.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 页面数据统计服务类
 */
@Service
public class VisitCountService {

    public static void main(String[] args){
        Date now = new Date();

        Date former = DateUtils.addSceond(now,-2);

        System.out.println(former);
    }


    @Autowired
    DrewPageVisitMapper drewPageVisitMapper;

    public void addPageVisit(DrewPageVisit pageVisit){



        Date now = pageVisit.getVisitTime();
        Date former = DateUtils.addSceond(now,-2);

        DrewPageVisit condition = new DrewPageVisit();
        condition.setPageName(pageVisit.getPageName());
        condition.setIpAddr(pageVisit.getIpAddr());
        condition.setVisitTime(former);

        if (null == drewPageVisitMapper.selectOneByPageVisit(condition)){

            try {
                drewPageVisitMapper.insert(pageVisit);
            }catch (Exception e){

            }

        }
    }
}
