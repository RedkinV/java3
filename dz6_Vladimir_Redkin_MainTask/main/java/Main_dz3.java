public class Main_dz3 {

    public static void main(String[] args) {
        int arr[]={5,2,3,5,5,6};
        Main_dz3 m=new Main_dz3();
        System.out.println(m.checkArr(arr));
    }
    public boolean checkArr (int[] arr){
        for (int a: arr) {
            if (a==1||a==4) return true;
        }
        return false;
    }
}
