package me.meet.test;

import java.util.logging.Level;
import java.util.logging.Logger;


public class ClassLoaderTest {
    class MyClassLoader extends ClassLoader {

    }

    public static void main(String args[]) {
        try {
            //printing ClassLoader of this class
            System.out.println("ClassLoaderTest.getClass().getClassLoader() : "
                    + ClassLoaderTest.class.getClassLoader());

            //trying to explicitly load this class again using Extension class loader
            Class.forName("me.meet.test.ClassLoaderTest", true
                    , ClassLoaderTest.class.getClassLoader().getParent());
            Class clazz = int.class;

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClassLoaderTest.class.getName()).log(Level.SEVERE, null, ex);
//            System.out.println(ClassLoaderTest.class.getName());
        }
    }

}
