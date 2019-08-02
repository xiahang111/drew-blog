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

    public static AtomicInteger pageVisitConsumerCount = new AtomicInteger(0);

    public static final String PRE_ARTICLE_COUNT = "/article/";

}
