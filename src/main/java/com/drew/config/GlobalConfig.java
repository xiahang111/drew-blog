package com.drew.config;

import com.drew.single.PageVisitSingleQueue;
import com.drew.thread.PageVisitConsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class GlobalConfig {

    public static final String ALL_ARTICLES_KEY = "ALL_ARTICLES_KEY";

    public static ExecutorService service = Executors.newSingleThreadExecutor();

    public static ExecutorService fixservice = Executors.newFixedThreadPool(5);

    public static AtomicInteger pageVisitConsumerCount = new AtomicInteger(0);

    public static AtomicInteger spprCompanyConsumerCount = new AtomicInteger(0);

    public volatile static Integer spprCompanyCount = 1002668;



    public static final String PRE_ARTICLE_COUNT = "/article/";

}
