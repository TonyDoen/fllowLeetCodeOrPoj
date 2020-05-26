package me.meet.leetcode.easy;

public final class RangeSumOfBST {
    private RangeSumOfBST() {
    }

    static class TreeNode {
        Integer val;
        TreeNode left;
        TreeNode right;

        TreeNode(Integer val, TreeNode l, TreeNode r) {
            this.val = val;
            this.left = l;
            this.right = r;
        }
    }

    static class ResultHolder {
        Integer data = 0;
    }

    /**
     * 938. Range Sum of BST
     * 
     * Given the `root` node of a binary search tree, return the sum of values of all nodes with value between `L` and `R` (inclusive).
     * The binary search tree is guaranteed to have unique values.
     * 
     * Example 1:
     * Input: root = [10,5,15,3,7,null,18], L = 7, R = 15
     * Output: 32
     * 
     * Example 2:
     * Input: root = [10,5,15,3,7,13,18,1,null,6], L = 6, R = 10
     * Output: 23
     * 
     * Note:
     * The number of nodes in the tree is at most 10000.
     * The final answer is guaranteed to be less than 2^31.
     * 
     * 题意：二叉搜索树的区间和
     * 思路：这道题给了一棵二叉搜索树，还给了两个整型数L和R，让返回所有结点值在区间 [L, R] 内的和，就是说找出所有的在此区间内的结点，将其所有结点值累加起来返回即可。最简单粗暴的思路就是遍历所有的结点，对每个结点值都检测其是否在区间内，是的话就累加其值，最后返回累加和即可，
     */
    static int rangeSumBST(TreeNode root, int l, int r) {
        ResultHolder res = new ResultHolder();
        helper(root, l, r, res);
        return res.data;
    }

    private static void helper(TreeNode node, int l, int r, ResultHolder res) {
        if (null == node) {
            return;
        }
        if (node.val >= l && node.val <= r) {
            res.data += node.val;
        }
        helper(node.left, l, r, res);
        helper(node.right, l, r, res);
    }

    private static TreeNode prepareTree() {
        // root = [10,5,15,3,7,null,18], L = 7, R = 15

        TreeNode _18 = new TreeNode(18, null, null);
        TreeNode _3 = new TreeNode(3, null, null);
        TreeNode _7 = new TreeNode(7, null, null);
        TreeNode _15 = new TreeNode(15, null, _18);
        TreeNode _5 = new TreeNode(5, _3, _7);
        TreeNode _10 = new TreeNode(10, _5, _15);
        return _10;
    }

    private static void testRangeSumBST() {
        // root = [10,5,15,3,7,null,18], L = 7, R = 15
        TreeNode tn = prepareTree();
        int l = 7, r = 15;
        int res = rangeSumBST(tn, l, r);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testRangeSumBST();
    }
}
