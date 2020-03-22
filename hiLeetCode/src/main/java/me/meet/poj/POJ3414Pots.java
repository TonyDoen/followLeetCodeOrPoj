package me.meet.poj;

import java.util.LinkedList;

public class POJ3414Pots {
    private static class Node {
        int a, b, step, pre, cmd, pos;

        Node() {
        }

        Node(int a, int b, int step, int pre, int cmd, int pos) {
            this.a = a;      // 两个容积分别为 a 和 b 的pot
            this.b = b;
            this.step = step;// step 代表前一个他的行动ID 1:FILL(1); 2:FILL(2); 3:DROP(1); 4:DROP(2); 5:POUR(2,1); 6:POUR(1,2)
            this.pre = pre;  // pre  代表前一个
            this.cmd = cmd;  // cmd  代表他的行动 1:FILL(1); 2:FILL(2); 3:DROP(1); 4:DROP(2); 5:POUR(2,1); 6:POUR(1,2)
            this.pos = pos;  // pos  代表最短几步
        }
    }

    /**
     * url: https://blog.csdn.net/qq_34374664/article/details/53170539
     *
     * POJ 3414 Pots
     *
     * Description
     * You are given two pots, having the volume of A and B liters respectively. The following operations can be performed:
     * 1. FILL(i)        fill the pot i (1 ≤ i ≤ 2) from the tap;
     * 2. DROP(i)        empty the pot i to the drain;
     * 3. POUR(i,j)      pour from pot i to pot j; after this operation either the pot j is full (and there may be some water left in the pot i), or the pot i is empty (and all its contents have been moved to the pot j).
     * Write a program to find the shortest possible sequence of these operations that will yield exactly C liters of water in one of the pots.
     *
     * Input
     * On the first and only line are the numbers A, B, and C. These are all integers in the range from 1 to 100 and C≤max(A,B).
     *
     * Output
     * The first line of the output must contain the length of the sequence of operations K. The following K lines must each describe one operation. If there are several sequences of minimal length, output any one of them. If the desired result can’t be achieved, the first and only line of the file must contain the word ‘impossible’.
     *
     * Sample Input
     * 3 5 4
     *
     * Sample Output
     * 6
     *
     * FILL(2)
     * POUR(2,1)
     * DROP(1)
     * POUR(2,1)
     * FILL(2)
     * POUR(2,1)
     *
     * Source
     * Northeastern Europe 2002, Western Subregion
     *
     */
    /**
     * 题意:给出两个容积分别为 a 和 b 的pot,按照以下三种操作方式,求出能否在一定步数后,使者两个pot的其中一个的水量为c。
     * 1.FILL(i):   将 i pot倒满水。
     * 2.DROP(i):   将 i pot倒空水。
     * 3.POUR(i,j): 将 i pot的水倒到 j pot上,直至要么 i pot为空,要么 j pot为满。
     *
     * 思路:
     * 6入口的bfs
     * 把两个两个桶的某个同一时间的状态看做一个整体,视为初态
     * 可对初态进行六种操作,即FILL(1),FILL(2),DROP(1),DROP(2),POUR(1,2),POUR(2,1)
     * 这6种操作在我的程序中分别用一个整数进行记录;
     * 1z0: 清空z瓶子
     * 2z0: 装满z瓶子
     * 3xy: 从x瓶倒向y瓶 （x,y,z∈{1,2}）
     *
     * 这样在输出操作时就能根据数字的特点 进行选择性输出 了
     *
     * eg:
     * 设1瓶的容量为v1,残余水量为k1,
     * 2瓶的容量为v2,残余水量为 k2
     * 那么
     * fill(1) 相当于 k1=v1
     * fill(2) 相当于 k2=v2
     * drop(1) 相当于 k1=0
     * drop(2) 相当于 k2=0
     * pour(1,2),若  k1+k2<=v2,     则 k2=k1+k2, k1=0  （有先后顺序）
     *           否则 k1=k1+k2-v2,     k2=v2            （有先后顺序）
     * pour(1,1) 同理
     *
     *
     * 难点
     * 1. 输出得到容量C的过程。
     * 解决方法：对于每一步的操作，我们都得对它设置一个pos指针，使它指向前一步操作在queue[]队列的下标，即对下标进行回溯，再顺序输出每一步操作
     *
     * 2. 状态记录
     * 怎样记录两个瓶子在同一时间点的状态，又能使得搜索标记时为O(1)的复杂度
     *
     */
    static void bfs(int a, int b, int c) {
        LinkedList<Node> queue = new LinkedList<>();
        queue.push(new Node(0, 0, -1, -1, -1, 0));

        Node[] arr = new Node[1005 * 1005];
        int flag = 0, k = 1, book[][] = new int[1005][1005];
        book[0][0] = 1;

        Node point = new Node();
        for (; !queue.isEmpty(); ) {
            point = queue.pop();
            if (point.a == c || point.b == c) {
                flag = 1;
                break;
            }
            //这里开始6种操作同时进行
            if (point.a != a && 0 == book[a][point.b]) {
                arr[k] = new Node(a, point.b, k, point.step, 1, point.pos + 1);
                book[a][point.b] = 1;
                queue.push(arr[k]);
                k++;
            }
            if (point.b != b && 0 == book[point.a][b]) {
                arr[k] = new Node(point.a, b, k, point.step, 2, point.pos + 1);
                book[point.a][b] = 1;
                queue.push(arr[k]);
                k++;
            }
            if (0 != point.a && 0 == book[0][point.b]) {
                arr[k] = new Node(0, point.b, k, point.step, 3, point.pos + 1);
                book[0][point.b] = 1;
                queue.push(arr[k]);
                k++;
            }
            if (0 != point.b && 0 == book[point.a][0]) {
                arr[k] = new Node(point.a, 0, k, point.step, 4, point.pos + 1);
                book[point.a][0] = 1;
                queue.push(arr[k]);
                k++;
            }
            if (point.a != a && 0 != point.b) {
                int x = Math.min(a, point.a + point.b);
                int y = Math.max(0, point.b - a + point.a);
                if (0 == book[x][y]) {
                    arr[k] = new Node(x, y, k, point.step, 5, point.pos + 1);
                    queue.push(arr[k]);
                    book[x][y] = 1;
                    k++;
                }

            }
            if (point.b != b && 0 != point.a) {
                int x = Math.min(b, point.a + point.b);
                int y = Math.max(0, point.a - b + point.b);
                if (0 == book[y][x]) {
                    arr[k] = new Node(y, x, k, point.step, 6, point.pos + 1);
                    queue.push(arr[k]);
                    book[y][x] = 1;
                    k++;
                }
            }
        }
        if (0 == flag) {
            System.out.println("impossible");
        } else {
            System.out.println(point.pos);
            LinkedList<Node> s = new LinkedList<>();
            for (; true; ) {
                s.push(point);
                if (-1 == point.pre) {
                    break;
                }
                point = arr[point.pre];
            }

            for (; !s.isEmpty(); ) {
                Node tmp = s.pop();
                int t = tmp.cmd;
                if (t == 1) {
                    System.out.println("FILL(1)");
                } else if (t == 2) {
                    System.out.println("FILL(2)");
                } else if (t == 3) {
                    System.out.println("DROP(1)");
                } else if (t == 4) {
                    System.out.println("DROP(2)");
                } else if (t == 5) {
                    System.out.println("POUR(2,1)");
                } else if (t == 6) {
                    System.out.println("POUR(1,2)");
                }
            }

        }
    }

    private static void testBFS() {
        bfs(3, 5, 4);
        bfs(3, 7, 1);
    }

    public static void main(String[] args) {
        testBFS();
    }
}
