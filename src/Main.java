public class Main {
    static final int size = 10000000;
    static final int h = size / 2;

    public static void main(String[] args) {
        threadMetod1();
        threadMetod2();
    }

    public static void threadMetod1() {
        float[] arr = new float[size];
        for (int i = 0; i < arr.length ; i++) {
            arr[i] = 1;
        }
        long a = System.currentTimeMillis();
        for (int i = 0; i < arr.length ; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("Общее время выполнения 1 метода: "+(System.currentTimeMillis() - a));
        System.out.println("Проверка значения -"+arr[9000000]);
    }

    public static void threadMetod2() {
        float[] arr = new float[size];
        float[] a1 = new float[h];
        float[] a2 = new float[h];

        for (int i = 0; i < arr.length ; i++) {
            arr[i] = 1;
        }
        long a = System.currentTimeMillis();
        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);

        System.out.println("Время разбивки: "+(System.currentTimeMillis() - a));

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                long a = System.currentTimeMillis();
                for (int i = 0; i < a1.length; i++) {
                    a1[i] = (float)(a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
                System.out.println("Время просчета 1: "+(System.currentTimeMillis() - a));
            }
        });


        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                long a = System.currentTimeMillis();
                for (int i = 0; i < a2.length; i++) {
                    a2[i] = (float)(a2[i] * Math.sin(0.2f + (i+h) / 5) * Math.cos(0.2f + (i+h) / 5) * Math.cos(0.4f + (i+h) / 2));
                }
                System.out.println("Время просчета 2: "+(System.currentTimeMillis() - a));
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long b = System.currentTimeMillis();
        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);
        System.out.println("Время склейки: "+(System.currentTimeMillis() - b));

        System.out.println("Общее время общего выполнения метода 2: "+(System.currentTimeMillis() - a));
        System.out.println("Проверка значения - "+arr[9000000]);
    }
}
