import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class Test_dz2 {
    Main_dz2 m;
    @Before
    public void init(){
        m=new Main_dz2();
        System.out.println("init");
    }
    @Test
    public void test1 (){
        Assert.assertArrayEquals(new int[]{5,6},m.modArray(new int[]{1,2,3,4,4,5,6}));
    }
@Test
    public void test2 (){
        Assert.assertArrayEquals(new int[]{5,6,7},m.modArray(new int[]{4,2,3,4,4,5,6,7}));
    }

    @Test (expected = RuntimeException.class)
    public void test3 () {
        Assert.assertArrayEquals(new int[]{5,6,7},m.modArray(new int[]{2,3,5,6,7}));
    }

    @Test
    public void test4 (){
        Assert.assertArrayEquals(new int[]{},m.modArray(new int[]{2,3,4,4,5,6,4}));
    }

}
