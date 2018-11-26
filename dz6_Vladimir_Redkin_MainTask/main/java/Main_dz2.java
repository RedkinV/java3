import java.lang.reflect.Array;

public class Main_dz2 {
    public static void main(String[] args) {
        Main_dz2 m=new Main_dz2();
        int[] arr={1,2,5,6,4};
        int[] newArr=m.modArray(arr);
        for (int a:newArr) {
            System.out.print(a+", ");
        }

    }

    public int[] modArray(int[] arr){
        int[] newArr;
        int pos4=-1;
        for (int i = arr.length-1; i >=0 ; i--) {
            if(arr[i]==4) {
                pos4=i;
                break;
            }
        }
        if(pos4==-1) {
            throw new RuntimeException();

        }

        newArr=new int[arr.length-pos4-1];
        System.arraycopy(arr,pos4+1,newArr,0,arr.length-pos4-1);
        return newArr;
    }
}
