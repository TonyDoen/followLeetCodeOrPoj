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
     *
     */
}
