package com.drew.intercepter;

import com.drew.item.pojo.DrewPageVisit;
import com.drew.single.PageVisitSingleQueue;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.BlockingDeque;
import java.util.regex.Pattern;

public class RequestIntercepter extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        BlockingDeque<DrewPageVisit> queue = PageVisitSingleQueue.getInstance();

        String uri = request.getRequestURI();

        String pattern = "^(/article/)[0-9]{1,10}";

//        if (uri.contains(".css") || uri.contains(".js")|| uri.contains(".gif")|| uri.contains(".jpg")||
//                uri.contains(".png")|| uri.contains(".ico")|| uri.contains(".jpeg")){
//            return true;
//        }

        if (Pattern.matches(pattern, uri)) {
            //获取Ip地址
            String ipAddr = request.getRemoteAddr();

            DrewPageVisit visit = new DrewPageVisit();
            visit.setIpAddr(ipAddr);
            visit.setPageName(uri);

            queue.offer(visit);
        }


        return true;
    }
}
