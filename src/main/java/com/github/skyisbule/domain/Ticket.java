package com.github.skyisbule.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class Ticket {


    public String code;
    public String route_id;
    public List<Desc> desc;

    public String date;
    public String identity;
    public String only_day;


    @Override
    @JsonIgnore
    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append(date+"\n");
        desc.forEach(desc1 -> {
            result.append(desc1.start_time+" "+desc1.station_from_name+"->"
                    +desc1.station_to_name+"->剩余票数："+desc1.ticket_left);
            if (desc1.bus_type.equals("2")) result.append(" 加班车 ");
            if (desc1.issue_time.equals("2")) result.append("未开启");
            result.append("\n");
        });
        return result.toString();
    }


}
