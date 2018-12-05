# 一个没有名字的项目
那就直接开门见山吧，这个项目是用来监听中大南方车票系统的一个项目。<br>
使用它你可以通过简单的几行代码来监听某一天或某些天，车票的售卖情况。<br>
比如当有加班车放出来的时候，你可以给自己或他人发一条短信或者邮件，这样就可以省去你一直不断用手机“刷票”的麻烦了。
# 入门
如上述所说，你只需要实现事件发生后的行为即可，具体的事件定义如下：<br>
``` java
public interface TicketMonitor {

    //当监视者实例化时
    public void init();

    //当监视者第一次运行时  即第一次从服务器读取到数据时
    public void onFirstReadData(Ticket ticket);

    //当监视者退出时
    public void onDestroy(Ticket ticket);

    //爬取的值无法转成Ticket对象时
    public void onConvertFail(Ticket ticket);

    //每次抓取新数据时
    public void onFlushData(Ticket ticket);

    //每次有座位变化时
    public void onLeftSeatChange(Ticket ticket);

    //每次有加班车的状态改变时
    public void onOverTimeTrainChange(Ticket ticket);

    //每次有班次变化时
    public void onBusNumChange(Ticket ticket);
}
```
当然，对于只想监听加班车的同学就只需要实现onOverTimeTrainChange()方法或者onLeftSeatChange()方法即可。<br>
## Ticket对象
Ticket对象即为爬虫从售票系统拿到的数据的一个包装，每个字段具体的含义大致如下（只写了重要的）：
``` java
    public String  id;                //已知是用来区分学生车和职工车的
    public String  start_time;        //开始时间
    public String  station_from_name; //起始站
    public String  station_to_name;   //终点站
    public Integer ticket_left;       //剩余票数

    public String code;              //类似于zdnf06的那个车次号
    public String route_id;
    public String price;             //价格
    public String seat_num;          //总的座位数
    public String reserve_num;
    public String circle_rule;       //日期范围
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
```
另外，我重写了ticket的toString方法，具体输出大致为:<br>
2018-12-06<br>
08:00 南方学院->中大南校区->剩余票数：0<br>
09:00 南方学院->中大南校区->剩余票数：0<br>
09:40 南方学院->中大南校区->剩余票数：0 加班车 <br>
12:00 南方学院->中大南校区->剩余票数：0 加班车 <br>
12:30 南方学院->中大南校区->剩余票数：0 加班车 <br>
13:00 南方学院->中大南校区->剩余票数：0<br>
13:20 南方学院->中大南校区->剩余票数：3 加班车 <br>
17:00 南方学院->中大南校区->剩余票数：0<br>
17:30 南方学院->中大南校区->剩余票数：3 加班车 <br>
18:00 南方学院->中大南校区->剩余票数：0<br>
18:10 南方学院->中大南校区->剩余票数：0 加班车 <br>
18:15 南方学院->中大南校区->剩余票数：17 加班车 
# 一个简单的示例
此示例代码位于 test.java.Test

``` java
public class Test {

    private static class monitor implements TicketMonitor{

        @Override
        public void init() {
            System.out.println("初始化啦");
        }

        @Override
        public void onFirstReadData(Ticket ticket) {

        }

        @Override
        public void onDestroy(Ticket ticket) {
            System.out.println("线程退出啦，可以在这里执行一些关闭资源的操作。");
        }

        @Override
        public void onConvertFail(Ticket ticket) {

        }

        @Override
        public void onFlushData(Ticket ticket) {
            System.out.println("爬到新数据了，大致打印一下数据。");
            System.out.println(ticket.toString());
            //接下来可以发邮件给自己，提醒自己要抢票了。
        }

        @Override
        public void onLeftSeatChange(Ticket ticket) {

        }

        @Override
        public void onOverTimeTrainChange(Ticket ticket) {

        }

        @Override
        public void onBusNumChange(Ticket ticket) {

        }
    }

    public static void main(String[] args){
        String cookie = "Hm_lvt_a7682412dd0033e64587e8fa2fa2e9f7=1543197740,1543201561; PHPSESSID=6ko94scih3mvurd9qm9dvaca15";
        new SubscribleOutThread("2018-12-6",cookie,new monitor()).start();
    }

}
```
# 最后
have fun