package me.meet.labuladong._2;

import me.meet.labuladong.common.TreeNode;

public final class LC0654 {
    private LC0654() {
    }
    /*
     * 读完本文，你能去力扣解决如下题目：
     *
     * 654.最大二叉树（难度 Medium）
     * 105.从前序与中序遍历序列构造二叉树（难度 Medium）
     * 106.从中序与后序遍历序列构造二叉树（难度 Medium）
     *
     * 上篇文章 手把手教你刷二叉树（第一篇） 连刷了三道二叉树题目，很多读者直呼内行。其实二叉树相关的算法真的不难，本文再来三道，手把手带你看看树的算法到底怎么做。
     * 先来复习一下，我们说过写树的算法，关键思路如下：
     * 把题目的要求细化，搞清楚根节点应该做什么，然后剩下的事情抛给前/中/后序的遍历框架就行了，我们千万不要跳进递归的细节里，你的脑袋才能压几个栈呀。
     * 也许你还不太理解这句话，我们下面来看例子。
     */

    /**
     * 构造最大二叉树
     * 先来道简单的，这是力扣第 654 题，题目如下：
     *
     * 654. 最大二叉树
     * 难度： 中等
     * 给定一个不含重复元素的整数数组。一个以此数组构建的最大二叉树定义如下：
     * 1. 二叉树的根是数组中的最大元素。
     * 2. 左子树是通过数组中最大值左边部分构造出的最大二叉树。
     * 3. 右子树是通过数组中最大值右边部分构造出的最大二叉树。
     * 通过给定的数组构造最大二叉树，并且输出这个树的根节点。
     *
     * 例子：
     * input:  [3,2,1,6,0,5]
     * output: 返回下面这棵树的根节点
     *          6
     *        /   \
     *      3      5
     *       \    /
     *        2  0
     *         \
     *          1
     *
     *
     * 思路：
     *
     * 函数签名如下：
     * TreeNode constructMaximumBinaryTree(int[] nums);
     * 按照我们刚才说的，先明确根节点做什么？对于构造二叉树的问题，根节点要做的就是把想办法把自己构造出来。
     * 我们肯定要遍历数组把找到最大值maxVal，把根节点root做出来，然后对maxVal左边的数组和右边的数组进行递归调用，作为root的左右子树。
     * 按照题目给出的例子，输入的数组为[3,2,1,6,0,5]，对于整棵树的根节点来说，其实在做这件事：
     *
     * TreeNode constructMaximumBinaryTree([3,2,1,6,0,5]) {
     *     // 找到数组中的最大值
     *     TreeNode root = new TreeNode(6);
     *     // 递归调用构造左右子树
     *     root.left = constructMaximumBinaryTree([3,2,1]);
     *     root.right = constructMaximumBinaryTree([0,5]);
     *     return root;
     * }
     *
     * 看懂了吗？对于每个根节点，只需要找到当前nums中的最大值和对应的索引，然后递归调用左右数组构造左右子树即可。
     * 明确了思路，我们可以重新写一个辅助函数build，来控制nums的索引：
     *
     */
    static TreeNode constructMaximumBinaryTree(int[] nums) {
        return buildMaximumBinaryTree(nums, 0, nums.length - 1);
    }

    private static TreeNode buildMaximumBinaryTree(int[] nums, int lo, int hi) {
        // base case
        if (lo > hi) {
            return null;
        }

        // 找到数组中的最大值和对应的索引
        int idx = -1, maxVal = Integer.MIN_VALUE;
        for (int i = lo; i <= hi; i++) {
            if (maxVal < nums[i]) {
                idx = i;
                maxVal = nums[i];
            }
        }

        TreeNode root = new TreeNode(maxVal);
        // 递归调用构造左右子树
        root.setLeft(buildMaximumBinaryTree(nums, lo, idx - 1));
        root.setRight(buildMaximumBinaryTree(nums, idx + 1, hi));
        return root;
    }

    private static void testConstructMaximumBinaryTree() {
        int[] nums = new int[]{3, 2, 1, 6, 0, 5};
        TreeNode rs = constructMaximumBinaryTree(nums);
        rs.print();
    }

