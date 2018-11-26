import lesson4_mychat.server.AuthService;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

public class Test_AuthService {

    @Before
    public void init(){
        System.out.println("init");
    }
    @Test // connection test
    public void test1() {
        try {
            AuthService.connect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
