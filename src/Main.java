public class Main {

    public static void main(String[] args) {
        int dim = 10000000;
        int threadNum = 5;
        ArrClass arrClass = new ArrClass(dim, threadNum);
        MinResult minResult = new MinResult();
        System.out.println("Min elem "+arrClass.partMin(0,dim).min+" with id "+arrClass.partMin(0,dim).id);

        minResult = arrClass.threadMin();
        System.out.println("Min elem "+minResult.min+" with id "+minResult.id);
    }
}