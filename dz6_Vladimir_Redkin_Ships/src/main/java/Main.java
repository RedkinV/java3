import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        CyclicBarrier cb=new CyclicBarrier(10);
        PortFrom portFrom=new PortFrom(700,5900,8500);
        PortTo portTo=new PortTo();
        Channel channel=new Channel();
        Route route=new Route(channel,portFrom,channel,portTo);
        Fleet fleet=new Fleet(  new Ship("fo_1", "food", 500,route,cb),
                                new Ship("fo_2", "food", 500,route,cb),
                                new Ship("fo_3", "food", 500,route,cb),
                                new Ship("fu_1", "fuel", 500,route,cb),
                                new Ship("fu_2", "fuel", 500,route,cb),
                                new Ship("fu_3", "fuel", 500,route,cb),
                                new Ship("te_1", "textile", 500,route,cb),
                                new Ship("te_2", "textile", 500,route,cb),
                                new Ship("te_3", "textile", 500,route,cb));

//        Ship ship1=new Ship("te_3", "textile", 500,route);
//        Thread t1=new Thread(ship1);
//        t1.start();
//        t1.join();
        portFrom.warehouseStatus();
        portTo.warehouseStatus();

        ExecutorService pool;

        while (true){
            pool= Executors.newFixedThreadPool(fleet.getCount());
            for (Ship s:portFrom.acquireShips(fleet).getShips()) {
                pool.execute(s);
            }
            pool.shutdown();
            while (true) {
                if (pool.isTerminated()) break;
            }
            if (portFrom.isWarehouseEmpty()) break;
        }

//        pool.shutdown();
//        while(true){
//            if (pool.isTerminated()) break;
//        }

        portFrom.warehouseStatus();
        portTo.warehouseStatus();


    }
}
