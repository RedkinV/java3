import java.util.ArrayList;
import java.util.Arrays;

public class Fleet {
    private ArrayList<Ship> ships;
    public ArrayList<Ship> getShips() {
        return ships;
    }
    public Fleet(Ship... ships) {
        this.ships = new ArrayList<>(Arrays.asList(ships));
    }
    public int totalCapacity (String type){
        int t=0;
        for (Ship s: ships) {
            if(type.equals(s.getType())) {
                t+=s.getCapacity();
            }
        }
        return t;
    }
    public void addShip(Ship ship){
        ships.add(ship);
    }

    @Override
    public String toString() {
        return "Fleet{" +
                "ship=" + ships +
                '}';
    }
    public int getCount(){
        return ships.size();
    }
}
