package com.drew.thread;

import com.drew.item.pojo.DrewPageVisit;
import com.drew.mapper.DrewPageVisitMapper;
import com.drew.service.VisitCountService;
import com.drew.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Date;
import java.util.concurrent.BlockingDeque;

public class PageVisitConsumer implements Runnable {


    private VisitCountService visitCountService;

    BlockingDeque<DrewPageVisit> queue;

    public PageVisitConsumer(BlockingDeque<DrewPageVisit> queue,VisitCountService visitCountService){
        this.queue = queue;
        this.visitCountService = visitCountService;
    }

    @Override
    public void run() {

        try {

            while (true){
                DrewPageVisit drewPageVisit = queue.take();

                if (null != drewPageVisit){
                    drewPageVisit.setVisitTime(new Date());
                    visitCountService.addPageVisit(drewPageVisit);
                }
            }


        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
