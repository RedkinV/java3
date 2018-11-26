import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class PortFrom extends Stage {
    private AtomicInteger textile;
    private AtomicInteger food;
    private AtomicInteger fuel;
    private ReentrantLock loadLockTextile;
    private ReentrantLock loadLockFuel;
    private ReentrantLock loadLockFood;

    public PortFrom(int textile, int food, int fuel) {
        this.textile=new AtomicInteger();
        this.food=new AtomicInteger();
        this.fuel=new AtomicInteger();

        this.textile.set(textile) ;
        this.food.set(food);
        this.fuel.set(fuel);
        this.loadLockTextile = new ReentrantLock();
        this.loadLockFuel = new ReentrantLock();
        this.loadLockFood = new ReentrantLock();
    }

    public Fleet acquireShips(Fleet fleet) {
        Fleet newFleet = new Fleet();
        if (textile.get() > 0) {
            for (Ship s : fleet.getShips()) {
                if (s.getType().equals("textile")) newFleet.addShip(s);
                if (newFleet.totalCapacity("textile") >= textile.get()) break;
            }
        }

        if (food.get() > 0) {
            for (Ship s : fleet.getShips()) {
                if (s.getType().equals("food")) newFleet.addShip(s);
                if (newFleet.totalCapacity("food") >= food.get()) break;
            }
        }

        if (fuel.get() > 0) {
            for (Ship s : fleet.getShips()) {
                if (s.getType().equals("fuel")) newFleet.addShip(s);
                if (newFleet.totalCapacity("fuel") >= fuel.get()) break;
            }
        }
        return newFleet;
    }

    @Override                       //load
    public void doIt(Ship ship) {
        switch (ship.getType()) {
            case "textile":
                loadLockTextile.lock();


                if (textile.get() >= ship.getCapacity()) {
                    ship.setGoods(ship.getCapacity());
                    textile.set(textile.get() - ship.getCapacity());
                } else {
                    ship.setGoods(textile.get());
                    textile.set(0);
                }

                loadLockTextile.unlock();
                break;

            case "food":
                loadLockFood.lock();
                if (food.get() >= ship.getCapacity()) {
                    ship.setGoods(ship.getCapacity());
                    food.set(food.get() - ship.getCapacity());
                } else {
                    ship.setGoods(food.get());
                    food.set(0);
                }
                loadLockFood.unlock();
                break;
            case "fuel":
                loadLockFuel.lock();
                if (fuel.get() >= ship.getCapacity()) {
                    ship.setGoods(ship.getCapacity());
                    fuel.set(fuel.get() - ship.getCapacity());
                } else {
                    ship.setGoods(fuel.get());
                    fuel.set(0);
                }
                loadLockFuel.unlock();
                break;

        }
    }
    public void warehouseStatus() {
        System.out.println("PortFrom: textile: " + textile + ", food: " + food + ",fuel " + fuel);
    }
    public boolean isWarehouseEmpty(){
        return !((textile.get()+food.get()+fuel.get())>0);
    }
}
