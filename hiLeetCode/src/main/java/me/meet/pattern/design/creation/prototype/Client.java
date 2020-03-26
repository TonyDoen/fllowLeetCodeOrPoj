package me.meet.pattern.design.creation.prototype;

public class Client {
    /**
     * 原型模式: 用原型实例指定创建对象的种类，并通过拷贝这些原型创建新的对象。
     *
     * 原型模式主要用于对象的复制，它的核心是就是类图中的原型类Prototype。
     * Prototype类需要具备以下两个条件：
     * 1. 实现Cloneable接口。在java语言有一个Cloneable接口，它的作用只有一个，就是在运行时通知虚拟机可以安全地在实现了此接口的类上使用clone方法。在java虚拟机中，只有实现了这个接口的类才可以被拷贝，否则在运行时会抛出CloneNotSupportedException异常。
     * 2. 重写Object类中的clone方法。Java中，所有类的父类都是Object类，Object类中有一个clone方法，作用是返回对象的一个拷贝，但是其作用域protected类型的，一般的类无法调用，因此，Prototype类需要将clone方法的作用域修改为public类型。
     *
     * 原型模式是一种比较简单的模式，也非常容易理解，实现一个接口，重写一个方法即完成了原型模式。在实际应用中，原型模式很少单独出现。经常与其他模式混用，他的原型类Prototype也常用抽象类来替代。
     *
     *
     * 原型模式的优点：
     * 1. 使用原型模式创建对象比直接 new 一个对象在性能上要好的多，因为Object类的clone方法是一个本地方法，它直接操作内存中的二进制流，特别是复制大对象时，性能的差别非常明显。
     * 2. 使用原型模式的另一个好处是简化对象的创建，使得创建对象就像我们在编辑文档时的复制粘贴一样简单。
     *
     *
     * 原型模式的使用场景：
     * 1. 因为以上优点，所以在需要重复地创建相似对象时可以考虑使用原型模式。比如需要在一个循环体内创建对象，假如对象创建过程比较复杂或者循环次数很多的话，使用原型模式不但可以简化创建过程，而且可以使系统的整体性能提高很多。
     *
     *
     * 原型模式的注意事项：
     * 1. 使用原型模式复制对象不会调用类的构造方法。因为对象的复制是通过调用 Object类的clone方法来完成的，它直接在内存中复制数据，因此不会调用到类的构造方法。不但构造方法中的代码不会执行，甚至连访问权限都对原型模式无效。单例模式中，只要将构造方法的访问权限设置为private型，就可以实现单例。但是clone方法直接无视构造方法的权限，所以，单例模式与原型模式是冲突的，在使用时要特别注意。
     * 2. 深拷贝与浅拷贝。Object类的clone方法只会拷贝对象中的基本的数据类型（8种基本数据类型byte,char,short,int,long,float,double,boolean），对于数组、容器对象、引用对象等都不会拷贝，这就是浅拷贝。如果要实现深拷贝，必须将原型模式中的数组、容器对象、引用对象等另行拷贝。
     * 3. 关于深拷贝和浅拷贝，会发生深拷贝的是java 的 8种基本数据类型和他们的封装类，至于String这个类型需要注意，它是引用数据类型，所以是浅拷贝
     *
     */

    public static void main(String[] args) {
        Prototype p1 = new Prototype();
        Prototype p2 = p1.clone();

        System.out.println(p1 == p2);
        System.out.println(p1.getList() == p2.getList());
    }
}