package com.github.skyisbule.callback;

import com.github.skyisbule.domain.Ticket;

public interface TicketMonitor {

    //当监视者实例化时
    public void init();

    //当监视者第一次运行时  即第一次从服务器读取到数据时
    public void onFirstReadData(Ticket ticket);

    //当监视者退出时
    public void onDestroy(String finalResult);

    //爬取的值无法转成Ticket对象时
    public void onConvertFail(String ticketResult);

    //每次抓取新数据时
    public void onFlushData(Ticket ticket);

    //每次有座位变化时
    public void onLeftSeatChange(Ticket ticket);

    //每次有加班车的状态改变时
    public void onOverTimeTrainChange(Ticket ticket);

    //每次有班次变化时
    public void onBusNumChange(Ticket ticket);
}
