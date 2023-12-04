import algorithms.Dekker;
import algorithms.LamportBakeryAlgorithm;

import java.util.concurrent.Callable;

public class Algorithms {
    public static void main(String[] args) {
        Dekker algo = new Dekker();
        Callable<Integer> call = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("in critical section "+ Thread.currentThread().getId());
                Thread.sleep(10000L);
                return 1;
            }
        };

        Thread t1 = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        algo.lock(0, call);
                        algo.unlock(0);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        Thread t2 = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        algo.lock(1, call);
                        algo.unlock(1);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        t1.start();
        t2.start();

    }
}
