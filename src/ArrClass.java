import java.util.Random;

public class ArrClass {
    private final int dim;
    private final int threadNum;
    public final int[] arr;
    public MinResult id;
    public Random random = new Random();

    public ArrClass(int dim, int threadNum) {
        this.dim = dim;
        arr = new int[dim];
        this.threadNum = threadNum;
        for(int i = 0; i < dim; i++){
            arr[i] = dim - i;
        }
        arr[random.nextInt(dim)] = -1;
    }
    public MinResult partMin(int startIndex, int finishIndex){
        MinResult minResult = new MinResult();
        minResult.min = arr[startIndex];
        minResult.id = startIndex;
        for(int i = startIndex; i < finishIndex; i++){
            if(minResult.min > arr[i]) {
                minResult.min = arr[i];
                minResult.id = i;
            }
        }
        return minResult;
    }
    MinResult min;

    synchronized private MinResult getMin() {
        while (getThreadCount()<threadNum){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return min;
    }

    synchronized public void collectMin(MinResult minElem) {
        //MinResult minResult =new MinResult();
        if (threadCount != 0) {
            if (min.min > minElem.min)
                min = minElem;
        } else {
            min = minElem;
        }
    }

    private int threadCount = 0;
    synchronized public void incThreadCount(){
        threadCount++;
        notify();
    }

    private int getThreadCount() {
        return threadCount;
    }

    public MinResult threadMin(){
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