    /**
     * 通过前序和中序遍历结果构造二叉树
     *
     * 经典问题了，面试/笔试中常考，力扣第 105 题就是这个问题：
     *
     * 105. 从前序与中序遍历序列构造二叉树
     * 难度： 中等
     *
     * 根据一棵树的前序遍历与中序遍历构造二叉树
     * 注意： 可以假设树中没有重复的元素
     *
     * 例如：
     * 前序遍历 preOrder = [3,9,20,15,7]
     * 中序遍历 inOrder  = [9,3,15,20,7]
     *
     * 返回如下二叉树：
     *            3
     *          /   \
     *        9      20
     *              /  \
     *            15    7
     *
     *
     * 思路：
     *
     * 函数签名如下：
     * TreeNode buildTree(int[] preorder, int[] inorder);
     * 废话不多说，直接来想思路，首先思考，根节点应该做什么。
     * 类似上一题，我们肯定要想办法确定根节点的值，把根节点做出来，然后递归构造左右子树即可。
     * 我们先来回顾一下，前序遍历和中序遍历的结果有什么特点？
     *
     * void traverse(TreeNode root) {
     *     // 前序遍历
     *     preorder.add(root.val);
     *     traverse(root.left);
     *     traverse(root.right);
     * }
     *
     * void traverse(TreeNode root) {
     *     traverse(root.left);
     *     // 中序遍历
     *     inorder.add(root.val);
     *     traverse(root.right);
     * }
     *
     * 前文 二叉树就那几个框架 写过，这样的遍历顺序差异，导致了preorder和inorder数组中的元素分布有如下特点：
     *                                                                root.left          root.right
     *               root                                 root ┌────────────────────┐   ┌──────────┐
     *            3                            preOrder   1    2    5    4    6    7    3    8    9
     *          /   \
     *        9      20                                         root.left                root.right
     *              /  \                                  ┌────────────────────┐   root ┌──────────┐
     *            15    7                      inOrder    5    2    6    4    7    1    8    3    9
     *
     * 找到根节点是很简单的，前序遍历的第一个值preorder[0]就是根节点的值，关键在于如何通过根节点的值，将preorder和postorder数组划分成两半，构造根节点的左右子树？
     * 换句话说，对于以下代码中的?部分应该填入什么：
     *
     * // 主函数
     * TreeNode buildTree(int[] preorder, int[] inorder) {
     *     return build(preorder, 0, preorder.length - 1,
     *                  inorder, 0, inorder.length - 1);
     * }
     *
     * // 若前序遍历数组为 preorder[preStart..preEnd]，
     * // 后续遍历数组为 postorder[postStart..postEnd]，
     * // 构造二叉树，返回该二叉树的根节点
     * TreeNode build(int[] preorder, int preStart, int preEnd,
     *                int[] inorder, int inStart, int inEnd) {
     *     // root 节点对应的值就是前序遍历数组的第一个元素
     *     int rootVal = preorder[preStart];
     *     // rootVal 在中序遍历数组中的索引
     *     int index = 0;
     *     for (int i = inStart; i <= inEnd; i++) {
     *         if (inorder[i] == rootVal) {
     *             index = i;
     *             break;
     *         }
     *     }
     *
     *     TreeNode root = new TreeNode(rootVal);
     *     // 递归构造左右子树
     *     root.left = build(preorder, ?, ?,
     *                       inorder, ?, ?);
     *
     *     root.right = build(preorder, ?, ?,
     *                        inorder, ?, ?);
     *     return root;
     * }
     *
     * 现在我们来看图做填空题，下面这几个问号处应该填什么：
     * root.left = build(preorder, ?, ?,
     *                   inorder, ?, ?);
     *
     * root.right = build(preorder, ?, ?,
     *                    inorder, ?, ?);
     *
     * 对于左右子树对应的inorder数组的起始索引和终止索引比较容易确定：
     * root.left = build(preorder, ?, ?,
     *                   inorder, inStart, index - 1);
     *
     * root.right = build(preorder, ?, ?,
     *                    inorder, index + 1, inEnd);
     *
     * 对于preorder数组呢？如何确定左右数组对应的起始索引和终止索引？
     * 这个可以通过左子树的节点数推导出来，假设左子树的节点数为leftSize，那么preorder数组上的索引情况是这样的：
     * int leftSize = index - inStart;
     *
     * root.left = build(preorder, preStart + 1, preStart + leftSize,
     *                   inorder, inStart, index - 1);
     *
     * root.right = build(preorder, preStart + leftSize + 1, preEnd,
     *                    inorder, index + 1, inEnd);
     *
     *
     *              preStart                                         preEnd
     * preOrder => |_rootVal_|_root.left________|_root.right______________|
     *
     *              inStart           index-1   index  index+1        inEnd
     * inOrder =>  |___________root.left____|_rootVal_|_root.right________|
     *
     *                                    | |
     *                                   \  /
     *                                    \/
     *              preStart                                         preEnd
     * preOrder => |_rootVal_|_root.left________|_root.right______________|
     *
     *              inStart           index-1   index  index+1        inEnd
     * inOrder =>  |___________root.left____|_rootVal_|_root.right________|
     *
     *                                    | |
     *                                   \  /
     *                                    \/
     *              preStart     *** preStart+leftSize ***           preEnd
     * preOrder => |_rootVal_|_root.left________|_root.right______________|
     *
     *              inStart           index-1   index  index+1        inEnd
     * inOrder =>  |___________root.left____|_rootVal_|_root.right________|
     *                   *** leftSize ***
     *
     * 我们的主函数只要调用build函数即可，你看着函数这么多参数，解法这么多代码，似乎比我们上面讲的那道题难很多，让人望而生畏，
     * 实际上呢，这些参数无非就是控制数组起止位置的，画个图就能解决了。
     */
    static TreeNode buildBinaryTreeFromPreInOrder(int[] preOrder, int preStart, int preEnd,
                                                  int[] inOrder, int inStart, int inEnd) {
        // base case
        if (preStart > preEnd) {
            return null;
        }

        // root 节点对应的值就是前序遍历数组的第一个元素
        int rootVal = preOrder[preStart];
        // rootVal 在中序遍历数组中的索引
        int index = 0;
        for (int i = inStart; i <= inEnd; i++) {
            if (inOrder[i] == rootVal) {
                index = i;
                break;
            }
        }

        int leftSize = index - inStart;
        // 先构造出当前根节点
        TreeNode root = new TreeNode(rootVal);
        // 递归构造左右子树
        root.setLeft(buildBinaryTreeFromPreInOrder(preOrder, preStart + 1, preStart + leftSize,
            inOrder, inStart, index - 1));

        root.setRight(buildBinaryTreeFromPreInOrder(preOrder, preStart + leftSize + 1, preEnd,
            inOrder, index + 1, inEnd));
        return root;

    }

