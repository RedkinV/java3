import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class PortTo extends Stage{
    private AtomicInteger textile;
    private AtomicInteger food;
    private AtomicInteger fuel;

    private ReentrantLock unloadLockTextile;
    private ReentrantLock unloadLockFuel;
    private ReentrantLock unloadLockFood;

    public PortTo() {
        this.unloadLockTextile = new ReentrantLock();
        this.unloadLockFuel = new ReentrantLock();
        this.unloadLockFood = new ReentrantLock();
        this.textile=new AtomicInteger();
        this.food=new AtomicInteger();
        this.fuel=new AtomicInteger();
    }

    public void warehouseStatus() {
        System.out.println("PortTo: textile: " + textile + ", food: " + food + ",fuel " + fuel);
    }

    @Override                       //unload
    public void doIt(Ship ship) {
        //System.out.print(ship.goodsInfo()+whInfo());
        switch (ship.getType()) {
            case "textile":
                unloadLockTextile.lock();
                textile.set(textile.get() + ship.getGoods());
                ship.setGoods(0);
                unloadLockTextile.unlock();
                break;
            case "food":
                unloadLockFood.lock();
                food.set(food.get() + ship.getGoods());
                ship.setGoods(0);
                unloadLockFood.unlock();
                break;
            case "fuel":
                unloadLockFuel.lock();
                fuel.set(fuel.get() + ship.getGoods());
                ship.setGoods(0);
                unloadLockFuel.unlock();
                break;

        }
        //System.out.println(ship.goodsInfo()+whInfo());
    }
    public String whInfo(){
        return "portTo  t:"+textile.get()+" fo:"+food.get()+" fu:"+fuel.get();
    }
}
