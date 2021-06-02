package me.meet.labuladong._2;

import me.meet.labuladong.common.TreeNode;

public final class LC0230 {
    private LC0230() {
    }
    /*
     * 前文「手把手刷二叉树系列」已经写了 第一期，第二期 和 第三期，
     * 今天写一篇二叉搜索树（Binary Search Tree，后文简写 BST）相关的文章，手把手带你刷 BST。
     *
     * 首先，BST 的特性大家应该都很熟悉了：
     * 1、对于 BST 的每一个节点node，左子树节点的值都比node的值要小，右子树节点的值都比node的值大。
     * 2、对于 BST 的每一个节点node，它的左侧子树和右侧子树都是 BST。
     *
     * 二叉搜索树并不算复杂，但我觉得它构建起了数据结构领域的半壁江山，直接基于 BST 的数据结构有 AVL 树，红黑树等等，
     * 拥有了自平衡性质，
     * 可以提供 logN 级别的增删查改效率；
     *
     * 还有 B+ 树，线段树等结构都是基于 BST 的思想来设计的。
     *
     * 从做算法题的角度来看 BST，除了它的定义，还有一个重要的性质：BST 的中序遍历结果是有序的（升序）。
     * 也就是说，如果输入一棵 BST，以下代码可以将 BST 中每个节点的值升序打印出来：
     * void traverse(TreeNode root) {
     *     if (root == null) return;
     *     traverse(root.left);
     *     // 中序遍历代码位置
     *     print(root.val);
     *     traverse(root.right);
     * }
     *
     *
     */
    /**
     * 寻找第 K 小的元素
     *
     * 首先是力扣第 230 题「二叉搜索树中第K小的元素」，看下题目：
     * 难度：中等
     * 给定一个二叉搜索树，编写一个函数 kthSmallest 来查找其中第 k 个最小的元素。你可以假设 k 总是有效的， 1<=k<=二叉搜索树元素个数。
     * 例子1:
     * input:  root = [5,3,6,2,4,null,null,1];  k = 3
     *               5
     *            /    \
     *          3       6
     *        /  \     /
     *      2     2   0
     *    /
     *  1
     * output: 3
     *
     * 这个需求很常见，一个直接的思路就是升序排序，然后找到第 k 个元素。 BST 的中序遍历其实就是升序排序的结果，
     *
     *
     * 这道题就做完了，不过呢，还是要多说几句，因为这个解法并不是最高效的解法，而是仅仅适用于这道题。
     */

    // 记录结果
    private static int res = 0;
    // 记录当前元素的排名
    private static int rank = 0;

    static int kthSmallest(TreeNode root, int k) {
        // 利用 BST 的中序遍历特性
        traverse(root, k);
        return res;
    }

    private static void traverse(TreeNode root, int k) {
        if (root == null) {
            return;
        }
        traverse(root.getLeft(), k);
        // ========= 中序遍历代码位置 start ==========
        rank++;
        if (k == rank) {
            // 找到第 k 小的元素
            res = root.getVal();
            return;
        }
        // ========= 中序遍历代码位置  end  ==========
        traverse(root.getRight(), k);
    }

    /**
     * 如果让你实现一个在二叉搜索树中通过排名计算对应元素的方法select(int k)，你会怎么设计？
     *
     * 如果按照我们刚才说的方法，利用「BST 中序遍历就是升序排序结果」这个性质，每次寻找第k小的元素都要中序遍历一次，最坏的时间复杂度是O(N)，N是 BST 的节点个数。
     *
     * 要知道 BST 性质是非常牛逼的，像红黑树这种改良的自平衡 BST，增删查改都是O(logN)的复杂度，让你算一个第k小元素，时间复杂度竟然要O(N)，有点低效了。
     * 所以说，计算第k小元素，最好的算法肯定也是对数级别的复杂度，不过这个依赖于 BST 节点记录的信息有多少。
     * 我们想一下 BST 的操作为什么这么高效？
     * 就拿搜索某一个元素来说，BST 能够在对数时间找到该元素的根本原因还是在 BST 的定义里，左子树小右子树大嘛，所以每个节点都可以通过对比自身的值判断去左子树还是右子树搜索目标值，从而避免了全树遍历，达到对数级复杂度。
     *
     * 那么回到这个问题，想找到第k小的元素，或者说找到排名为k的元素，如果想达到对数级复杂度，关键也在于每个节点得知道他自己排第几。
     *
     * 比如说你让我查找排名为k的元素，当前节点知道自己排名第m，那么我可以比较m和k的大小：
     * 1、如果m == k，显然就是找到了第k个元素，返回当前节点就行了。
     * 2、如果k < m，那说明排名第k的元素在左子树，所以可以去左子树搜索第k个元素。
     * 3、如果k > m，那说明排名第k的元素在右子树，所以可以去右子树搜索第k - m - 1个元素。
     *
     * 这样就可以将时间复杂度降到O(logN)了。
     *
     * 那么，如何让每一个节点知道自己的排名呢？
     * 这就是我们之前说的，需要在二叉树节点中维护额外信息。每个节点需要记录，以自己为根的这棵二叉树有多少个节点。
     * 也就是说，我们TreeNode中的字段应该如下：
     * class TreeNode {
     *     int val;
     *     // 以该节点为根的树的节点总数
     *     int size;
     *     TreeNode left;
     *     TreeNode right;
     * }
     *
     * 有了size字段，外加 BST 节点左小右大的性质，对于每个节点node就可以通过node.left推导出node的排名，从而做到我们刚才说到的对数级算法。
     * 当然，size字段需要在增删元素的时候需要被正确维护，力扣提供的TreeNode是没有size这个字段的，所以我们这道题就只能利用 BST 中序遍历的特性实现了，
     * 但是我们上面说到的优化思路是 BST 的常见操作，还是有必要理解的。
     *
     */

