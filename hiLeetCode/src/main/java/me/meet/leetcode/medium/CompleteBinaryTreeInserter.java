package me.meet.leetcode.medium;

import java.util.*;

public class CompleteBinaryTreeInserter {
    static class TreeNode {
        Integer val;
        TreeNode left;
        TreeNode right;

        TreeNode(Integer val) {
            this.val = val;
        }
    }

    /**
     * Complete Binary Tree Inserter 完全二叉树插入器
     * 
     * A complete binary tree is a binary tree in which every level, except possibly the last, is completely filled, and all nodes are as far left as possible.
     * Write a data structure CBTInserter that is initialized with a complete binary tree and supports the following operations:
     * 1. CBTInserter(TreeNode root) initializes the data structure on a given tree with head node root;
     * 2. CBTInserter.insert(int v) will insert a TreeNode into the tree with value node.val = v so that the tree remains complete, and returns the value of the parent of the inserted TreeNode;
     * 3. CBTInserter.get_root() will return the head node of the tree.
     * 
     * Example 1:
     * Input: inputs = ["CBTInserter","insert","get_root"], inputs = [[[1]],[2],[]]
     * Output: [null,1,[1,2]]
     * 
     * Example 2:
     * Input: inputs = ["CBTInserter","insert","insert","get_root"], inputs = [[[1,2,3,4,5,6]],[7],[8],[]]
     * Output: [null,3,4,[1,2,3,4,5,6,7,8]]
     * 
     * Note:
     * 1. The initial given tree is complete and contains between 1 and 1000 nodes.
     * 2. CBTInserter.insert is called at most 10000 times per test case.
     * 3. Every value of a given or inserted node is between 0 and 5000.
     * 
     * 
     * 题意：完全二叉树插入器
     */
    static class CBTInserter1 {
        final TreeNode root;
        final Deque<TreeNode> deque;

        public CBTInserter1(TreeNode root) {
            this.root = root;
            this.deque = new LinkedList<>();

            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);

            // BFS to populate deque
            while (!queue.isEmpty()) {
                TreeNode node = queue.poll();
                if (node.left == null || node.right == null) {
                    deque.offerLast(node);
                }
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }

        public int insert(int v) {
            TreeNode node = deque.peekFirst();
            deque.offerLast(new TreeNode(v));
            if (node.left == null) {
                node.left = deque.peekLast();
            } else {
                node.right = deque.peekLast();
                deque.pollFirst();
            }

            return node.val;
        }

        public TreeNode getRoot() {
            return root;
        }
    }

    private static void testCBTInserter1() {
        CBTInserter1 cbti1 = new CBTInserter1(new TreeNode(1));
        cbti1.insert(2);
        TreeNode res1 = cbti1.getRoot();
        System.out.println(res1);

        int[] arr = new int[]{2, 3, 4, 5, 6};
        CBTInserter1 cbti2 = new CBTInserter1(new TreeNode(1));
        for (int i : arr) {
            cbti2.insert(i);
        }
        cbti2.insert(7);
        cbti2.insert(8);
        TreeNode res2 = cbti2.getRoot();
        System.out.println(res2);
    }


//    static class CBTInserter2 {
//        private final TreeNode root;
//        private final Deque<TreeNode> q;
//
//        CBTInserter2(TreeNode root) {
//            this.root = root;
//            q = new LinkedList<>();
//            q.push(root);
//
//            for (; !q.isEmpty(); ) {
//                TreeNode t = q.peek();
//                if (null == t.left || null == t.right) {
//                    break;
//                }
//                q.push(t.left);
//                q.push(t.right);
//                q.pop();
//            }
//        }
//
//        int insert(int v) {
//            TreeNode node = new TreeNode(v);
//            TreeNode t = q.peek();
//            if (null == t.left) {
//                t.left = node;
//            } else {
//                t.right = node;
//                q.push(t.left);
//                q.push(t.right);
//                q.pop();
//            }
//            return t.val;
//        }
//
//        TreeNode getRoot() {
//            return this.root;
//        }
//    }
//
//    private static void testCBTInserter2() {
//        int[] arr = new int[]{2, 3, 4, 5, 6};
//        CBTInserter2 cbti2 = new CBTInserter2(new TreeNode(1));
//        for (int i : arr) {
//            cbti2.insert(i);
//        }
//        cbti2.insert(7);
//        cbti2.insert(8);
//        TreeNode res2 = cbti2.getRoot();
//        System.out.println(res2);
//    }

    static class CBTInserter3 {
        final List<TreeNode> tree;
        CBTInserter3(TreeNode root) {
            this.tree = new LinkedList<>();

            tree.add(root);
            for (int i = 0; i < tree.size(); i++) {
                TreeNode it = tree.get(i);
                if (null != it.left) {
                    tree.add(it.left);
                }
                if (null != it.right) {
                    tree.add(it.right);
                }
            }
        }

        int insert(int v) {
            TreeNode vt = new TreeNode(v);
            int n = tree.size();

            tree.add(vt);
            TreeNode tmp = tree.get((n-1)/2);
            if (n % 2 == 1) {
                tmp.left = vt;
            } else {
                tmp.right = vt;
            }
            return tmp.val;
        }

        TreeNode getRoot() {
            return tree.get(0);
        }
    }

    private static void testCBTInserter3() {
        CBTInserter3 cbti2 = new CBTInserter3(new TreeNode(1));

        int[] arr = new int[]{2, 3, 4, 5, 6};
        for (int i : arr) {
            cbti2.insert(i);
        }
        cbti2.insert(7);
        cbti2.insert(8);
        TreeNode res2 = cbti2.getRoot();
        System.out.println(res2);
    }

    public static void main(String[] args) {
        testCBTInserter1();
//        testCBTInserter2();
        testCBTInserter3();
    }
}
