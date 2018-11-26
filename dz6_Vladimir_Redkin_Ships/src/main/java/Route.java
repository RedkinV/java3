import java.util.ArrayList;
import java.util.Arrays;

public class Route {
    private ArrayList<Stage> stages;
    public ArrayList<Stage> getStages() {
        return stages;
    }
    public Route(Stage... stages) {
        this.stages = new ArrayList<>(Arrays.asList(stages));
    }
}
