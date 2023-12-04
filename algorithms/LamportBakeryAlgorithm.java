package algorithms;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class LamportBakeryAlgorithm {
    private int n;
    private final AtomicIntegerArray tickets;
    private final AtomicIntegerArray entering;

    public LamportBakeryAlgorithm(int n) {
        this.n = n;
        this.tickets = new AtomicIntegerArray(n);
        this.entering = new AtomicIntegerArray(n);
    }

    private int getMaxTicket() {
        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, tickets.get(i));
        }
        return ans;
    }

    public <T> void lock(int num, Callable<T> call) throws Exception {
        entering.set(num, 1);
        int myTicket = getMaxTicket() + 1;
        tickets.set(num, myTicket);
        entering.set(num, 0);

        for (int i = 0; i < n; i++) {
            if (i != num) {
                while (entering.get(i) == 1) {
                    Thread.yield();
                }
                while (tickets.get(i) != 0 && ((tickets.get(i) < tickets.get(num)) || (tickets.get(i) == tickets.get(num) && i < num))) {
                    Thread.yield();
                }
            }
        }
        call.call();
    }

    public void unlock(int num) {
        tickets.set(num, 0);
    }
}
