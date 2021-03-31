package example;

import boot.annotation.start.ComponentScan;
import boot.core.ApplicationContext;
import boot.core.store.UrlAndMethodMapping;

import java.util.*;


@ComponentScan(value = {"example"})
public class MiniBootApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = ApplicationContext.getContext();
        applicationContext.run(MiniBootApplication.class);
//        MiniBootApplication miniBootApplication = new MiniBootApplication();
//        int[] arr = {1,2,3};
    }


    public static int getNumberOfBacklogOrders(int[][] orders) {
        PriorityQueue<int[]> sQueue = new PriorityQueue<>();

        PriorityQueue<int[]> bQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
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

    int[] heap;

    public void init(int capacity) {
        heap = new int[capacity];
    }

    public void add(int t) {

    }

    public void swap(int[] arr, int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    public void heapSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            heapInsert(arr,i);
        }
        int size = arr.length;
        swap(arr, 0, --size);
        while (size > 0) {
            heapify(arr, 0, size);
            swap(arr, 0, --size);
        }

    }

    public void heapify(int[] arr, int index, int size) {
        int left = index * 2 + 1;
        while (left < size) {
            int large = left + 1 < size && arr[left + 1] > arr[left] ? left + 1 : left;
            large = arr[large] > arr[index] ? large : index;
            if (large == index) {
                break;
            }
            swap(arr,large,index);
            index = large;
            left = index * 2 + 1;
        }
    }

    public void heapInsert(int[] arr, int index) {
        while (arr[index] > arr[(index - 1) / 2]) {
            swap(arr,index,(index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

}
