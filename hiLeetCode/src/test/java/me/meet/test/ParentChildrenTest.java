package me.meet.test;

import java.util.ArrayList;
import java.util.List;

public final class ParentChildrenTest {
    private ParentChildrenTest() {
    }

    static class Parent {

        private static Integer privateStaticInt;

        static {
            privateStaticInt = 1;
            System.out.println("Parent static{} 1");
        }

        static {
            System.out.println("Parent static{} 2");
        }

        private Integer privateInt;
        protected Integer protectedInt;

        public Parent() {
            System.out.println("Parent()");
        }

    }

    static class Children extends Parent {

        private static Integer privateStaticInt;

        static {
            privateStaticInt = 1;
            System.out.println("Children static{} 1");
        }

        static {
            System.out.println("Children static{} 2");
        }

        public Children() {
            System.out.println("Children()");
        }

        public Children(Integer privateInt, Integer protectedInt) {
//            super();
            super.privateInt = privateInt;
            this.protectedInt = protectedInt;
            System.out.println("Children(Integer privateInt, Integer protectedInt)");
        }

    }

    public static class Singleton {
        private Singleton() {
            System.out.println("Singleton()");
        }

        private static class Inner {
            private Inner() {
                System.out.println("Singleton.Inner()");
            }

            private static Singleton INSTANCE = new Singleton();
        }

        public static Singleton getInstance() {
            return Inner.INSTANCE;
        }
    }

    static class ObjRef {
        private Integer data;

        public Integer getData() {
            return data;
        }

        public void setData(Integer data) {
            this.data = data;
        }
    }

    private static void testClassLoadSeq() {
        // 类加载顺序问题
        ParentChildrenTest.Children children2 = new ParentChildrenTest.Children(1, 2);
        ParentChildrenTest.Children children1 = new ParentChildrenTest.Children();
    }

    private static void testParentChildrenClass() {
        // 父类/子类 问题
        ParentChildrenTest.Children children1 = new ParentChildrenTest.Children();
        ParentChildrenTest.Parent parent1 = children1;

        ParentChildrenTest.Parent parent2 = new ParentChildrenTest.Children();
        ParentChildrenTest.Children children3 = (ParentChildrenTest.Children) parent2;

//        ParentChildrenTest.Children children3 = (ParentChildrenTest.Children) new ParentChildrenTest.Parent(); // java.lang.ClassCastException
    }

    private static void testSingleton() {
        Singleton singleton1 = Singleton.getInstance();
        Singleton singleton2 = Singleton.getInstance();

    }

    private static void testObjectRef() {
        List<ObjRef> list = new ArrayList<ObjRef>();
        for (int i = 0; i < 100; i++) {
            ObjRef one = new ObjRef();
            list.add(one);
            setObjRef(one, i);
        }
        System.out.println(list);
    }

    private static void setObjRef(ObjRef one, int i) {
        if (null == one) {
            return;
        }
        one.setData(i);
    }

    public static void main(String[] args) {
        testObjectRef();

        testClassLoadSeq();
        testParentChildrenClass();
        testSingleton();

        System.out.println();
    }

}
