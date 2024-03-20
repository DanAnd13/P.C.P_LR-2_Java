public class Main {

    public static void main(String[] args) {
        int dim = 10000000;
        int threadNum = 5;
        ArrClass arrClass = new ArrClass(dim, threadNum);
        System.out.println("Min elem "+arrClass.partMin(0,dim)+" with id "+arrClass.id);

        System.out.println("Min elem "+arrClass.threadMin()+" with id "+arrClass.id);
    }
}