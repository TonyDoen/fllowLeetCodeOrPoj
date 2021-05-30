package me.meet.labuladong._2;

import me.meet.labuladong.common.TreeNode;

public final class LC0226 {
    private LC0226() {
    }
    /*
     * 读完本文，你可以去力扣拿下：
     * 226. 翻转二叉树，难度 Easy
     * 114. 将二叉树展开为链表，难度 Medium
     * 116. 填充二叉树节点的右侧指针，难度 Medium
     *
     * 先刷二叉树的题目，先刷二叉树的题目，先刷二叉树的题目，因为很多经典算法，以及我们前文讲过的所有回溯、动归、分治算法，其实都是树的问题，
     * 而树的问题就永远逃不开树的递归遍历框架这几行破代码：
     * void traverse(TreeNode root) {
     *     // 前序遍历
     *     traverse(root.left)
     *     // 中序遍历
     *     traverse(root.right)
     *     // 后序遍历
     * }
     *
     * 很多读者说觉得「递归」非常难以理解，说实话，递归解法应该是最简单，最容易理解的才对，行云流水地写递归代码是学好算法的基本功，
     * 而二叉树相关的题目就是最练习递归基本功，最练习框架思维的。
     *
     *
     *
     * 一、二叉树的重要性
     * 举个例子，比如说我们的经典算法「快速排序」和「归并排序」，对于这两个算法，你有什么理解？
     * 如果你告诉我，快速排序就是个二叉树的前序遍历，归并排序就是个二叉树的后续遍历，那么我就知道你是个算法高手了。
     *
     * 为什么快速排序和归并排序能和二叉树扯上关系？我们来简单分析一下他们的算法思想和代码框架：
     * 快速排序的逻辑是，若要对nums[lo..hi]进行排序，
     * 我们先找一个分界点p，通过交换元素使得nums[lo..p-1]都小于等于nums[p]，且nums[p+1..hi]都大于nums[p]，
     * 然后递归地去nums[lo..p-1]和nums[p+1..hi]中寻找新的分界点，
     * 最后整个数组就被排序了。
     *
     * 快速排序的代码框架如下：
     * void sort(int[] nums, int lo, int hi) {
     *     // ==== 前序遍历位置 start ====
     *     // 通过交换元素构建分界点 p
     *     int p = partition(nums, lo, hi);
     *     // ==== 前序遍历位置 end ====
     *
     *     sort(nums, lo, p - 1);
     *     sort(nums, p + 1, hi);
     * }
     * 先构造分界点，然后去左右子数组构造分界点，你看这不就是一个二叉树的前序遍历吗？
     *
     *
     * 再说说归并排序的逻辑，若要对nums[lo..hi]进行排序，
     * 我们先对nums[lo..mid]排序，再对nums[mid+1..hi]排序，
     * 最后把这两个有序的子数组合并，整个数组就排好序了。
     *
     * 归并排序的代码框架如下：
     * void sort(int[] nums, int lo, int hi) {
     *     int mid = (lo + hi) / 2;
     *     sort(nums, lo, mid);
     *     sort(nums, mid + 1, hi);
     *
     *     // ==== 后序遍历位置 start ====
     *     // 合并两个排好序的子数组
     *     merge(nums, lo, mid, hi);
     *     // ==== 后序遍历位置 start ====
     * }
     *
     * 先对左右子数组排序，然后合并（类似合并有序链表的逻辑），你看这是不是二叉树的后序遍历框架？另外，这不就是传说中的分治算法嘛，不过如此呀。
     * 如果你一眼就识破这些排序算法的底细，还需要背这些算法代码吗？这不是手到擒来，从框架慢慢扩展就能写出算法了。
     * 说了这么多，旨在说明，二叉树的算法思想的运用广泛，甚至可以说，只要涉及递归，都可以抽象成二叉树的问题。
     *
     *
     *
     *
     * 二、写递归算法的秘诀
     * 写递归算法的关键是要明确函数的「定义」是什么，然后相信这个定义，利用这个定义推导最终结果，绝不要试图跳入递归。
     * 怎么理解呢，我们用一个具体的例子来说，比如说让你计算一棵二叉树共有几个节点：
     * // 定义：count(root) 返回以 root 为根的树有多少节点
     * int count(TreeNode root) {
     *     // base case
     *     if (root == null) return 0;
     *     // 自己加上子树的节点数就是整棵树的节点数
     *     return 1 + count(root.left) + count(root.right);
     * }
     *
     * 这个问题非常简单，大家应该都会写这段代码，root本身就是一个节点，加上左右子树的节点数就是以root为根的树的节点总数。
     * 左右子树的节点数怎么算？其实就是计算根为root.left和root.right两棵树的节点数呗，按照定义，递归调用count函数即可算出来。
     *
     * 写树相关的算法，简单说就是，先搞清楚当前root节点该做什么，然后根据函数定义递归调用子节点，递归调用会让孩子节点做相同的事情。
     *
     *
     */
    /**
     * 第一题、翻转二叉树
     * 我们先从简单的题开始，看看力扣第 226 题「翻转二叉树」，输入一个二叉树根节点root，让你把整棵树镜像翻转，
     * 比如输入的二叉树如下：
     *      4
     *    /   \
     *   2     7
     *  / \   / \
     * 1   3 6   9
     *
     * 算法原地翻转二叉树，使得以root为根的树变成：
     *      4
     *    /   \
     *   7     2
     *  / \   / \
     * 9   6 3   1
     *
     * 通过观察，我们发现只要把二叉树上的每一个节点的左右子节点进行交换，最后的结果就是完全翻转之后的二叉树。
     *
     * 这道题目比较简单，关键思路在于我们发现翻转整棵树就是交换每个节点的左右子节点，于是我们把交换左右子节点的代码放在了前序遍历的位置。
     * 值得一提的是，如果把交换左右子节点的代码放在后序遍历的位置也是可以的，但是放在中序遍历的位置是不行的，请你想一想为什么？这个应该不难想到，我会把答案置顶在公众号留言区。
     *
     *
     */
    // 将整棵树的节点翻转
    static TreeNode invertTree(TreeNode root) {
        // base case
        if (root == null) {
            return null;
        }

        /**** 前序遍历位置 ****/
        // root 节点需要交换它的左右子节点
        TreeNode tmp = root.getLeft(); // TreeNode tmp = root.left;
        root.setLeft(root.getRight()); // root.left = root.right;
        root.setRight(tmp); // root.right = tmp;

        // 让左右子节点继续翻转它们的子节点
        invertTree(root.getLeft());
        invertTree(root.getRight());

        return root;
    }

