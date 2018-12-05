package com.github.skyisbule.thread;

import cn.hutool.core.text.UnicodeUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.skyisbule.callback.TicketMonitor;
import com.github.skyisbule.domain.Desc;
import com.github.skyisbule.domain.Ticket;
import com.github.skyisbule.util.DateUtil;

import java.util.HashMap;


public class SubscribleOutThread extends Thread {

    public SubscribleOutThread(String date,String cookie,TicketMonitor monitor){
        this.date    = date;
        this.cookie  = cookie;
        this.monitor = monitor;
        param = new HashMap<>();
        param.put("time", DateUtil.getSpecifiedDayAfter(date));
        param.put("route_id",13);
        param.put("type","prevday");
    }

    private String date;
    private String cookie;
    private HashMap<String,Object> param;
    private ObjectMapper mapper = new ObjectMapper();
    private TicketMonitor monitor;
    private boolean firstRead = true;

    //以下为生命周期函数所需要的监听变量
    private Integer lastLeftTicket = 0;


    @Override
    public void run(){
        System.out.println("开始尝试监听："+date+"去中大的情况");
        monitor.init();
        while (DateUtil.compareDate(date, DateUtil.getNowDate()) >= 0) {
            String result = doRequest();
            try {
                Thread.sleep(1000 * 5);
                //json data 序列化为对象
                Ticket ticket = mapper.readValue(result, Ticket.class);
                //开始进行生命周期函数处理
                //每次读到数据时，即每次都执行
                if (!firstRead) monitor.onFlushData(ticket);
                //第一次读取数据时
                if (firstRead) {
                    monitor.onFirstReadData(ticket);firstRead = false;
                }
                //当剩余座位发生变化时
                if (leftTicketChanged(ticket)) monitor.onLeftSeatChange(ticket);

            } catch (Exception e) {
                System.out.println("去中大：" + date + " obj序列化失败，爬虫的返回值为：" + result);
            }

        }
    }

    //检测剩余座位有没有变化
    private boolean leftTicketChanged(Ticket ticket){
        int nowLeftTicketNum = 0;
        for (Desc desc : ticket.desc) {
            nowLeftTicketNum += desc.ticket_left;
        }
        if (lastLeftTicket != nowLeftTicketNum){
            lastLeftTicket = nowLeftTicketNum;
            return true;
        }
        return false;
    }

    //获取最新的信息
    private String doRequest(){
        String url = "http://nfuedu.zftcloud.com/campusbus_index/ticket/search_schedule.html";
        return UnicodeUtil.toString(HttpRequest.post(url)
                .header(Header.USER_AGENT, "chrome")
                .header("Cookie", cookie)
                .form(param)
                .timeout(20000)
                .execute().body());
    }

}
