import com.github.skyisbule.callback.TicketMonitor;
import com.github.skyisbule.config.Config;
import com.github.skyisbule.domain.Ticket;
import com.github.skyisbule.thread.SubscribleOutThread;

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

        }

        @Override
        public void onConvertFail(Ticket ticket) {

        }

        @Override
        public void onFlushData(Ticket ticket) {
            System.out.println("爬到新数据了");
            System.out.println(ticket.toString());
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
        new SubscribleOutThread("2018-12-6",cookie,new monitor(), Config.SCHOOL_TO_ZHONGDA).start();
    }

}