    private static void testBuildBinaryTreeFromPreInOrder() {
        /*
         * 前序遍历 preOrder = [3,9,20,15,7]
         * 中序遍历 inOrder  = [9,3,15,20,7]
         */
        int[] preOrder = new int[]{3, 9, 20, 15, 7};
        int[] inOrder = new int[]{9, 3, 15, 20, 7};

        TreeNode rs = buildBinaryTreeFromPreInOrder(preOrder, 0, preOrder.length - 1
            , inOrder, 0, inOrder.length - 1);
        assert rs != null;
        rs.print();
    }

    /**
     * 通过后序和中序遍历结果构造二叉树
     *
     * 难度： 中等
     * 注意： 可以假设树中没有重复的元素
     *
     * 例如：
     * 中序遍历 inOrder  = [9,3,15,20,7]
     * 后序遍历 preOrder = [9,15,7,20,3]
     *
     * 返回如下二叉树：
     *            3
     *          /   \
     *        9      20
     *              /  \
     *            15    7
     *
     * 类似的，看下后序和中序遍历的特点：
     * void traverse(TreeNode root) {
     *     traverse(root.left);
     *     traverse(root.right);
     *     // 前序遍历
     *     postorder.add(root.val);
     * }
     *
     * void traverse(TreeNode root) {
     *     traverse(root.left);
     *     // 中序遍历
     *     inorder.add(root.val);
     *     traverse(root.right);
     * }
     *
     * 这样的遍历顺序差异，导致了preorder和inorder数组中的元素分布有如下特点：
     *                                                            root.left          root.right
     *               root                                  ┌────────────────────┐   ┌──────────┐   root
     *            3                            postOrder   5    6    7    4    2    8    9    3    1
     *          /   \
     *        9      20                                         root.left                root.right
     *              /  \                                  ┌────────────────────┐   root ┌──────────┐
     *            15    7                      inOrder    5    2    6    4    7    1    8    3    9
     *
     *
     *
     * 有了前一题的铺垫，这道题很快就解决了，无非就是rootVal变成了最后一个元素，再改改递归函数的参数而已，只要明白二叉树的特性，也不难写出来。
     * 最后呼应下前文，做二叉树的问题，关键是把题目的要求细化，搞清楚根节点应该做什么，然后剩下的事情抛给前/中/后序的遍历框架就行了。
     */
    static TreeNode buildBinaryTreeFromInPostOrder(int[] inOrder, int inStart, int inEnd,
                                                   int[] postOrder, int postStart, int postEnd) {
        // base case
        if (inStart > inEnd) {
            return null;
        }
        // root 节点对应的值就是后序遍历数组的最后一个元素
        int rootVal = postOrder[postEnd];
        // rootVal 在中序遍历数组中的索引
        int index = 0;
        for (int i = inStart; i <= inEnd; i++) {
            if (inOrder[i] == rootVal) {
                index = i;
                break;
            }
        }
        // 左子树的节点个数
        int leftSize = index - inStart;
        TreeNode root = new TreeNode(rootVal);
        // 递归构造左右子树
        root.setLeft(buildBinaryTreeFromInPostOrder(inOrder, inStart, index - 1,
            postOrder, postStart, postStart + leftSize - 1));

        root.setRight(buildBinaryTreeFromInPostOrder(inOrder, index + 1, inEnd,
            postOrder, postStart + leftSize, postEnd - 1));
        return root;
    }

    private static void testBuildBinaryTreeFromInPostOrder() {
        /*
         * 中序遍历 inOrder   = [9,3,15,20,7]
         * 后序遍历 postOrder = [9,15,7,20,3]
         */
        int[] inOrder = new int[]{9, 3, 15, 20, 7};
        int[] postOrder = new int[]{9, 15, 7, 20, 3};

        TreeNode rs = buildBinaryTreeFromInPostOrder(inOrder, 0, inOrder.length - 1
            , postOrder, 0, postOrder.length - 1);
        assert rs != null;
        rs.print();

    }

    public static void main(String[] args) {
        // 构造最大二叉树
        testConstructMaximumBinaryTree();
        // 从前序与中序遍历序列构造二叉树
        testBuildBinaryTreeFromPreInOrder();
        // 通过后序和中序遍历结果构造二叉树
        testBuildBinaryTreeFromInPostOrder();
    }
}
