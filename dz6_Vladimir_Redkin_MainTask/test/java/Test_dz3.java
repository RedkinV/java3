import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Test_dz3 {
    Main_dz3 m;
    @Before
    public void init(){
        m=new Main_dz3();
    }
    @Test
    public void test1(){
        int[] arr={1,2,3,4,5,6};
        Assert.assertEquals(true,m.checkArr(arr));
    }

    @Test
    public void test2(){
        int[] arr={2,3,5,6};
        Assert.assertEquals(false,m.checkArr(arr));
    }
    @Test
    public void test3(){
        int[] arr={};
        Assert.assertEquals(false,m.checkArr(arr));
    }
}
