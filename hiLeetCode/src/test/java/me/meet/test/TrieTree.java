//package me.meet.test;
//
//import com.alibaba.fastjson.JSON;
//
///**
// * 字典树模版，默认只包含26个小写字母
// * 提供hasStr、insert、countPrefix、preWalk、getRoot接口
// */
//public class TrieTree {
//    private final int SIZE = 26;  //每个节点能包含的子节点数，即需要SIZE个指针来指向其孩子
//    private Node root;   //字典树的根节点
//
//    /**
//     * 字典树节点类
//     */
//    private class Node {
//        private boolean isStr;   //标识该节点是否为某一字符串终端节点
//        private int num;         //标识经过该节点的字符串数。在计算前缀包含的时候会用到
//        private Node[] child;    //该节点的子节点
//
//        Node() {
//            child = new Node[SIZE];
//            isStr = false;
//            num = 1;
//        }
//    }
//
//    public TrieTree() {
//        root = new Node();
//    }
//
//    /**
//     * 检查字典树中是否完全包含字符串word
//     */
//    public boolean hasStr(String word) {
//        Node pNode = this.root;
//
//        //逐个字符去检查
//        for (int i = 0; i < word.length(); i++) {
//            int index = word.charAt(i) - 'a';
//            //在字典树中没有对应的节点，或者word字符串的最后一个字符在字典树中检测对应节点的isStr属性为false，则返回false
//            if (pNode.child[index] == null
//                    || (i + 1 == word.length() && !pNode.child[index].isStr)) {
//                return false;
//            }
//            pNode = pNode.child[index];
//        }
//
//        return true;
//    }
//
//    /**
//     * 在字典树中插入一个单词
//     */
//    public void insert(String word) {
//        if (word == null || word.isEmpty()) {
//            return;
//        }
//        Node pNode = this.root;
//        for (int i = 0; i < word.length(); i++) {
//            int index = word.charAt(i) - 'a';
//            if (pNode.child[index] == null) {   //如果不存在节点，则new一个一节点插入字典树
//                Node tmpNode = new Node();
//                pNode.child[index] = tmpNode;
//            } else {
//                pNode.child[index].num++;       //如果字典树中改路径上存在节点，则num加1，表示在该节点上有一个新的单词经过
//            }
//            pNode = pNode.child[index];
//        }
//        pNode.isStr = true;
//    }
//
//    /**
//     * 统计在字典树中有多少个单词是以str为前缀的
//     */
//    public int countPrefix(String str) {
//        Node pNode = this.root;
//        for (int i = 0; i < str.length(); i++) {
//            int index = str.charAt(i) - 'a';
//            if (pNode.child[index] == null) {
//                return 0;
//            } else {
//                pNode = pNode.child[index];
//            }
//        }
//
//        return pNode.num;
//    }
//
//    /**
//     * 先序遍历
//     */
//    public void preWalk() {
//        preWalk(this.root);
//    }
//    public void preWalk(Node root) {
//        Node pNode = root;
//        for (int i = 0; i < SIZE; i++) {
//            if (pNode.child[i] != null) {
//                System.out.print((char) ('a' + i) + "->");
//                preWalk(pNode.child[i]);
//            }
//        }
//    }
//
//    /**
//     * 返回字典树根节点
//     */
//    public Node getRoot() {
//        return root;
//    }
//
//    enum KKK {
//        ABC,
//        CDE,
//        FJH,
//    }
//    enum QQQ {
//        YES(1, "yes"),
//        NO(2, "no"),
//        ;
//        private Integer code;
//        private String desc;
//        QQQ(Integer code, String desc) {
//            this.code = code;
//            this.desc = desc;
//        }
//    }
//
//    public static void main(String[] args) {
//        TrieTree trieTree = new TrieTree();
//        trieTree.insert("hello");
//        trieTree.insert("halo");
//        trieTree.insert("helloow");
//        trieTree.insert("yellow");
//        trieTree.preWalk();
//        System.out.println(trieTree.countPrefix("hel"));
//        System.out.println();
//
//        System.out.println(JSON.parseObject("2333", Integer.class));
//        System.out.println(JSON.parseObject("{\"SIZE\":2333,\"root\":{\"isStr\":false,\"num\":1,\"child\":[]}}", TrieTree.class));
//        System.out.println(JSON.parseObject("1", KKK.class));
//        System.out.println(JSON.parseObject("0", QQQ.class));
//        System.out.println();
//    }
//}
