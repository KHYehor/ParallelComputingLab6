package lab6;
import java.util.concurrent.*;


public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        /**
         * Variant20
         * O = SORT(P) * SORT(MR * MS);
         */
        int defaultSize = 200;
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        Vector P = new Vector("./task1/P.txt").initWithRandomValues();
        Matrix MR = new Matrix("./task1/MR.txt").initWithRandomValues();
        Matrix MS = new Matrix("./task1/MS.txt").initWithRandomValues();
        // 1. SORT(P)
        pool.invoke(new VectorParallel("sort", 0, defaultSize, P, null));
        // 2. MR*MS
        pool.invoke(new MatrixParallel("mul", 0, defaultSize, MR, MS));
        // 3. SORT(MR*MS)
        pool.invoke(new MatrixParallel("sort", 0, defaultSize, MR, null));
        // 4. SORT(P) * SORT(MR*MS)
        pool.invoke(new VectorParallel("mul", 0, defaultSize, P, MR));
        P.printResult().saveFinalResult("./task1/O.txt");
        /**
         * Variant20
         * MG = MB * MS + MC * (MR + MM);
         */
        Matrix MB = new Matrix("./task2/MB.txt").initWithRandomValues();
        MS = new Matrix("./task2/MS.txt").initWithRandomValues();
        Matrix MC = new Matrix("./task2/MC.txt").initWithRandomValues();
        MR = new Matrix("./task2/MR.txt").initWithRandomValues();
        Matrix MM = new Matrix("./task2/MM.txt").initWithRandomValues();
        // 1. MB * MS + put it into queue
        pool.invoke(new MatrixParallel("mul", 0, defaultSize, MB, MS));
        // 2. MR + MM
        pool.invoke(new MatrixParallel("sum", 0, defaultSize, MR, MM));
        // 3. MC * (MR + MM)
        pool.invoke(new MatrixParallel("mul", 0, defaultSize, MR, MC));
        // 4. (MB * MS) + (MC * (MR + MM))
        pool.invoke(new MatrixParallel("mul", 0, defaultSize, MR, MB));
        MR.printResult().saveFinalResult("./task2/MG.txt");
    }
}