    /**
     * BST 转化累加树
     *
     * 力扣第 538 题和 1038 题都是这道题，完全一样，你可以把它们一块做掉。看下题目：
     *
     * 538. 把二叉搜索树转换为累加树
     * 难度： 中等
     * 给出二叉搜索树的根节点，该树的节点值各不相同，请你将其转换成累加树(Greater Sum Tree), 使每个节点 node 的新值等于 原来树中大于或等于 node.val 的值之和。
     * 例子1：
     *        4       ...
     *      /   \
     *    2       7   => 16 = 7+9
     *  /  \    /  \
     * 1    3  6... 9 => 9
     *
     * 题目应该不难理解，比如图中的节点 6，转化成累加树的话，比 6 大的节点有 7，9，加上 6 本身，所以累加树上这个节点的值应该是 6+7+9=22。
     *
     * 我们需要把 BST 转化成累加树，函数签名如下：
     * TreeNode convertBST(TreeNode root)
     * 按照二叉树的通用思路，需要思考每个节点应该做什么，但是这道题上很难想到什么思路。
     * BST 的每个节点左小右大，这似乎是一个有用的信息，既然累加和是计算大于等于当前值的所有元素之和，那么每个节点都去计算右子树的和，不就行了吗？
     * 这是不行的。对于一个节点来说，确实右子树都是比它大的元素，但问题是它的父节点也可能是比它大的元素呀？
     * 这个没法确定的，我们又没有触达父节点的指针，所以二叉树的通用思路在这里用不了。
     *
     * 其实，正确的解法很简单，还是利用 BST 的中序遍历特性。
     *
     * 刚才我们说了 BST 的中序遍历代码可以升序打印节点的值：
     * void traverse(TreeNode root) {
     *     if (root == null) return;
     *     traverse(root.left);
     *     // 中序遍历代码位置
     *     print(root.val);
     *     traverse(root.right);
     * }
     *
     * 那如果我想降序打印节点的值怎么办？ 很简单，只要把递归顺序改一下就行了：
     * void traverse(TreeNode root) {
     *     if (root == null) return;
     *     // 先递归遍历右子树
     *     traverse(root.right);
     *     // 中序遍历代码位置
     *     print(root.val);
     *     // 后递归遍历左子树
     *     traverse(root.left);
     * }
     *
     * 这段代码可以从大到小降序打印 BST 节点的值，如果维护一个外部累加变量sum，然后把sum赋值给 BST 中的每一个节点，不就将 BST 转化成累加树了吗？
     * 看下代码就明白了：
     *
     */
    static TreeNode convertBST2GreaterSumTree(TreeNode root) {
        traverse(root);
        return root;
    }

    // 记录累加和
    private static int sum = 0;

    private static void traverse(TreeNode root) {
        if (root == null) {
            return;
        }
        traverse(root.getRight()); // careful
        // ========= 中序遍历代码位置 start ==========
        // 维护累加和
        sum += root.getVal();
        // 将 BST 转化成累加树
        root.setVal(sum);
        // ========= 中序遍历代码位置  end  ==========

        traverse(root.getLeft());
    }

    /**
     * 这道题就解决了，核心还是 BST 的中序遍历特性，只不过我们修改了递归顺序，降序遍历 BST 的元素值，从而契合题目累加树的要求。
     *
     * 简单总结下吧，BST 相关的问题，要么利用 BST 左小右大的特性提升算法效率，要么利用中序遍历的特性满足题目的要求，也就这么些事儿吧。
     *
     */

    /*
     * 前文 手把手刷二叉搜索树（第一期） 主要是利用二叉搜索树「中序遍历有序」的特性来解决了几道题目，
     *
     * 本文来实现 BST 的基础操作：判断 BST 的合法性、增、删、查。其中「删」和「判断合法性」略微复杂。
     *
     *
     */

