package me.meet.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public final class NIOTest {
    private NIOTest() {
    }

    private static void simpleIOSample1() {
        String fileInputPath = "/Users/tony/a";
        String fileOutputPath = "/Users/tony/b";
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(new File(fileInputPath));
            fos = new FileOutputStream(new File(fileOutputPath));
            int ch;
            while ((ch = fis.read()) != -1) {
//                System.out.println((char) ch);
                fos.write(ch);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 字节流转换成字符流
    private static void simpleIOSample2() {
        String fileInputPath = "/Users/tony/a";
        String fileOutputPath = "/Users/tony/b";
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fileInputPath)));
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOutputPath)));
            String s;
            StringBuilder sb = new StringBuilder();
            while ((s = br.readLine()) != null) {
//                System.out.println(s);
                bw.write(s);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != bw) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 读取大文件
    private static void simpleIOSample3() throws IOException {
        String fileInputPath = "/Users/tony/a";
        String fileOutputPath = "/Users/tony/b";
        BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(fileInputPath)), "utf-8"), 10 * 1024 * 1024); // 用10M的缓冲读取文本文件
        String line = "";
        while ((line = reader.readLine()) != null) {
            //TODO: write your business
        }
    }

    // 读取大文件 better
    private static void simpleIOSample4() throws IOException {
        String fileInputPath = "/Users/tony/a";
        String fileOutputPath = "/Users/tony/b";
        FileChannel read = new FileInputStream(fileInputPath).getChannel();
        FileChannel writer = new RandomAccessFile(fileOutputPath, "rw").getChannel();
        long i = 0;
        long size = read.size() / 30;
        ByteBuffer bb, cc = null;

        while (i < read.size() && (read.size() - i) > size) {
            bb = read.map(FileChannel.MapMode.READ_ONLY, i, size);
            cc = writer.map(FileChannel.MapMode.READ_WRITE, i, size);
            cc.put(bb);
            i += size;
            bb.clear();
            cc.clear();
        }
        bb = read.map(FileChannel.MapMode.READ_ONLY, i, read.size() - i);
        cc.put(bb);
        bb.clear();
        cc.clear();
        read.close();
        writer.close();
    }

    //
    private static void simpleIOSample5() throws IllegalAccessException {
        Test t = new Test();

        long start = System.currentTimeMillis();
        Class cls = t.getClass();
        Field[] fields = cls.getDeclaredFields();
        //得到属性
        Field field = fields[0];
        //打开私有访问
        field.setAccessible(true);
        //获取属性
        String name = field.getName();
        //获取属性值
        Object value = field.get(t);
        System.out.println(System.currentTimeMillis()-start);
        //一个个赋值
        System.out.println(field.getName()+":"+value+";\n");

        start = System.currentTimeMillis();
        int result = t.getData();
        System.out.println(System.currentTimeMillis()-start);
        System.out.println(result);
    }



    public static void main(String[] args) throws IOException, IllegalAccessException {
        long start = System.currentTimeMillis();
//        simpleIOSample1();
//        simpleIOSample2();
//        simpleIOSample4();
        simpleIOSample5();
        System.out.println(System.currentTimeMillis()-start);
    }

    private static class Test {
        private int data = 666;
        public int getData() {
            return data;
        }
        public void setData(int data) {
            this.data = data;
        }
    }
}
