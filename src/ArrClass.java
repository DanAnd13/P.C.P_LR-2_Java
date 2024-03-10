import java.util.Random;

public class ArrClass {
    private final int dim;
    private final int threadNum;
    public final int[] arr;
    public int id;
    public Random random = new Random();

    public ArrClass(int dim, int threadNum) {
        this.dim = dim;
        arr = new int[dim];
        this.threadNum = threadNum;
        for(int i = 0; i < dim; i++){
            arr[i] = i;
        }
        arr[random.nextInt(dim)] = -1;
    }

    public long partMin(int startIndex, int finishIndex){
        long min = 9_999_999_99;
        for(int i = startIndex; i < finishIndex; i++){
            if(min > arr[i]) {
                min = arr[i];
                id = i;
            }
        }
        return min;
    }

    private long min = 0;

    synchronized private long getMin() {
        while (getThreadCount()<threadNum){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return min;
    }

    synchronized public void collectMin(long minElem){
        if(min > minElem)
            min = minElem;
    }

    private int threadCount = 0;
    synchronized public void incThreadCount(){
        threadCount++;
        notify();
    }

    private int getThreadCount() {
        return threadCount;
    }

    public long threadMin(){
        ThreadMin[] threadMin = new ThreadMin[threadNum];
        int partSize = dim / threadNum;

        for(int i = 0; i < threadNum; i++){
            int startIndex = i * partSize;
            int endIndex = (i == threadNum - 1) ? dim : (i + 1) * partSize;
            threadMin[i] = new ThreadMin(startIndex, endIndex, this);
            threadMin[i].start();
        }

        return getMin();
    }
}