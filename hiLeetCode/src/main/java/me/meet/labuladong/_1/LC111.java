package me.meet.labuladong._1;

import me.meet.labuladong.common.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * BFS
 * 
 * int bfs(Node start, Node target) {
 *     Queue<Node> q;     //
 *     Set<Node> visited; // 避免走回头路
 * 
 *     q.offer(start);    // 将起点加入队列
 *     visited.add(start);
 *     int step = 0;      // 记录扩散的步数
 * 
 *     while (q.notEmpty()) {
 *         int sz = q.size();
 * 
 *         // 将当前队列中的所有节点向四周扩散
 *         for (int i = 0; i < sz; i++) {
 *             Node cur = q.poll();
 *             // 重点： 判断是否到达终点
 *             if (cur.equals(target)) {
 *                 return step;
 *             }
 * 
 *             // 将 cur 相邻节点加入队列
 *             for (Node x : cur.adj()) {
 *                 if (!visited.contains(x)) {
 *                     q.offer(x);
 *                     visited.add(x);
 *                 }
 *             }
 *         }
 * 
 *         // 重点： 更新布数
 *         step++;
 *     }
 * }
 */

public final class LC111 {
    private LC111() {
    }

    /**
     * LeetCode 第 111 题: 判断一棵二叉树的最小高度
     *
     * 思路：
     * 1. 显然起点就是 root 根节点,终点就是最靠近根节点的那个「叶子节点」
     * 2. 叶子节点就是两个子节点都是 null 的节点 => if (cur.left == null && cur.right == null) // 到达叶子节点
     */
    static int minDepth(TreeNode node) {
        if (null == node) {
            return 0;
        }

        Queue<TreeNode> q = new LinkedList<>();
        q.offer(node);
        int depth = 1; // root 本身就是一层,depth 初始化为 1

        while (!q.isEmpty()) {
            int sz = q.size();
            // 将当前队列中的所有节点向四周扩散
            for (int i = 0; i < sz; i++) {
                TreeNode cur = q.poll();

                // 重点： 判断是否到达终点
                assert cur != null;
                if (null == cur.getLeft() && null == cur.getRight()) {
                    return depth;
                }

                // 将 cur 相邻节点加入队列
                if (null != cur.getLeft()) {
                    q.offer(cur.getLeft());
                }
                if (null != cur.getRight()) {
                    q.offer(cur.getRight());
                }
            }

            // 重点： 更新布数
            depth++;
        }

        return depth;
    }

    private static void testMinDepth0() {
        TreeNode node = TreeNode.prepareTree2();
        node.println();
        
        int depth = minDepth(node);
        System.out.println("MinDepth: "+depth);
    }

    public static void main(String[] args) {
        testMinDepth0();
    }
}

