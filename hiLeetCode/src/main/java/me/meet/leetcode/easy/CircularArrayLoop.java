package me.meet.leetcode.easy;

import java.util.HashMap;

public final class CircularArrayLoop {
    /**
     * Circular Array Loop
     * You are given a circular array nums of positive and negative integers. If a number k at an index is positive, then move forward k steps. Conversely, if it's negative (-k), move backward k steps. Since the array is circular, you may assume that the last element's next element is the first element, and the first element's previous element is the last element.
     * Determine if there is a loop (or a cycle) in nums. A cycle must start and end at the same index and the cycle's length > 1. Furthermore, movements in a cycle must all follow a single direction. In other words, a cycle must not consist of both forward and backward movements.
     *
     * Example 1:
     * Input: [2,-1,1,2,2]
     * Output: true
     * Explanation: There is a cycle, from index 0 -> 2 -> 3 -> 0. The cycle's length is 3.
     *
     * Example 2:
     * Input: [-1,2]
     * Output: false
     * Explanation: The movement from index 1 -> 1 -> 1 ... is not a cycle, because the cycle's length is 1. By definition the cycle's length must be greater than 1.
     *
     * Example 3:
     * Input: [-2,1,-1,-2,-2]
     * Output: false
     * Explanation: The movement from index 1 -> 2 -> 1 -> ... is not a cycle, because movement from index 1 -> 2 is a forward movement, but movement from index 2 -> 1 is a backward movement. All movements in a cycle must follow a single direction.
     *
     * Note:
     * -1000 ≤ nums[i] ≤ 1000
     * nums[i] ≠ 0
     * 1 ≤ nums.length ≤ 5000
     *
     * Follow up:
     * Could you solve it in O(n) time complexity and O(1) extra space complexity?
     */

    /**
     * 题意：环形数组循环
     * 思路：说实话，这道题描述的并不是很清楚，比如题目中有句话说循环必须是 forward 或是 backward 的，如果不给例子说明，不太容易能 get 到 point。所谓的循环必须是一个方向的就是说不能跳到一个数，再反方向跳回来，这不算一个 loop。比如 [1, -1] 就不是一个 loop，而 [1, 1] 是一个正确的 loop。看到论坛中一半的帖子都是各种需要 clarify 和不理解 test case 就感觉很好笑～ 当然博主也成功踩坑了。
     *
     * 弄清楚了题意后来考虑如何做，由于从一个位置只能跳到一个别的位置，而不是像图那样一个点可以到多个位置，所以这里我们就可以用个 HashMap 根据坐标建立一对一的映射，一旦某个达到的坐标已经有映射了，说明环存在，当然我们还需要进行一系列条件判断。
     * 首先我们需要一个 visited 数组，来记录访问过的数字，然后我们遍历原数组，如果当前数字已经访问过了，直接跳过，否则就以当前位置坐标为起始点开始查找，进行 while 循环，计算下一个位置，计算方法是当前位置坐标加上对应的数字，由于是循环数组，所以结果可能会超出数组的长度，所以我们要对数组长度取余。
     * 当然上面的数字也可能是负数，加完以后可能也是负数，所以在取余之前还得再补上一个n，使其变为正数，但是 若这个负数远大于n的话，取余之前只加上一个n，可能是不够的，所以正确的方法是应该先对n取余，再加上n。为了同时把正数的情况也包含进来，最终我们的处理方法是先对n取余，再加上n，再对n取余，这样不管正数还是负数，大小如何，都可以成功的旋转跳跃了。
     * 此时我们判断，如果 next 和 cur 相等，说明此时是一个数字的循环，不符合题意，再有就是检查二者的方向，数字是正数表示 forward，若是负数表示 backward，在一个 loop 中必须同正或同负，我们只要让二者相乘，如果结果是负数的话，说明方向不同，直接 break 掉。
     * 此时如果 next 已经有映射了，说明我们找到了合法的 loop，返回 true，否则建立一个这样的映射，将 next 位置在 visited 数组中标记 true，继续循环
     */

    static boolean circularArrayLoop(int[] arr) {
        int n = arr.length;
        boolean[] visited = new boolean[n];
        for (int i = 0; i < n; ++i) {
            if (visited[i]) continue;
            visited[i] = true;
            HashMap<Integer, Integer> m = new HashMap<>();
            int cur = i;
            while (true) {
                int next = ((cur + arr[cur]) % n + n) % n;
                if (next == cur || arr[next] * arr[cur] < 0) break;
                if (null != m.get(next)) return true;
                m.put(cur, next);
                cur = next;
                visited[next] = true;
            }
        }
        return false;
    }

