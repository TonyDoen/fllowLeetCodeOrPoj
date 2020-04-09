package me.meet.leetcode.medium;

import java.util.*;

public final class KeysAndRooms {
    private KeysAndRooms() {}

    /**
     * url: https://www.cnblogs.com/grandyang/p/10415773.html
     * url: https://leetcode.com/problems/keys-and-rooms/discuss/133944/Java-8-lines
     *
     * There are N rooms and you start in room 0.  Each room has a distinct number in 0, 1, 2, ..., N-1, and each room may have some keys to access the next room.
     * Formally, each room i has a list of keys rooms[i], and each key rooms[i][j] is an integer in [0, 1, ..., N-1] where N = rooms.length.  A key rooms[i][j] = v opens the room with number v.
     * Initially, all the rooms start locked (except for room 0).
     * You can walk back and forth between rooms freely.
     * Return true if and only if you can enter every room.
     *
     * Example 1:
     * Input: [[1],[2],[3],[]]
     * Output: true
     *
     * Explanation:
     * We start in room 0, and pick up key 1.
     * We then go to room 1, and pick up key 2.
     * We then go to room 2, and pick up key 3.
     * We then go to room 3.  Since we were able to go to every room, we return true.
     *
     * Example 2:
     * Input: [[1,3],[3,0,1],[2],[0]]
     * Output: false
     *
     * Explanation: We can't enter the room with number 2.
     *
     * Note:
     * 1 <= rooms.length <= 1000
     * 0 <= rooms[i].length <= 1000
     * The number of keys in all rooms combined is at most 3000.
     *
     *
     * 题意：钥匙与房间
     * 这道题给了我们一些房间，房间里有一些钥匙，用钥匙可以打开对应的房间，说是起始时在房间0，问我们最终是否可以打开所有的房间。
     * 思路：
     * 这是一道典型的有向图的遍历的题，邻接链表都已经帮我们建立好了，我们直接遍历就好了，这里先用BFS来遍历。使用一个HashSet来记录访问过的房间，先把0放进去，然后使用queue来辅助遍历，同样将0放入。之后进行典型的BFS遍历，取出队首的房间，然后遍历其中的所有钥匙，若该钥匙对应的房间已经遍历过了，直接跳过，否则就将钥匙加入HashSet。此时我们看若HashSet中的钥匙数已经等于房间总数了，直接返回true，因为这表示所有房间已经访问过了，否则就将钥匙加入队列继续遍历。最后遍历结束后，就看HashSet中的钥匙数是否和房间总数相等即可，
     *
     */
    static boolean canVisitAllRooms(int[][] rooms) {
        HashSet<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new ArrayDeque<>();
        visited.add(0); // 题意可知
        queue.add(0);

        while(!queue.isEmpty()) {
            Integer t = queue.poll();
            for (int key : rooms[t]) {
                if (visited.contains(key)) {
                    continue;
                }
                if (visited.size() == rooms.length) {
                    return true;
                }
                visited.add(key);
                queue.add(key);
            }
        }
        return visited.size() == rooms.length;
    }


    /**
     * 居然递归是最快的。。。。
     */
    static boolean canVisitAllRooms(List<List<Integer>> rooms) {
        Set<Integer> opened = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        opened.add(0);
        queue.add(0);

        while(!queue.isEmpty()) {
            int index = queue.poll();
            for (int key : rooms.get(index)) {
                if (!opened.contains(key)) {
                    opened.add(key);
                    queue.add(key);
                }
            }
        }
        return opened.size() == rooms.size();
    }

    /**
     * 居然递归是最快的。。。。
     */
    static boolean canVisitAllRooms3(List<List<Integer>> rooms) {
        Set<Integer> visited = new HashSet<>();
        traverse(rooms, 0, visited);
        return visited.size() == rooms.size();
    }

    static void traverse(List<List<Integer>> rooms, int start, Set<Integer> visited) {
        visited.add(start);
        for (int neighbor : rooms.get(start)) {
            if (visited.contains(neighbor)) continue;
            traverse(rooms, neighbor, visited);
        }
    }

    public static void main(String[] args) {
        int[][] rooms = new int[][]{{1},{2},{3},{}};
        System.out.println(canVisitAllRooms(rooms));

        rooms = new int[][]{{1,3},{3,0,1},{2},{0}};
        System.out.println(canVisitAllRooms(rooms));

    }
}
