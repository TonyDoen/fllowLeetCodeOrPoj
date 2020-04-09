package me.meet.leetcode.medium;

public final class MirrorReflection {
    private MirrorReflection() {
    }

    /**
     * There is a special square room with mirrors on each of the four walls.  Except for the southwest corner, there are receptors on each of the remaining corners, numbered 0, 1, and 2.
     * The square room has walls of length p, and a laser ray from the southwest corner first meets the east wall at a distance q from the 0th receptor.
     * Return the number of the receptor that the ray meets first.  (It is guaranteed that the ray will meet a receptor eventually.)
     *
     * Example 1:
     * Input: p = 2, q = 1
     * Output: 2
     *
     * Explanation: The ray meets receptor 2 the first time it gets reflected back to the left wall.
     *
     * Note:
     * 1 <= p <= 1000
     * 0 <= q <= p
     *
     * 题意：
     * 这道题给了我们一个正方形的房间，说是四面都是镜子墙，然后在西南角有一个激光发射器，其余三个角都有接收装置，问我们最终激光会被哪个接收器接收。第一次读题时这句 "Return the number of the receptor that the ray meets first." 让博主理解错误了，以为是让返回接收器的个数，以为接收器也能反射激光到其对角的接收器，那么接收器2和0互相反射，就是返回经过了2个接收器，接收器1返回到反射点，就是返回经过了1个接收点，想的是一套一套的，结果人家让返回的是接收器的标号，
     * 个人觉得将 number 改为 index 会减少些歧义。无所谓了，反正最终搞懂了题意就行了。其实这道题的正确解法还挺难想的，因为大家很容易走进的误区就是研究反射角啥的，然后算具体反射到了哪一个位置，再算下一个位置，其实这样都将题目变复杂了。博主把这一类型归为脑筋急转弯 Brain Teaser，一般都有很巧妙的数学解法，并不需要太复杂的算法
     */

    static int mirrorReflection(int p, int q) {
        while (p % 2 == 0 && q % 2 == 0) {
            p /= 2;
            q /= 2;
        }
        if (p % 2 == 0) return 2;
        if (q % 2 == 0) return 0;
        return 1;
    }

    public static void main(String[] args) {
        int p = 2, q = 1;
        System.out.println(mirrorReflection(p, q));
    }
}
