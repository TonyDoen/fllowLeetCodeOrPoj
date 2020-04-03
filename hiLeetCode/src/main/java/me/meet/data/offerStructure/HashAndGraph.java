package me.meet.data.offerStructure;

import java.util.LinkedHashMap;
import java.util.Map;

public final class HashAndGraph {
    private HashAndGraph() {
    }

    /**
     * 034-第一个只出现一次的字符
     * 
     * 题目：
     * 第一个只出现一次的字符
     * 
     * 题目描述：
     * 在一个字符串(0<=字符串长度<=10000，全部由字母组成)中找到第一个只出现一次的字符,
     * 并返回它的位置, 如果没有则返回 -1（需要区分大小写）.
     * 
     * 思路：
     * 1、遍历字符串，Hash存储字符串每个字符出现的次数
     * 2、顺序遍历上面存储的结果，如果该字符出现次数为1次，则找到该字符及其位置
     */
    static int firstNotRepeatingChar(String src) {
        if (null == src || src.isEmpty()) {
            return -1;
        }

        Map<Character, String> m = new LinkedHashMap<>(); // 有序
        char[] arr = src.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            char c = arr[i];
            String idxCnt = m.get(c);
            if (null == idxCnt) {
                m.put(c, 1 + "|" + i); // cnt and first idx
            } else {
                String[] piece = idxCnt.split("\\|");
                idxCnt = (Integer.valueOf(piece[0]) + 1) + "|" + piece[1];
                m.put(c, idxCnt);
            }
        }

