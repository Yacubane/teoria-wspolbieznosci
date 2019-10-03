import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        int[] threadNums = new int[]{5, 10, 20, 50, 100, 1000, 10000};
        long[] loopIterations = new long[]{10, 100, 1000, 10000, 100000, 1000000};
        int tryNums = 10;

        for (int threadNum : threadNums) {
            for (long loopIteration : loopIterations) {
                int successTries = 0;
                for (int tryNum = 0; tryNum < tryNums; tryNum++) {
                    if (runTest(threadNum, loopIteration)) {
                        successTries++;
                    }
                }
                double percentage = successTries / (double) tryNums;
                System.out.println("Wynik dla " + threadNum + " wątków i "
                        + loopIteration + " iteracji: "
                        + (percentage*100) + "% sukcesu");
            }
        }
    }

    private static boolean runTest(int threadsNum, long loopIterations) {
        Counter counter = new Counter();
        List<Thread> threadList = new ArrayList<>();

        for (int i = 0; i < threadsNum; i++) {
            threadList.add(new Thread(() -> {
                for (int j = 0; j < loopIterations; j++) {
                    counter.increment();
                }
            }));
            threadList.add(new Thread(() -> {
                for (int j = 0; j < loopIterations; j++) {
                    counter.decrement();
                }
            }));
        }

        threadList.forEach(e -> e.start());
        threadList.forEach(e -> {
            try {
                e.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        //System.out.println("Wynik dla " + threadsNum + " wątków i " + loopIterations + " iteracji: " + counter.getValue());
        return counter.getValue() == 0;
    }
}