    /**
     * 一、判断 BST 的合法性
     * 这里是有坑的哦，我们按照刚才的思路，每个节点自己要做的事不就是比较自己和左右孩子吗？看起来应该这样写代码：
     * boolean isValidBST(TreeNode root) {
     *     if (root == null) return true;
     *     if (root.left != null && root.val <= root.left.val)
     *         return false;
     *     if (root.right != null && root.val >= root.right.val)
     *         return false;
     *
     *     return isValidBST(root.left)
     *         && isValidBST(root.right);
     * }
     *
     * 但是这个算法出现了错误，BST 的每个节点应该要小于右边子树的所有节点，下面这个二叉树显然不是 BST，因为节点 10 的右子树中有一个节点 6，
     * 但是我们的算法会把它判定为合法 BST：
     *        10？
     *      /   \
     *    5       15
     *          /  \
     *         6？  20
     * 出现问题的原因在于，对于每一个节点root，代码值检查了它的左右孩子节点是否符合左小右大的原则；
     * 但是根据 BST 的定义，root的整个左子树都要小于root.val，整个右子树都要大于root.val。
     *
     * 请看正确的代码：
     *
     * 我们通过使用辅助函数，增加函数参数列表，在参数中携带额外信息，将这种约束传递给子树的所有节点，这也是二叉树算法的一个小技巧吧。
     */
    static boolean isValidBST(TreeNode root) {
        return isValidBST(root, null, null);
    }

    // 限定以 root 为根的子树节点必须满足 max.val > root.val > min.val
    private static boolean isValidBST(TreeNode root, TreeNode min, TreeNode max) {
        // base case
        if (root == null) return true;

        // 若 root.val 不符合 max 和 min 的限制，说明不是合法 BST
        if (min != null && root.getVal() <= min.getVal()) return false;
        if (max != null && root.getVal() >= max.getVal()) return false;
        // 限定左子树的最大值是 root.val，右子树的最小值是 root.val
        return isValidBST(root.getLeft(), min, root) && isValidBST(root.getRight(), root, max);
    }

    /**
     * 在 BST 中搜索一个数
     *
     * 如果是在二叉树中寻找元素，可以这样写代码：
     * boolean isInBST(TreeNode root, int target) {
     *     if (root == null) return false;
     *     if (root.val == target) return true;
     *     // 当前节点没找到就递归地去左右子树寻找
     *     return isInBST(root.left, target) || isInBST(root.right, target);
     * }
     *
     * 这样写完全正确，但这段代码相当于穷举了所有节点，适用于所有普通二叉树。那么应该如何充分利用信息，把 BST 这个「左小右大」的特性用上？
     * 很简单，其实不需要递归地搜索两边，类似二分查找思想，根据target和root.val的大小比较，就能排除一边。我们把上面的思路稍稍改动：
     *
     * 于是，我们对原始框架进行改造，抽象出一套针对 BST 的遍历框架：
     * void BST(TreeNode root, int target) {
     *     if (root.val == target)
     *         // 找到目标，做点什么
     *     if (root.val < target)
     *         BST(root.right, target);
     *     if (root.val > target)
     *         BST(root.left, target);
     * }
     * 这个代码框架其实和二叉树的遍历框架差不多，无非就是利用了 BST 左小右大的特性而已。
     *
     */
    static boolean isInBST(TreeNode root, int target) {
        if (root == null) return false;
        if (root.getVal() == target) return true;
        if (root.getVal() < target) return isInBST(root.getRight(), target);
        if (root.getVal() > target) return isInBST(root.getLeft(), target);
        // root 该做的事做完了，顺带把框架也完成了，妙
        return false;
    }

    private static void testKthSmallest() {
        TreeNode root = TreeNode.prepareTree4();
        root.print();

        int rs = kthSmallest(root, 3);
        System.out.println(rs);
    }

    private static void testConvertBST2GreaterSumTree() {
        TreeNode root = TreeNode.prepareTree4();
        root.print();

        TreeNode rs = convertBST2GreaterSumTree(root);
        rs.print();
    }

    private static void testIsValidBST() {
        TreeNode root = TreeNode.prepareTree4();
        root.print();

        boolean rs = isValidBST(root);
        System.out.println(rs);
    }

    private static void testIsInBST() {
        TreeNode root = TreeNode.prepareTree4();
        root.print();

        int target = 7;
        boolean rs = isInBST(root, target);
        System.out.println(rs);
    }

    public static void main(String[] args) {
        // 230. 二叉搜索树中第K小的元素
        testKthSmallest();
        // 538. 把二叉搜索树转换为累加树
        testConvertBST2GreaterSumTree();
        // 判断 BST 的合法性
        testIsValidBST();
        // 在 BST 中搜索一个数
        testIsInBST();
    }
}
