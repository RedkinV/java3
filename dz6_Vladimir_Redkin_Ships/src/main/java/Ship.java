import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Ship implements Runnable {
    private int capacity;
    private String name;
    private String type;
    private int goods;
    private Route route;
    private CyclicBarrier cb;
    static int SHIP_COUNT = 0;

    public Ship(String name, String type, int capacity,  Route route, CyclicBarrier cb) {
        this.capacity = capacity;
        this.name = name;
        this.type = type;
        this.goods = 0;
        this.route=route;
        this.cb=cb;
    }

    public String getType() {
        return type;
    }

    public void run() {
        //System.out.println("Ship "+name+" is going after "+type);
        for (Stage st: route.getStages()) {
            st.doIt(this);
        }
        //System.out.println("Ship "+name+" delivered "+type);
    }


    public String goodsInfo(){
        return name+": "+type+"-"+goods;
    }
    public int getCapacity() {
        return capacity;
    }

    public int getGoods() {
        return goods;
    }

    public void setGoods(int goods) {
        this.goods = goods;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        //return name  +", " + type + ", cap=" + capacity +'}';
        return name;
    }
}
