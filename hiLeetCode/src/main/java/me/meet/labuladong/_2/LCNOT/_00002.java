package me.meet.labuladong._2.LCNOT;

import me.meet.labuladong.common.TreeNode;

public final class _00002 {
    private _00002() {
    }

    /*
     * 如果说笔试的时候喜欢靠各种动归回溯的骚操作，面试其实最喜欢考比较经典的问题，难度不算太大，而且也比较实用。
     * 上篇文章 我用四个命令，总结了 Git 的所有套路 写了 Git 最常用的命令，没有提分支合并，其实分支合并没什么困难的，主要就是merge和rebase两种方式。
     * 本文就用 Git 的rebase工作方式引出一个经典的算法问题：最近公共祖先（Lowest Common Ancestor，简称 LCA）。
     *
     * 比如git pull这个命令，我们经常会用，它默认是使用merge方式将远端别人的修改拉到本地；
     * 如果带上上参数git pull -r，就会使用rebase的方式将远端修改拉到本地。
     * 这二者最直观的区别就是：merge方式合并的分支会有很多「分叉」，而rebase方式合并的分支就是一条直线。
     *
     * 对于多人协作，merge方式并不好，举例来说，之前有很多朋友参加了在 GitHub 上的仓库翻译工作，GitHub 的 Pull Request 功能是使用merge方式，
     * 所以你看 fucking-algorithm 仓库的 Git 历史：
     * 画面看起来很炫酷，但实际上我们并不希望出现这种情形的。你想想，光是合并别人的代码就这般群魔乱舞，如果说你本地还有多个开发分支，那画面肯定更杂乱，
     * 杂乱就意味着很容易出问题，所以一般来说，实际工作中更推荐使用rebase方式合并代码。
     * 那么问题来了，rebase是如何将两条不同的分支合并到同一条分支的呢：
     *        .                   .
     *        |                   |
     *        .                   .  master
     *      /   \                 |
     *    .       .               .  dev
     * master    dev
     *
     * 上图的情况是，我站在dev分支，使用git rebase master，然后就会把dev接到master分支之上。Git 是这么做的：
     * 首先，找到这两条分支的最近公共祖先LCA，然后从master节点开始，重演LCA到dev几个commit的修改，如果这些修改和LCA到master的commit有冲突，
     * 就会提示你手动解决冲突，最后的结果就是把dev的分支完全接到master上面。
     * 那么，Git 是如何找到两条不同分支的最近公共祖先的呢？这就是一个经典的算法问题了，下面来详解。
     *
     * 二叉树的最近公共祖先
     *
     */
    /**
     * 二叉树的最近公共祖先
     *
     * 这个问题可以在 LeetCode 上找到，第 236 题，看下题目：
     * 给定一个二叉树，找到该树中两个指定节点的最近公共祖先。
     * 例如， 给定如下二叉树： root = [3,5,1,6,2,0,8,null,null,7,4]
     *                          3
     *                       /    \
     *                     5        1
     *                   /   \    /   \
     *                 6      2  0     8
     *                      /  \
     *                    7     4
     *
     * 例子1：
     * input:   root = [3,5,1,6,2,0,8,null,null,7,4];  p = 5;  q = 1
     * output:  3
     * explain: 节点 5 和 节点 1 的最近公共祖先是 节点 3
     *
     * 例子2：
     * input:   root = [3,5,1,6,2,0,8,null,null,7,4];  p = 5;  q = 4
     * output:  5
     * explain: 节点 5 和 节点 4 的最近公共祖先是节点 5.因为根据定义最近公共祖先节点可以为节点本身。
     *
     *
     * 函数的签名如下：
     * TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q);
     * root节点确定了一棵二叉树，p和q是这这棵二叉树上的两个节点，让你返回p节点和q节点的最近公共祖先节点。
     * 我们前文 学习数据结构和算法的框架思维 就说过了，所有二叉树的套路都是一样的：
     * void traverse(TreeNode root) {
     *     // 前序遍历
     *     traverse(root.left)
     *     // 中序遍历
     *     traverse(root.right)
     *     // 后序遍历
     * }
     *
     * 所以，只要看到二叉树的问题，先把这个框架写出来准没问题：
     * TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
     *     TreeNode left = lowestCommonAncestor(root.left, p, q);
     *     TreeNode right = lowestCommonAncestor(root.right, p, q);
     * }
     *
     * 现在我们思考如何添加一些细节，把框架改造成解法。
     * 遇到任何递归型的问题，无非就是灵魂三问：
     * 1、这个函数是干嘛的？
     * 2、这个函数参数中的变量是什么的是什么？
     * 3、得到函数的递归结果，你应该干什么？
     *
     * 呵呵，看到这灵魂三问，你有没有感觉到熟悉？本号的动态规划系列文章，篇篇都在说的动态规划套路，首先要明确的是什么？
     * 是不是要明确「定义」「状态」「选择」，这仨不就是上面的灵魂三问吗？
     * 下面我们就来看看如何回答这灵魂三问。
     *
     *
     *
     * 解法思路
     * 首先看第一个问题，这个函数是干嘛的？或者说，你给我描述一下lowestCommonAncestor这个函数的「定义」吧。
     * 描述：给该函数输入三个参数root，p，q，它会返回一个节点。
     * 情况 1，如果p和q都在以root为根的树中，函数返回的即使p和q的最近公共祖先节点。
     * 情况 2，那如果p和q都不在以root为根的树中怎么办呢？函数理所当然地返回null呗。
     * 情况 3，那如果p和q只有一个存在于root为根的树中呢？函数就会返回那个节点。
     *
     * 题目说了输入的p和q一定存在于以root为根的树中，但是递归过程中，以上三种情况都有可能发生，所以说这里要定义清楚，后续这些定义都会在代码中体现。
     * OK，第一个问题就解决了，把这个定义记在脑子里，无论发生什么，都不要怀疑这个定义的正确性，这是我们写递归函数的基本素养。
     * 然后来看第二个问题，这个函数的参数中，变量是什么？或者说，你描述一个这个函数的「状态」吧。
     *
     * 描述：
     * 函数参数中的变量是root，因为根据框架，lowestCommonAncestor(root)会递归调用root.left和root.right；至于p和q，我们要求它俩的公共祖先，它俩肯定不会变化的。
     * 第二个问题也解决了，你也可以理解这是「状态转移」，每次递归在做什么？不就是在把「以root为根」转移成「以root的子节点为根」，不断缩小问题规模嘛？
     * 最后来看第三个问题，得到函数的递归结果，你该干嘛？或者说，得到递归调用的结果后，你做什么「选择」？
     * 这就像动态规划系列问题，怎么做选择，需要观察问题的性质，找规律。那么我们就得分析这个「最近公共祖先节点」有什么特点呢？
     * 刚才说了函数中的变量是root参数，所以这里都要围绕root节点的情况来展开讨论。
     * 先想 base case，如果root为空，肯定得返回null。如果root本身就是p或者q，比如说root就是p节点吧，如果q存在于以root为根的树中，显然root就是最近公共祖先；
     * 即使q不存在于以root为根的树中，按照情况 3 的定义，也应该返回root节点。
     *
     * 以上两种情况的 base case 就可以把框架代码填充一点了：
     * TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
     *     // 两种情况的 base case
     *     if (root == null) return null;
     *     if (root == p || root == q) return root;
     *
     *     TreeNode left = lowestCommonAncestor(root.left, p, q);
     *     TreeNode right = lowestCommonAncestor(root.right, p, q);
     * }
     *
     * 现在就要面临真正的挑战了，用递归调用的结果left和right来搞点事情。根据刚才第一个问题中对函数的定义，我们继续分情况讨论：
     *
     * 情况 1，如果p和q都在以root为根的树中，那么left和right一定分别是p和q（从 base case 看出来的）。
     * 情况 2，如果p和q都不在以root为根的树中，直接返回null。
     * 情况 3，如果p和q只有一个存在于root为根的树中，函数返回该节点。
     *
     * 明白了上面三点，可以直接看解法代码了：
     *
     *
     * 对于情况 1，你肯定有疑问，left和right非空，分别是p和q，可以说明root是它们的公共祖先，但能确定root就是「最近」公共祖先吗？
     * 这就是一个巧妙的地方了，因为这里是二叉树的后序遍历啊！
     * 前序遍历可以理解为是从上往下，而后序遍历是从下往上，就好比从p和q出发往上走，第一次相交的节点就是这个root，你说这是不是最近公共祖先呢？
     *
     * 综上，二叉树的最近公共祖先就计算出来了。
     */
    static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // base case
        if (root == null) return null;
        if (root == p || root == q) return root;

        TreeNode left = lowestCommonAncestor(root.getLeft(), p, q);
        TreeNode right = lowestCommonAncestor(root.getRight(), p, q);
        // 情况 1
        if (left != null && right != null) {
            return root;
        }
        // 情况 2
        if (left == null && right == null) {
            return null;
        }
        // 情况 3
        return left == null ? right : left;
    }

    private static void testLowestCommonAncestor() {
        /**         3
         *        /  \
         *      4      5
         *    /  \    /  \
         *   1   3  null  1
         */
        TreeNode _1 = new TreeNode(1);
        TreeNode _3 = new TreeNode(3);
        TreeNode _1a = new TreeNode(1);
        TreeNode _4 = new TreeNode(4, _1, _3);
        TreeNode _5 = new TreeNode(5, null, _1a);
        TreeNode _3a = new TreeNode(3, _4, _5);

        TreeNode rs = lowestCommonAncestor(_3a, _1, _3);
        rs.print();
    }

    public static void main(String[] args) {
        testLowestCommonAncestor();
    }
}
