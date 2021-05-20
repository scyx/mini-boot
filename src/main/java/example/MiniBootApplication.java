package example;

import boot.annotation.start.ComponentScan;
import boot.core.ApplicationContext;

import java.io.IOException;
import java.util.Scanner;


@ComponentScan(value = {"example"})
public class MiniBootApplication {
    public static void main(String[] args) throws IOException, IllegalAccessException {
        ApplicationContext applicationContext = ApplicationContext.getContext();
        applicationContext.run(MiniBootApplication.class);

//        MiniBootApplication miniBootApplication = new MiniBootApplication();
//        int[] arr = {0,1,2,3,0};
//        minSideJumps(arr);

//        Socket socket = null;
//        try {
//            ServerSocket serverSocket = new ServerSocket(10086);
//            socket = serverSocket.accept();
//            InputStream inputStream =  socket.getInputStream();
//            OutputStream outputStream = socket.getOutputStream();
//            byte[] bytes = new byte[1024];
//            while (true) {
//                int t = inputStream.read(bytes);
//                if (t != -1) {
//                    String res  = "接收到客户端消息： "+new String(bytes,0,t);
//                    outputStream.write(res.getBytes(StandardCharsets.UTF_8));
//                    outputStream.flush();
//                } else {
//                    break;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            System.out.println("socket close");
//            socket.close();
//        }
        Scanner scanner = new Scanner(System.in);
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
