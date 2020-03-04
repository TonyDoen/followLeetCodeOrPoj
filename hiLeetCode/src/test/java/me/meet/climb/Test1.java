package me.meet.climb;

public class Test1 {

    /**
     * 猴子爬山
     * 有n级台阶，小猴子要么跳1步，要么跳3步，求n级台阶有多少种跳法
     */
    public static int countStep(int n) {
        if (n < 1) {
            return -1;
        }
        int[] f = new int[n + 1];
        f[1] = 1;
        f[2] = 1;
        f[3] = 2;
        for (int k = 4; k <= n; k++)
            f[k] = f[k - 1] + f[k - 3];
        return f[n];
    }

    /**
     * 量水问题
     * 有2个无刻度两杯 A, B, 其容积分别是 a 升和 b 升(a > b); 允许量杯从水缸中取水或倒回水缸。量杯可以互相倒。
     * 1. 求 量杯A 得到 c 升(c < b < a) 水 的可能性
     */
    public static boolean minSteps(int a, int b, int c) {
        int r;
        while (true) {
            r = a % b;
            if (r != 0) {
                a = b;
                b = r;
            } else {
                break;
            }
        }
        return c % b == 0;
    }

    /**
     * POJ 3414 Pots  数组模拟bfs
     * 量水问题
     * 有2个无刻度两杯 A, B, 其容积分别是 a 升和 b 升(a > b); 允许量杯从水缸中取水或倒回水缸。量杯可以互相倒。
     * 2. 求 量杯A 得到 c 升(c < b < a) 水 最少步数
     *
     *
     * 题意：倒水问题。给你两个标有容量的空杯子，求最少需要多少步能够倒出含指定容量的水。并输出倒水步骤（升级版）。
     * 思路：看到最少步数和输出具体步骤就应该想到BFS。用二维数组b来记录两个杯子的容量。分六步，倒满1、倒满2、倒空1、倒空2、1倒入2、2倒入1。倒入水时要保证不会溢出，所以倒入要分全部倒入和部分倒入两种情况。步骤较多，所以代码啰嗦些。。
     */
    public static int countMinSteps() {

        bfs(3, 2, 1);

        return 0;
    }

    private static void bfs(int a, int b, int c) { //容器大小
        int maxn = 1100;
        int vis[][] = new int[maxn][maxn]; //标记状态是否入队过
//        int a,b,c; //容器大小
        int step = 0; //最终的步数
        int flag = 0; //纪录是否能够成功
        int id[] = new int[maxn * maxn]; //纪录最终操作在队列中的编号
        int lastIndex = 0; //最后一个的编号
        Status[] q = new Status[maxn * maxn];
        for (int i = 0; i < maxn * maxn; i++) {
            q[i] = new Status();
        }

        Status now = new Status(), next = new Status();

        int head, tail;
        head = tail = 0;

        q[tail].k1 = 0;
        q[tail].k2 = 0;
        q[tail].op = 0;
        q[tail].step = 0;
        q[tail].pre = 0;

        tail++;

//        memset(vis,0,sizeof(vis));
        vis[0][0] = 1; //标记初始状态已入队

        while (head < tail) //当队列非空
        {
            now = q[head]; //取出队首
            head++; //弹出队首

            if (now.k1 == c || now.k2 == c) //应该不会存在这样的情况, c=0
            {
                flag = 1;
                step = now.step;
                lastIndex = head - 1; //纪录最后一步的编号
            }

            for (int i = 1; i <= 6; i++) //分别遍历 6 种情况
            {
                if (i == 1) //fill(1)
                {
                    next.k1 = a;
                    next.k2 = now.k2;
                } else if (i == 2) //fill(2)
                {
                    next.k1 = now.k1;
                    next.k2 = b;
                } else if (i == 3) //drop(1)
                {
                    next.k1 = 0;
                    next.k2 = now.k2;
                } else if (i == 4) // drop(2);
                {
                    next.k1 = now.k1;
                    next.k2 = 0;
                } else if (i == 5) //pour(1,2)
                {
                    if (now.k1 + now.k2 <= b) //如果不能够装满 b
                    {
                        next.k1 = 0;
                        next.k2 = now.k1 + now.k2;
                    } else //如果能够装满 b
                    {
                        next.k1 = now.k1 + now.k2 - b;
                        next.k2 = b;
                    }
                } else if (i == 6) // pour(2,1)
                {
                    if (now.k1 + now.k2 <= a) //如果不能够装满 a
                    {
                        next.k1 = now.k1 + now.k2;
                        next.k2 = 0;
                    } else //如果能够装满 b
                    {
                        next.k1 = a;
                        next.k2 = now.k1 + now.k2 - a;
                    }
                }

                next.op = i; //纪录操作
                if (0 != vis[next.k1][next.k2]) //如果当前状态没有入队过
                {
                    vis[next.k1][next.k2] = 1; //标记当前状态入队
                    next.step = now.step + 1; //步数 +1
                    next.pre = head - 1; //纪录前一步的编号

                    //q.push(next);
                    //q[tail] = next; 加入队尾
                    q[tail].k1 = next.k1;
                    q[tail].k2 = next.k2;
                    q[tail].op = next.op;
                    q[tail].step = next.step;
                    q[tail].pre = next.pre;
                    tail++; //队尾延长

                    if (next.k1 == c || next.k2 == c) //如果达到目标状态
                    {
                        flag = 1; //标记成功
                        step = next.step; //纪录总步骤数
                        lastIndex = tail - 1; //纪录最后一步在模拟数组中的编号
                        return;
                    }
                }
            }
        }

        System.out.println(flag);
        System.out.println(step);
        System.out.println(lastIndex);
    }

    private static class Status {
        int k1, k2; //当前水的状态
        int op; //当前操作
        int step; //纪录步数
        int pre; //纪录前一步的下标
    }

    public static void main(String[] args) {

//        int steps = countStep(50);
//        System.out.println(steps);
//
//        boolean tf = minSteps(3, 2, 1);
//        System.out.println(tf);

        bfs(3, 2, 1);
    }
}
