package me.meet.labuladong._2.LCNOT;

public final class _00006 {
    private _00006() {
    }
    /*
     * 二叉堆（Binary Heap）没什么神秘，性质比二叉搜索树 BST 还简单。其主要操作就两个，sink（下沉）和swim（上浮），用以维护二叉堆的性质。
     * 其主要应用有两个，首先是一种排序方法「堆排序」，第二是一种很有用的数据结构「优先级队列」。
     *
     * 本文就以实现优先级队列（Priority Queue）为例，通过图片和人类的语言来描述一下二叉堆怎么运作的。
     *
     * 一、二叉堆概览
     * 首先，二叉堆和二叉树有啥关系呢，为什么人们总数把二叉堆画成一棵二叉树？
     * 因为，二叉堆其实就是一种特殊的二叉树（完全二叉树），只不过存储在数组里。一般的链表二叉树，我们操作节点的指针，而在数组里，我们把数组索引作为指针：
     *
     * // 父节点的索引
     * int parent(int root) {
     *     return root / 2;
     * }
     * // 左孩子的索引
     * int left(int root) {
     *     return root * 2;
     * }
     * // 右孩子的索引
     * int right(int root) {
     *     return root * 2 + 1;
     * }
     *
     * 画个图你立即就能理解了，注意数组的第一个索引 0 空着不用：
     *  i         0    1    2    3    4    5    6    7
     *  arr[i]    *    T    S    R    P    N    O    A
     *
     *                 堆顶
     *                 T
     *                   \    \
     *                      S    R
     *                             \     \    \    \
     *                                P    N    O    A
     *                                堆底
     *
     * PS：因为数组索引是数字，为了方便区分，将字符作为数组元素。
     *
     * 你看到了，把 arr[1] 作为整棵树的根的话，每个节点的父节点和左右孩子的索引都可以通过简单的运算得到，这就是二叉堆设计的一个巧妙之处。
     * 为了方便讲解，下面都会画的图都是二叉树结构，相信你能把树和数组对应起来。
     * 二叉堆还分为最大堆和最小堆。最大堆的性质是：每个节点都大于等于它的两个子节点。类似的，最小堆的性质是：每个节点都小于等于它的子节点。
     * 两种堆核心思路都是一样的，本文以最大堆为例讲解。
     *
     * 对于一个最大堆，根据其性质，显然堆顶，也就是 arr[1] 一定是所有元素中最大的元素。
     *
     *
     *
     *
     * 二、优先级队列概览
     * 优先级队列这种数据结构有一个很有用的功能，你插入或者删除元素的时候，元素会自动排序，这底层的原理就是二叉堆的操作。
     * 数据结构的功能无非增删查该，优先级队列有两个主要 API，分别是insert插入一个元素和delMax删除最大元素（如果底层用最小堆，那么就是delMin）。
     * 下面我们实现一个简化的优先级队列，先看下代码框架：
     * PS：为了清晰起见，这里用到 Java 的泛型，Key可以是任何一种可比较大小的数据类型，你可以认为它是 int、char 等。
     *
     *
     *
     *
     * 三、实现 swim 和 sink
     * 为什么要有上浮 swim 和下沉 sink 的操作呢？为了维护堆结构。
     * 我们要讲的是最大堆，每个节点都比它的两个子节点大，但是在插入元素和删除元素时，难免破坏堆的性质，这就需要通过这两个操作来恢复堆的性质了。
     * 对于最大堆，会破坏堆性质的有有两种情况：
     * 1. 如果某个节点 A 比它的子节点（中的一个）小，那么 A 就不配做父节点，应该下去，下面那个更大的节点上来做父节点，这就是对 A 进行下沉。
     * 2. 如果某个节点 A 比它的父节点大，那么 A 不应该做子节点，应该把父节点换下来，自己去做父节点，这就是对 A 的上浮。
     * 当然，错位的节点 A 可能要上浮（或下沉）很多次，才能到达正确的位置，恢复堆的性质。所以代码中肯定有一个while循环。
     * 上浮的代码实现：
     *
     * 下沉的代码实现：
     * 下沉比上浮略微复杂一点，因为上浮某个节点 A，只需要 A 和其父节点比较大小即可；但是下沉某个节点 A，需要 A 和其两个子节点比较大小，
     * 如果 A 不是最大的就需要调整位置，要把较大的那个子节点和 A 交换。
     *
     * 画个图看下就明白了：
     * 至此，二叉堆的主要操作就讲完了，一点都不难吧，代码加起来也就十行。明白了sink和swim的行为，下面就可以实现优先级队列了。
     *
     *
     *
     *
     * 四、实现 delMax 和 insert
     * 这两个方法就是建立在swim和sink上的。
     * insert方法先把要插入的元素添加到堆底的最后，然后让其上浮到正确位置。
     *
     * 至此，一个优先级队列就实现了，插入和删除元素的时间复杂度为 O(logK)，K为当前二叉堆（优先级队列）中的元素总数。
     * 因为我们时间复杂度主要花费在sink或者swim上，而不管上浮还是下沉，最多也就树（堆）的高度，也就是 log 级别。
     *
     *
     *
     *
     * 五、最后总结
     * 二叉堆就是一种完全二叉树，所以适合存储在数组中，而且二叉堆拥有一些特殊性质。
     * 二叉堆的操作很简单，主要就是上浮和下沉，来维护堆的性质（堆有序），核心代码也就十行。
     * 优先级队列是基于二叉堆实现的，主要操作是插入和删除。插入是先插到最后，然后上浮到正确位置；删除是把第一个元素 pq[1]（最值）调换到最后再删除，
     * 然后把新的 pq[1] 下沉到正确位置。核心代码也就十行。也许这就是数据结构的威力，简单的操作就能实现巧妙的功能，真心佩服发明二叉堆算法的人！
     *
     * PS：本文的动画示例参考自经典书籍《算法第 4 版》，有兴趣的同学在公众号后台回复关键字「算法4」即可下载文字版 PDF。本文对你有帮助的话点个在看分个享吧～
     */
    public static class MaxPriorityQueue<K extends Comparable<K>> {
        // store element
        private K[] pq;
        // current pq's elements' size
        private int n = 0;
        public MaxPriorityQueue(int cap) {
            // index = 0 using for other place
            pq = (K[]) new Comparable[cap+1];
        }