    /**
     * 思路：
     * 我们也可以不用 visited 数组，直接在 nums 中标记，由于题目中说了 nums 中不会有0，所以可以把访问过的位置标记为0。然后计算 next 位置，
     * 对于会超出数组的长度的正数，我们可以通过对n取余，但是对于负数，若这个负数远大于n的话，取余之前只加上一个n，可能是不够的，所以正确的方法是应该先对n取余，再加上n。为了同时把正数的情况也包含进来，最终我们的处理方法是先对n取余，再加上n，再对n取余，这样不管正数还是负数，大小如何，都可以成功的旋转跳跃了。
     *
     * 接下来看，如果 next 和i相等，直接跳过，因为这表示循环只有一个数字，不合题意。
     * 然后开始循环，当 cur 和 nums[next] 的乘积为正时，说明此时是一个方向的，我们将 cur 赋值为 nums[next]，将 nums[next] 赋值为0，表示已经访问过，然后再计算新的 next 位置。直到循环被打破，
     * 若此时 next 和i相等，说明有大于1个数字的环存在，返回 true。
     * 最终 for 循环退出后，返回 false 即可， 代码参见评论区11楼。想法倒是不错，但是存在一个逻辑上的错误。
     * 对于 [1, 1, 2] 这个例子，当 i=0 时，最后跳出 while 循环时 next 是等于1的，和 i 不相等，所以没法返回 true。
     *
     * 但这个例子其实是有正确的循环的，是后两个数字1和2可以循环，那为什么我们没 catch 到呢？
     * 因为我们前面的思路默认为循环开始的位置就是i，但明显不一定成立的，就像在链表中找环一样，环的起点可以是任意位置啊，不一定是表头结点啊。
     * 那为啥上面的解法就可以呢？这是因为上面使用的是 HashMap，而且对于每个i，都使用了一个新的 HashMap，跟之前的并没有联系，而且把以i位置为起点，经过的位置都存到了 HashMap 中，这样是可以找出环的。而这里我们直接跟i相比肯定是不对的。
     *
     * 那么既然说到了链表中找环，刷题老司机们大声告诉我该用什么？对了，就是快慢指针了。那么对于每个i位置，慢指针指向i，快指针指向下一个位置，这里调用子函数来计算下一个位置。此时慢指针指向的数要和快指针指向的数正负相同，这个不难理解。并且慢指针指向的数还要跟快指针的下一个位置上的数符号相同，这个原因后面再讲。
     * 上面这两个就是 while 循环的条件，我们直到当快慢指针相遇的时候，就是环出现的时候，但是这里有个坑，即便快慢指针相遇了，也不同立马返回 true，因为题目中说了了环的长度必须大于1，所以我们要用慢指针指向的数和慢指针下一个位置上的数比较，若相同，则说明环的长度为1，此时并不返回 false，而且 break 掉 while 循环。
     *
     * 因为这只能说以i位置开始的链表无符合要求的环而已，后面可能还会出现符合要求的环。但是若二者不相同的话，则已经找到了符合要求的环，直接返回 true。若快慢指针还不相同的，则分别更新，慢指针走一步，快指针走两步。当 while 循环退出后，我们需要标记已经走过的结点，从而提高运算效率，方法就是将慢指针重置为i，再用一个 while 循环，条件是 nums[i] 和 慢指针指的数正负相同，然后计算下一个位置，并且 nums[slow] 标记为0，并且慢指针移动到 next 位置。最终 for 循环退出后，返回 false 即可，
     */
    static boolean circularArrayLoop2(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            if (arr[i] == 0) continue;
            int slow = i, fast = getNext(arr, i), val = arr[i];
            while (val * arr[fast] > 0 && val * arr[getNext(arr, fast)] > 0) {
                if (slow == fast) {
                    if (slow == getNext(arr, slow)) break;
                    return true;
                }
                slow = getNext(arr, slow);
                fast = getNext(arr, getNext(arr, fast));
            }
            slow = i;
            while (val * arr[slow] > 0) {
                int next = getNext(arr, slow);
                arr[slow] = 0;
                slow = next;
            }
        }
        return false;
    }
    static int getNext(int[] arr, int i) {
        int n = arr.length;
        return (((arr[i] + i) % n) + n) % n;
    }


    public static void main(String[] args) {
        int[] arr = new int[]{2, -1, 1, 2, 2};
        boolean res = circularArrayLoop(arr);
        System.out.println(res);

        res = circularArrayLoop2(arr);
        System.out.println(res);

        arr = new int[]{-2,1,-1,-2,-2};
        res = circularArrayLoop2(arr);
        System.out.println(res);

    }
}
