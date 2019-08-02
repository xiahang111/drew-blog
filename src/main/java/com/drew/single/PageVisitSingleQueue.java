package com.drew.single;

import com.drew.item.pojo.DrewPageVisit;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class PageVisitSingleQueue {

    private volatile static BlockingDeque<DrewPageVisit> pageVisitQueue = null;

    public static BlockingDeque<DrewPageVisit> getInstance(){

        if (pageVisitQueue == null ){
            synchronized (BlockingDeque.class){
                if (pageVisitQueue == null){
                    pageVisitQueue = new LinkedBlockingDeque<>();
                }
            }
        }

        return pageVisitQueue;

    }
}
