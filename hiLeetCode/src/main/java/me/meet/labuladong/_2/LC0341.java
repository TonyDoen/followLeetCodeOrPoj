package me.meet.labuladong._2;

public final class LC0341 {
    private LC0341() {
    }

    /**
     * LeetCode 第 341 题「扁平化嵌套列表迭代器」
     * 混合了算法和数据结构的设计，很有意思。
     *
     * 首先，现在有一种数据结构NestedInteger，这个结构中存的数据可能是一个Integer整数，也可能是一个NestedInteger列表。
     * 注意，这个列表里面装着的是NestedInteger，也就是说这个列表中的每一个元素可能是个整数，可能又是个列表，这样无限递归嵌套下去……
     *
     * NestedInteger有如下 API：
     * public class NestedInteger {
     *     // 如果其中存的是一个整数，则返回 true，否则返回 false
     *     public boolean isInteger();
     *
     *     // 如果其中存的是一个整数，则返回这个整数，否则返回 null
     *     public Integer getInteger();
     *
     *     // 如果其中存的是一个列表，则返回这个列表，否则返回 null
     *     public List<NestedInteger> getList();
     * }
     *
     * 我们的算法会被输入一个NestedInteger列表，我们需要做的就是写一个迭代器类，将这个带有嵌套结构NestedInteger的列表「拍平」：
     * public class NestedIterator implements Iterator<Integer> {
     *     // 构造器输入一个 NestedInteger 列表
     *     public NestedIterator(List<NestedInteger> nestedList) {}
     *
     *     // 返回下一个整数
     *     public Integer next() {}
     *
     *     // 是否还有下一个整数？
     *     public boolean hasNext() {}
     * }
     *
     * 我们写的这个类会被这样调用，先调用hasNext方法，后调用next方法：
     * NestedIterator i = new NestedIterator(nestedList);
     * while (i.hasNext())
     *     print(i.next());
     *
     *
     * 例子1：
     * input:  [[1,1],2,[1,1]]
     * output: [1,1,2,1,1]
     * explain:通过重复调用 next 直到 hasNext 返回 false, next 返回的元素的顺序应该是： [1,1,2,1,1]
     *
     * 例子2：
     * input:  [1,[4,[6]]]
     * output: [1,4,6]
     * explain:通过重复调用 next 直到 hasNext 返回 false, next 返回的元素的顺序应该是： [1,4,6]
     *
     *
     * 比如示例 1，输入的列表里有三个NestedInteger，两个列表型的NestedInteger和一个整数型的NestedInteger。
     * 学过设计模式的朋友应该知道，迭代器也是设计模式的一种，目的就是为调用者屏蔽底层数据结构的细节，简单地通过hasNext和next方法有序地进行遍历。
     * 为什么说这个题目很有启发性呢？因为我最近在用一款类似印象笔记的软件，叫做 Notion（挺有名的）。
     * 这个软件的一个亮点就是「万物皆 block」，block 其实就是一种数据结构，比如说标题、页面、表格都是 block。
     * 有的 block 甚至可以无限嵌套，这就打破了传统笔记本「文件夹」->「笔记本」->「笔记」的三层结构。
     *
     * 回想这个算法问题，NestedInteger结构实际上也是一种支持无限嵌套的结构，而且可以同时表示整数和列表两种不同类型，我想 Notion 的核心数据结构 block 估计也是这样的一种设计思路。
     * 那么话说回来，对于这个算法问题，我们怎么解决呢？NestedInteger结构可以无限嵌套，怎么把这个结构「打平」，为迭代器的调用者屏蔽底层细节，扁平化地输出所有整数元素呢？
     *
     *
     *
     * 二、解题思路
     * 显然，NestedInteger这个神奇的数据结构是问题的关键，不过题目专门提醒我们：
     * You should not implement it, or speculate about its implementation.
     *
     * 你看，labuladong 可不是什么好孩子，你不让推测，我就偏偏要去推测！我反手就把NestedInteger这个结构给实现出来：
     * public class NestedInteger {
     *     private Integer val;
     *     private List<NestedInteger> list;
     *
     *     public NestedInteger(Integer val) {
     *         this.val = val;
     *         this.list = null;
     *     }
     *     public NestedInteger(List<NestedInteger> list) {
     *         this.list = list;
     *         this.val = null;
     *     }
     *
     *     // 如果其中存的是一个整数，则返回 true，否则返回 false
     *     public boolean isInteger() {
     *         return val != null;
     *     }
     *
     *     // 如果其中存的是一个整数，则返回这个整数，否则返回 null
     *     public Integer getInteger() {
     *         return this.val;
     *     }
     *
     *     // 如果其中存的是一个列表，则返回这个列表，否则返回 null
     *     public List<NestedInteger> getList() {
     *         return this.list;
     *     }
     * }
     *
     * 嗯，其实这个实现也不难嘛，写出来之后，我不禁翻出前文 学习数据结构和算法的框架思维，发现这玩意儿竟然……
     * class NestedInteger {
     *     Integer val;
     *     List<NestedInteger> list;
     * }
     *
     * // 基本的 N 叉树节点
     * class TreeNode {
     *     int val;
     *     TreeNode[] children;
     * }
     *
     * 这玩意儿不就是棵 N 叉树吗？叶子节点是Integer类型，其val字段非空；
     * 其他节点都是List<NestedInteger>类型，其val字段为空，但是list字段非空，装着孩子节点。
     *
     * 比如说输入是[[1,1],2,[1,1]]，其实就是如下树状结构：
     * *  列表类型                   ^   虚拟根结点
     * -  整数类型                /  |  \
     *                         *  - 2   *
     *                       /  \      /   \
     *                     - 1  - 1  - 1   - 1
     *
     * 好的，刚才题目说什么来着？把一个NestedInteger扁平化对吧？这不就等价于遍历一棵 N 叉树的所有「叶子节点」吗？
     * 我把所有叶子节点都拿出来，不就可以作为迭代器进行遍历了吗？
     * N 叉树的遍历怎么整？我又不禁翻出前文 学习数据结构和算法的框架思维 找出框架：
     * void traverse(TreeNode root) {
     *     for (TreeNode child : root.children)
     *         traverse(child);
     *
     * 这个框架可以遍历所有节点，而我们只对整数型的NestedInteger感兴趣，也就是我们只想要「叶子节点」，所以traverse函数只要在到达叶子节点的时候把val加入结果列表即可：
     * class NestedIterator implements Iterator<Integer> {
     *
     *     private Iterator<Integer> it;
     *
     *     public NestedIterator(List<NestedInteger> nestedList) {
     *         // 存放将 nestedList 打平的结果
     *         List<Integer> result = new LinkedList<>();
     *         for (NestedInteger node : nestedList) {
     *             // 以每个节点为根遍历
     *             traverse(node, result);
     *         }
     *         // 得到 result 列表的迭代器
     *         this.it = result.iterator();
     *     }
     *
     *     public Integer next() {
     *         return it.next();
     *     }
     *
     *     public boolean hasNext() {
     *         return it.hasNext();
     *     }
     *
     *     // 遍历以 root 为根的多叉树，将叶子节点的值加入 result 列表
     *     private void traverse(NestedInteger root, List<Integer> result) {
     *         if (root.isInteger()) {
     *             // 到达叶子节点
     *             result.add(root.getInteger());
     *             return;
     *         }
     *         // 遍历框架
     *         for (NestedInteger child : root.getList()) {
     *             traverse(child, result);
     *         }
     *     }
     * }
     *
     * 这样，我们就把原问题巧妙转化成了一个 N 叉树的遍历问题，并且得到了解法。
     *
     *
     *
     *
     * 三、进阶思路
     * 以上解法虽然可以通过，但是在面试中，也许是有瑕疵的。
     *
     * 我们的解法中，一次性算出了所有叶子节点的值，全部装到result列表，也就是内存中，next和hasNext方法只是在对result列表做迭代。
     * 如果输入的规模非常大，构造函数中的计算就会很慢，而且很占用内存。
     *
     * 一般的迭代器求值应该是「惰性的」，也就是说，如果你要一个结果，我就算一个（或是一小部分）结果出来，而不是一次把所有结果都算出来。
     * 如果想做到这一点，使用递归函数进行 DFS 遍历肯定是不行的，而且我们其实只关心「叶子节点」，所以传统的 BFS 算法也不行。实际的思路很简单：
     * 调用hasNext时，如果nestedList的第一个元素是列表类型，则不断展开这个元素，直到第一个元素是整数类型。
     * 由于调用next方法之前一定会调用hasNext方法，这就可以保证每次调用next方法的时候第一个元素是整数型，直接返回并删除第一个元素即可。
     *
     * 看一下代码：
     * public class NestedIterator implements Iterator<Integer> {
     *     private LinkedList<NestedInteger> list;
     *
     *     public NestedIterator(List<NestedInteger> nestedList) {
     *         // 不直接用 nestedList 的引用，是因为不能确定它的底层实现
     *         // 必须保证是 LinkedList，否则下面的 addFirst 会很低效
     *         list = new LinkedList<>(nestedList);
     *     }
     *
     *     public Integer next() {
     *         // hasNext 方法保证了第一个元素一定是整数类型
     *         return list.remove(0).getInteger();
     *     }
     *
     *     public boolean hasNext() {
     *         // 循环拆分列表元素，直到列表第一个元素是整数类型
     *         while (!list.isEmpty() && !list.get(0).isInteger()) {
     *             // 当列表开头第一个元素是列表类型时，进入循环
     *             List<NestedInteger> first = list.remove(0).getList();
     *             // 将第一个列表打平并按顺序添加到开头
     *             for (int i = first.size() - 1; i >= 0; i--) {
     *                 list.addFirst(first.get(i));
     *             }
     *         }
     *         return !list.isEmpty();
     *     }
     * }
     *
     * 以这种方法，符合迭代器惰性求值的特性，是比较好的解法，建议拿小本本记下来！
     *
     */
}
