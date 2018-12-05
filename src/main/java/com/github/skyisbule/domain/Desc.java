package com.github.skyisbule.domain;

public class Desc {

    public String  id;
    public String  start_time;        //开始时间
    public String  station_from_name; //起始站
    public String  station_to_name;   //终点站
    public Integer ticket_left;       //剩余票数

    public String code;              //已知是用来区分学生车和职工车的
    public String route_id;
    public String price;
    public String seat_num;
    public String reserve_num;
    public String circle_rule;
    public String allow_weekday;
    public String bus_type;           //普通车跟加班车
    public String status;
    public String hint;
    public String remark;
    public String pathway;            //途径地点  磨碟沙啊那些
    public String live_time;
    public String specail_range;
    public String specail_seat;
    public String children_ticket;    //额外预留的座位  4个
    public String take_station;
    public String appoint_status;
    public String least_seat;
    public String take_station_limit;
    public String release_seat;

    public String issue_time;        //发车状态
    public String view_schedule;     //调度用的  为2 的话代表是加班车且不显示  而且并非永远是int类型
    public String view_extra;        //是否显示

}
