package example;

import boot.annotation.start.ComponentScan;
import boot.core.ApplicationContext;

import java.util.Comparator;
import java.util.PriorityQueue;


@ComponentScan(value = {"example"})
public class MiniBootApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = ApplicationContext.getContext();
        applicationContext.run(MiniBootApplication.class);
        int[][] arr = {{10,5,0},{15,2,1},{25,1,1},{30,4,0}};
        System.out.println(getNumberOfBacklogOrders(arr));
    }


    public static int getNumberOfBacklogOrders(int[][] orders) {
        PriorityQueue<int[]> sQueue = new PriorityQueue<>();

        PriorityQueue<int[]> bQueue = new PriorityQueue<>(new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return b[0] - a[0];
            }
        });

        for (int[] o : orders) {
            if (o[2] == 0) { // buy
                while (!sQueue.isEmpty() && sQueue.peek()[0] <= o[0] && o[1] != 0) {
                    int[] sell = sQueue.poll();
                    if (o[1] >= sell[1]) {
                        o[1] -= sell[1];
                    } else {
                        sell[1] -= o[1];
                        o[1] = 0;
                        sQueue.add(sell);
                    }
                }
                if (o[1] != 0) {
                    bQueue.add(o);
                }
            } else { // sell
                while (!bQueue.isEmpty() && bQueue.peek()[0] >= o[0] && o[1] != 0) {
                    int[] buy = bQueue.poll();
                    if (o[1] >= buy[1]) {
                        o[1] -= buy[1];
                    } else {
                        buy[1] -= o[1];
                        o[1] = 0;
                        bQueue.add(buy);
                    }
                }
                if (o[1] != 0) {
                    sQueue.add(o);
                }
            }
        }
        int mod = 1000000009;
        int res = 0;

        while (!sQueue.isEmpty()) {
            int[] cur = sQueue.poll();
            res += (cur[1] % mod);
            res = res % mod;
        }
        while (!bQueue.isEmpty()) {
            int[] cur = bQueue.poll();
            res += (cur[1] % mod);
            res = res % mod;
        }
        return res;
    }
}