        // return max element in current queue
        public K max() {
            return pq[1]; // careful
        }

        public void insert(K e) {
            n++;
            pq[n] = e;
            swim(n);
        }

        // delete max element in current queue and return it
        public K delMax() {
            K max = pq[1];
            swap(1, n);
            pq[n] = null;
            n--;
            sink(1);
            return max;
        }

        // swim the kth element
        private void swim(int k) {
            // if swim the top of heap
             while (k > 1 && less(parent(k), k)) {
                 // if the kth element is larger than upper level
                 // swap the kth element
                 swap(parent(k), k);
                 k = parent(k);
             }
        }

        // sink the kth element
        private void sink(int k) {
            while (left(k) < n) {
                int older = left(k);
                if (right(k) <= n && less(older, right(k))) {
                    older = right(k);
                }
                if (less(older, k)) {
                    break;
                }
                swap(k, older);
                k = older;
            }
        }

        // exchange two elements in current queue
        private void swap(int i, int j) {
            K tmp = pq[i];
            pq[i] = pq[j];
            pq[j] = tmp;
        }

        // pq[i] < pq[j] ?
        private boolean less(int i, int j) {
            return pq[i].compareTo(pq[j]) < 0;
        }

        private int parent(int k) {
            return (int) Math.floor((double) (k/2));
        }

        private int left(int k) {
            return k - 1;
        }

        private int right(int k) {
            return k + 1;
        }
    }

    public static void main(String[] args) {
        int n = 20;
        MaxPriorityQueue<Integer> mpq = new MaxPriorityQueue<>(n);
        for (int i = 0; i < n; i++) {
            mpq.insert(i);
        }
        System.out.println();
    }
}
