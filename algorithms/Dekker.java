package algorithms;

import java.util.concurrent.Callable;

public class Dekker {
    private volatile int turn;
    private boolean[] flag = new boolean[2];

    public Dekker() {
        this.turn = 0;
    }

    public <T> void lock(int num, Callable<T> call) throws Exception {
        int other = 1 - num;
        flag[num] = true;
        while (flag[other]) {
            flag[num] = false;
            while(turn == other){
                Thread.yield();
            }
            flag[num]  = true;
        }
        call.call();
        flag[num] = false;
        turn = other;
    }

    public void unlock(int num) {
        int other = 1 - num;
        turn = other;
    }
}