    /**
     * 首先讲这道题目是想告诉你，二叉树题目的一个难点就是，如何把题目的要求细化成每个节点需要做的事情。
     * 这种洞察力需要多刷题训练，我们看下一道题。
     *
     * 第二题、填充二叉树节点的右侧指针
     * 这是力扣第 116 题，看下题目：
     * 116. 填充二叉树节点的右侧指针    medium
     * 给定一个完美二叉树，其所有叶子节点都在同一层，每个父节点都是两个子节点。二叉树定义如下
     * struct Node {
     *     int val;
     *     Node *left;
     *     Node *right;
     *     Node *next;
     * }
     * 填充它的每个next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将next指针设置为NULL。
     * 初始状态下，所有next指针都被设置为NULL。
     *
     * 函数签名如下： Node connect(Node root);
     *
     * 题目的意思就是把二叉树的每一层节点都用next指针连接起来：
     *      1                          1 -------------> NULL
     *    /   \                    /       \
     *   2     3                 2 -------> 3 --------> NULL
     *  / \   / \               /  \      /  \
     * 4   5 6   7             4 -> 5 -> 6 -> 7 ------> NULL
     *  figure A                   figure B
     *
     * 而且题目说了，输入是一棵「完美二叉树」，形象地说整棵二叉树是一个正三角形，除了最右侧的节点next指针会指向null，其他节点的右侧一定有相邻的节点。
     * 这道题怎么做呢？把每一层的节点穿起来，是不是只要把每个节点的左右子节点都穿起来就行了？
     * 我们可以模仿上一道题，写出如下代码：
     *
     * 节点 5 和节点 6 不属于同一个父节点，那么按照这段代码的逻辑，它俩就没办法被穿起来，这是不符合题意的。
     * 回想刚才说的，二叉树的问题难点在于，如何把题目的要求细化成每个节点需要做的事情，但是如果只依赖一个节点的话，肯定是没办法连接「跨父节点」的两个相邻节点的。
     * 那么，我们的做法就是增加函数参数，一个节点做不到，我们就给他安排两个节点，「将每一层二叉树节点连接起来」可以细化成「将每两个相邻节点都连接起来」：
     *
     * 这样，connectTwoNode函数不断递归，可以无死角覆盖整棵二叉树，将所有相邻节点都连接起来，也就避免了我们之前出现的问题，这道题就解决了。
     *
     */
    // 主函数
    static TreeNode connect(TreeNode root) {
        if (null == root) {
            return null;
        }
        connect2Node(root.getLeft(), root.getRight());
        return root;
    }
    // 定义：输入两个节点，将它俩连接起来
    private static void connect2Node(TreeNode n1, TreeNode n2) {
        if (null == n1 || null == n2) {
            return;
        }
        /**** 前序遍历位置 ****/
        // 将传入的两个节点连接
        n1.setNext(n2);

        // 连接相同父节点的两个子节点
        connect2Node(n1.getLeft(), n2.getRight());
        connect2Node(n2.getLeft(), n2.getRight());
        // 连接跨越父节点的两个子节点
        connect2Node(n1.getRight(), n2.getLeft());
    }

