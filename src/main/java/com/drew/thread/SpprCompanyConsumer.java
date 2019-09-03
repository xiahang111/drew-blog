package com.drew.thread;

import com.drew.item.pojo.DrewPageVisit;
import com.drew.item.pojo.SpprCompany;
import com.drew.service.SpprCompanyService;

import java.util.Date;
import java.util.concurrent.BlockingDeque;

public class SpprCompanyConsumer implements Runnable {

    private SpprCompanyService spprCompanyService;

    private BlockingDeque<SpprCompany> queue;

    public SpprCompanyConsumer() {
    }

    public SpprCompanyConsumer(SpprCompanyService spprCompanyService, BlockingDeque<SpprCompany> queue) {
        this.queue = queue;
        this.spprCompanyService = spprCompanyService;
    }

    @Override
    public void run() {

        while (true) {
            try {


                SpprCompany spprCompany = queue.take();

                if (null != spprCompany) {

                    spprCompanyService.insertSpprCompany(spprCompany);
                }

                System.out.println("=====================数据存放数据库成功==========companyName = "+ spprCompany.getCompanyName());

                Thread.sleep(10l);


            } catch (Exception e) {
                System.out.println("=====================数据存放数据库错误！！"+e.getMessage());
            }
        }

    }
}
