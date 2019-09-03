package com.drew.single;

import com.drew.item.pojo.SpprCompany;
import com.drew.item.pojo.SpprCompany;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class SpprCompanySingleQueue {

    private volatile static BlockingDeque<SpprCompany> spprCompanyQueue = null;

    public static BlockingDeque<SpprCompany> getInstance(){

        if (spprCompanyQueue == null ){
            synchronized (BlockingDeque.class){
                if (spprCompanyQueue == null){
                    spprCompanyQueue = new LinkedBlockingDeque<>();
                }
            }
        }

        return spprCompanyQueue;

    }
}
