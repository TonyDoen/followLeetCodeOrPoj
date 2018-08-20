package me.meet.leetcode.medium;

public final class GasStation {
    private GasStation() {}
    /**
     * Gas Station
     * There are N gas stations along a circular route, where the amount of gas at station i is gas[i].
     * You have a car with an unlimited gas tank and it costs cost[i] of gas to travel from station i to its next station (i+1). You begin the journey with an empty tank at one of the gas stations.
     * Return the starting gas station's index if you can travel around the circuit once, otherwise return -1.
     *
     * Note:
     * The solution is guaranteed to be unique.
     */
    /**
     * 加油站问题
     * 
     * 题意：
     * 在一条环路上有 N 个加油站，其中第 i 个加油站有汽油 gas[i] 升。
     * 你有一辆油箱容量无限的的汽车，从第 i 个加油站开往第 i+1 个加油站需要消耗汽油 cost[i] 升。你从其中的一个加油站出发，开始时油箱为空。
     * 如果你可以绕环路行驶一周，则返回出发时加油站的编号，否则返回 -1。
     * 说明: 
     * 如果题目有解，该答案即为唯一答案。
     * 输入数组均为非空数组，且长度相同。
     * 输入数组中的元素均为非负数。 
     *
     *
     * 思路：
     * 一次遍历法，车能开完全程需要满足两个条件：
     * 1、车从i站能开到i+1。
     * 2、所有站里的油总量要>=车子的总耗油量。
     * 那么，假设从编号为0站开始，一直到k站都正常，在开往k+1站时车子没油了。这时，应该将起点设置为k+1站。
     * 问题1: 为什么应该将起始站点设为k+1？
     * 因为k->k+1站耗油太大，0->k站剩余油量都是不为负的，每减少一站，就少了一些剩余油量。
     * 所以如果从k前面的站点作为起始站，剩余油量不可能冲过k+1站。
     * 
     * 问题2: 为什么如果k+1->end全部可以正常通行，且rest>=0就可以说明车子从k+1站点出发可以开完全程？
     * 因为，起始点将当前路径分为A、B两部分。其中，必然有(1)A部分剩余油量<0。(2)B部分剩余油量>0。
     * 所以，无论多少个站，都可以抽象为两个站点（A、B）。
     * (1)从B站加满油出发，
     * (2)开往A站，车加油，
     * (3)再开回B站的过程。
     * 重点：B剩余的油>=A缺少的总油。必然可以推出，B剩余的油>=A站点的每个子站点缺少的油。
     *
     */
    static int canCompleteCircuit(int[] gas, int[] cost) {
        int rest = 0, run = 0, start = 0, length = gas.length;
        for (int i = 0; i < length; ++i){
            run += (gas[i] - cost[i]);
            rest += (gas[i] - cost[i]);
            if (run < 0){
                start = i + 1;
                run = 0;
            }
        }
        return rest < 0 ? -1: start;
    }

    private static void testCanCompleteCircuit() {
        int[] gas = new int[]{1, 5, 4};
        int[] cost = new int[]{3, 3, 4};
        int res = canCompleteCircuit(gas, cost);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testCanCompleteCircuit();
    }
}
