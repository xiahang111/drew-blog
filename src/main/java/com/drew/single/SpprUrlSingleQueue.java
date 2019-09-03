package com.drew.single;

import com.drew.item.pojo.SpprCompany;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class SpprUrlSingleQueue {

    private volatile static BlockingDeque<String> spprUrlQueue = null;

    public static BlockingDeque<String> getInstance(){

        if (spprUrlQueue == null ){
            synchronized (BlockingDeque.class){
                if (spprUrlQueue == null){
                    spprUrlQueue = new LinkedBlockingDeque<>();
                }
            }
        }

        return spprUrlQueue;

    }
}
