package me.meet.leetcode.easy;

import java.util.HashSet;
import java.util.Set;

public final class WalkingRobotSimulation {
    private WalkingRobotSimulation() {}
    /**
     * A robot on an infinite grid starts at point (0, 0) and faces north.  The robot can receive one of three possible types of commands:
     * -2: turn left 90 degrees
     * -1: turn right 90 degrees
     * 1 <= x <= 9: move forward x units
     * Some of the grid squares are obstacles.
     * The i-th obstacle is at grid point (obstacles[i][0], obstacles[i][1])
     * If the robot would try to move onto them, the robot stays on the previous grid square instead (but still continues following the rest of the route.)
     * Return the square of the maximum Euclidean distance that the robot will be from the origin.
     *
     * Example 1:
     * Input: commands = [4,-1,3], obstacles = []
     * Output: 25
     * Explanation: robot will go to (3, 4)
     *
     * Example 2:
     * Input: commands = [4,-1,4,-2,4], obstacles = [[2,4]]
     * Output: 65
     * Explanation: robot will be stuck at (1, 4) before turning left and going to (1, 8)
     *
     * Note:
     * 0 <= commands.length <= 10000
     * 0 <= obstacles.length <= 10000
     * -30000 <= obstacle[i][0] <= 30000
     * -30000 <= obstacle[i][1] <= 30000
     * The answer is guaranteed to be less than 2 ^ 31.
     */
    /**
     * 题意：走路机器人仿真
     * 这道题说在一个无限大的方格中，原点位置有一个面朝北方的机器人，可以接受三种不同的指令，-2 表示左转 90 度，-1 表示右转 90 度，正数表示沿当前方向前进该正数步，然后给了一个障碍数组，就是机器人无法通过的地方，比如当前机器人面前有个障碍物，此时机器人接到的指令是前进x步，但是由于障碍物的因素，该指令无法执行，机器人呆在原地不动。只有接到转向的命令，才有可能离开当前位置。好，理解了题意之后，来思考如何解题，说到底还是一道迷宫遍历的题目，但是跟以往不同的是，这里我们并不知道迷宫的大小，而且也没有目标点需要到达，唯一需要做的就是执行指令，当指令执行完了之后，搜索也就停止了，需要找出的是机器人能到达的距离原点最远的距离。首先对于障碍物，由于肯定要经常的查找下一个位置是否有障碍物，那么就用一个 HashSet 将所有的障碍物位置存进去，由于坐标是二维的，可以进行降维处理，之前我们都是进行 i*n+j 的降维处理，这里由于不知道迷宫的大小，所以只能换一种方式，可以将横纵坐标都转为字符串，然后在中间加个短横杆隔开，这样就可以把二维坐标变为一个字符串，放到 HashSet 中了。然后就是处理所有的命令了，在传统的迷宫遍历中，使用了方向数组，来控制遍历的方向，这里也是同样需要的，但稍有不同的是，这里方向的顺序也有讲究，因为机器人的初始状态是朝北的，所以方向数组的第一个应该是朝北走，上北下南左西右东，这样方向数组的顺序应该是上右下左。用一个变量 idx 来表示方向数组中的当前坐标，那么当遇到 -1，即右转时，idx 自增1即可，为了防止越界，需要对4取余。同理，当遇到 -2，即左转时，idx 自减1即可，同样为了防止越界，先加4，再对4取余。当遇到正数命令时，此时就该前进了，用两个变量x和y分别表示当前位置的横纵坐标，均初始化为0，分别加上方向数组中对应位置的值，就是下一个位置的坐标了，在经典的迷宫遍历问题中，我们都会检验新位置是否越界，以及是否访问过，而这里不存在越界的问题，访没访问过也无所谓，重要是看有没有障碍物，到 HashSet 中去查找一下，若没有障碍物，则可以到达，更新x和y为新的位置，继续 while 循环即可。当每个命令执行完了之后，都用当前的x和y距离原点的距离更新一个结果 res 即可，参见代码如下：
     */
    static int robotSim(int[] commands, int[][] obstacles) {
        int res = 0, x = 0, y = 0, idx = 0;
        Set<String> obs = new HashSet<>();
        for (int[] a : obstacles) {
            String tmp = a[0] + "-" + a[1];
            obs.add(tmp);
        }
        int[] dirX = new int[]{0, 1, 0, -1};
        int[] dirY = new int[]{1, 0, -1, 0};

        for (int command : commands) {
            if (command == -1) idx = (idx + 1) % 4;
            else if (command == -2) idx = (idx - 1 + 4) % 4;
            else {
                String tmp = String.format("%d-%d", x + dirX[idx], y + dirY[idx]);
                while (command-- > 0 && !obs.contains(tmp)) {
                    x += dirX[idx];
                    y += dirY[idx];
                }
            }
            res = Math.max(res, x * x + y * y);
        }
        return res;
    }

    static int robotSim2(int[] commands, int[][] obstacles) {
        int res = 0, x = 0, y = 0, idx = 0;
        Set<Pair<Integer, Integer>> obs = new HashSet<>();
        for (int[] a : obstacles) {
            obs.add(new Pair<>(a[0], a[1]));
        }
        int[] dirX = new int[]{0, 1, 0, -1};
        int[] dirY = new int[]{1, 0, -1, 0};
        for (int command : commands) {
            if (command == -1) idx = (idx + 1) % 4;
            else if (command == -2) idx = (idx - 1 + 4) % 4;
            else {
                String tmp = String.format("%d-%d", x + dirX[idx], y + dirY[idx]);
                while (command-- > 0 && !obs.contains(tmp)) {
                    x += dirX[idx];
                    y += dirY[idx];
                }
            }
            res = Math.max(res, x * x + y * y);
        }
        return res;
    }

    public static void main(String[] args) {
        int[] commands = new int[]{4,-1,4,-2,4};
        int[][] obstacles = new int[][]{{2, 4}};
        int res1 = robotSim(commands, obstacles);
        int res2 = robotSim2(commands, obstacles);
        System.out.println(res1);
        System.out.println(res2);
    }

    static class Pair<A, B> {
        A a;
        B b;

        Pair(A a, B b) {
            this.a = a;
            this.b = b;
        }
    }
}