    /**
     * 第三题、将二叉树展开为链表
     *
     * 这是力扣第 114 题，看下题目：
     * 给定一个二叉树，原地将它展开一个单链表，
     *      4
     *    /   \
     *   2     7
     *  / \   / \
     * 1   3 6   9
     * 将其展开
     *
     *
     *
     *
     * 函数签名如下：
     * void flatten(TreeNode root);
     * 我们尝试给出这个函数的定义：
     * 给flatten函数输入一个节点root，那么以root为根的二叉树就会被拉平为一条链表。
     * 我们再梳理一下，如何按题目要求把一棵树拉平成一条链表？很简单，以下流程：
     * 1、将root的左子树和右子树拉平。
     * 2、将root的右子树接到左子树下方，然后将整个左子树作为右子树。
     *
     *
     * 上面三步看起来最难的应该是第一步对吧，如何把root的左右子树拉平？其实很简单，按照flatten函数的定义，对root的左右子树递归调用flatten函数即可：
     *
     *
     * 你看，这就是递归的魅力，你说flatten函数是怎么把左右子树拉平的？不容易说清楚，但是只要知道flatten的定义如此，相信这个定义，让root做它该做的事情，然后flatten函数就会按照定义工作。
     * 另外注意递归框架是后序遍历，因为我们要先拉平左右子树才能进行后续操作。
     * 至此，这道题也解决了，我们旧文 递归思维：k 个一组反转链表 的递归思路和本题也有一些类似。
     */
    // 定义：将以 root 为根的树拉平为链表
    static void flatten(TreeNode root) {
        // base case
        if (root == null) return;

        flatten(root.getLeft());
        flatten(root.getRight());

        /**** 后序遍历位置 ****/
        // 1、左右子树已经被拉平成一条链表
        TreeNode left = root.getLeft();
        TreeNode right = root.getRight();

        // 2、将左子树作为右子树
        root.setLeft(null); // root.left = null;
        root.setRight(left); // root.right = left;

        // 3、将原先的右子树接到当前右子树的末端
        TreeNode p = root;
        while (p.getRight() != null) {
            p = p.getRight();
        }
        p.setRight(right); // p.right = right;
    }

    /**
     * 四、最后总结
     * 递归算法的关键要明确函数的定义，相信这个定义，而不要跳进递归细节。
     * 写二叉树的算法题，都是基于递归框架的，我们先要搞清楚root节点它自己要做什么，然后根据题目要求选择使用前序，中序，后续的递归框架。
     * 二叉树题目的难点在于如何通过题目的要求思考出每一个节点需要做什么，这个只能通过多刷题进行练习了。
     * 如果本文讲的三道题对你有一些启发，请三连，数据好的话东哥下次再来一波手把手刷题文，你很快就会发现二叉树的题真的是越刷越顺手，欲罢不能，恨不得一口气把二叉树的题刷通。
     *
     */

    private static void testInvertTree() {
        TreeNode root = TreeNode.prepareTree1();
        root.println();

        TreeNode rs = invertTree(root);
        rs.println();
    }

    private static void testConnect() {
        TreeNode root = TreeNode.prepareTree4();
        root.println();

        connect(root);
        root.println();
    }

    private static void testFlatten() {
        TreeNode root = TreeNode.prepareTree4();
        root.println();

        flatten(root);
        root.println();
    }

    public static void main(String[] args) {
        testInvertTree();

        testConnect();

        testFlatten();
    }
}
