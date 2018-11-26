import java.util.concurrent.Semaphore;

public class Channel extends Stage {
    Semaphore semaphore;

    public Channel(){
        semaphore=new Semaphore(2);
    }

    @Override                       //pass the channel
    public void doIt(Ship ship) {
        try {
            System.out.println("Channel... Ready to pass "+ship.getName());
            semaphore.acquire();
            System.out.println("Channel... Passing... "+ship.getName());
            Thread.sleep(500);
            System.out.println("Channel... Successfully passed.. "+ship.getName());
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