        for (Map.Entry<Character, String> entry : m.entrySet()) {
            String value = entry.getValue();
            if (value.startsWith("1|")) {
                return Integer.valueOf(value.split("\\|")[1]);
            }
        }
        return -1;
    }

    private static void testFirstNotRepeatingChar() {
        int res = firstNotRepeatingChar("google");
        System.out.println(res);
    }


    /**
     * 065-矩阵中的路径(回溯法)
     * 
     * 请设计一个函数，用来判断在一个矩阵中是否存在一条包含某字符串所有字符的路径。
     * 路径可以从矩阵中的任意一个格子开始，每一步可以在矩阵中向左，向右，向上，向下移动一个格子。
     * 如果一条路径经过了矩阵中的某一个格子，则该路径不能再进入该格子。
     * a b c e
     * s f c s
     * a d e e
     * 矩阵中包含一条字符串"bcced“的路径，但是矩阵中不包含”abcb"路径，因为字符串的第一个字符b占据了矩阵中的第一行第二个格子之后，路径不能再次进入该格子。
     * 
     * 思路：
     * 1. 首先确定利用回溯法进行求解。确立解空间-子集树空间。
     * 2. 利用深度优先搜索。
     * 3. 确立剪枝条件。
     *    1>搜索越出矩阵范围，停止向下搜索，剪枝。
     *    2>不是目标字符，停止向下搜索，剪枝。
     *    3>已经被搜索比较过，停止向下所搜，剪枝。
     * 
     * 具体做法：
     * 1、根据给定数组，初始化一个标志位数组，初始化为false，表示未走过，true表示已经走过，不能走第二次
     * 2、根据行数和列数，遍历数组，先找到一个与str字符串的第一个元素相匹配的矩阵元素，进入递归hasPath
     * 3、根据i和j先确定一维数组的位置，因为给定的matrix是一个一维数组
     * 4、确定递归终止条件：越界，当前找到的矩阵值不等于数组对应位置的值，已经走过的，这三类情况，都直接false，说明这条路不通
     * 5、若k，就是待判定的字符串str的索引已经判断到了最后一位，此时说明是匹配成功的
     * 6、下面就是本题的精髓，递归不断地寻找周围四个格子是否符合条件，只要有一个格子符合条件，就继续再找这个符合条件的格子的四周是否存在符合条件的格子，直到k到达末尾或者不满足递归条件就停止。
     * 7、走到这一步，说明本次是不成功的，我们要还原一下标志位数组index处的标志位，进入下一轮的判断。
     */
    static boolean hasPath(char[] matrix, int rows, int cols, char[] str) {
        if (matrix == null || matrix.length == 0 || str == null || str.length == 0 || matrix.length != rows * cols || rows <= 0 || cols <= 0 || rows * cols < str.length) {
            return false;
        }
        // 标志位，初始化为false
        boolean[] visited = new boolean[matrix.length];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // 循环遍历二维数组，找到起点等于str第一个元素的值，再递归判断四周是否有符合条件的----回溯法
                if (hasPath(matrix, i, j, rows, cols, visited, str, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static final int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}}; // 右左下上

    // judge(初始矩阵，索引行坐标i，索引纵坐标j，矩阵行数，矩阵列数，待判断的字符串，字符串索引初始为0即先判断字符串的第一位)
    private static boolean hasPath(char[] matrix, int i, int j, int rows, int cols, boolean[] flag, char[] str, int k) {
        // 先根据i和j计算匹配的第一个元素转为一维数组的位置
        int index = i * cols + j;
        // 递归终止条件
        if (i < 0 || j < 0 || i >= rows || j >= cols || matrix[index] != str[k] || flag[index] == true)
            return false;
        // 若k已经到达str末尾了，说明之前的都已经匹配成功了，直接返回true即可
        if (k == str.length - 1)
            return true;
        // 要走的第一个位置置为true，表示已经走过了
        flag[index] = true;

        // 回溯，递归寻找，每次找到了就给k加一，找不到，还原
        for (int[] dir : dirs) {
            if (hasPath(matrix, i + dir[0], j + dir[1], rows, cols, flag, str, k + 1)) {
                return true;
            }
        }
        // 走到这，说明这一条路不通，还原，再试其他的路径
        flag[index] = false;
        return false;
    }

    private static void testHasPath() {
        char[] matrix = {'a', 'b', 'c', 'e', 's', 'f', 'c', 's', 'a', 'd', 'e', 'e'}, str = {'b', 'c', 'c', 'e', 'd'};
        int rows = 3, cols = 4;
        boolean res = hasPath(matrix, rows, cols, str);
        System.out.println(res);
    }

    /**
     * 066-机器人的运动范围
     * 
     * 地上有一个m行和n列的方格。一个机器人从坐标0,0的格子开始移动，每一次只能向左，右，上，下四个方向移动一格，但是不能进入行坐标和列坐标的数位之和大于k的格子。
     * 例如，当k为18时，机器人能够进入方格（35,37），因为3+5+3+7 = 18。但是，它不能进入方格（35,38），因为3+5+3+8 = 19。
     * 请问该机器人能够达到多少个格子？
     * 
     * 1. 依然利用回溯法的子集树解空间思想。 确定解空间-子集树。
     * 2. 利用深度优先搜索。
     * 3. 确定剪枝条件。
     *    1>搜索到矩阵边界外，剪枝。
     *    2>该位置已经被搜索过了，剪枝。
     *    3>和大于K的，剪枝。
     *    
     * 需注意 的一点是，这里搜索过的位置不需要回溯了，也就是说不在搜索这里。
     */
    static int countMove(int threshold, int row, int col) {
        boolean[] visited = new boolean[row * col];
        return doCountMove(visited, threshold, 0, 0, row, col);
    }

    private static int doCountMove(boolean[] visited, int threshold, int i, int j, int row, int col) {
        int idx = i * col + j; // 先根据i和j计算匹配的第一个元素转为一维数组的位置
        if (i < 0 || j < 0 || i > row || j > col || sumInt(i) + sumInt(j) > threshold || true == visited[idx]) {
            return 0;
        }
        visited[idx] = true;
        int result = 1;
        for (int[] dir : dirs) {
            result += doCountMove(visited, threshold, i + dir[0], j + dir[1], row, col);
        }
        return result;
    }

    private static int sumInt(int i) {
        int result = 0;
        for (; i > 0; ) {
            result += i % 10;
            i = i / 10;
        }
        return result;
    }

    private static void testCountMove() {
        int threshold = 9;
        int row = 100;
        int col = 100;
        int res = countMove(threshold, row, col);
        System.out.println(res);
    }

    public static void main(String[] args) {
        // 034-第一个只出现一次的字符
        testFirstNotRepeatingChar();
        // 065-矩阵中的路径(回溯法)
        testHasPath();
        // 066-机器人的运动范围
        testCountMove();
    }
}
