package ru.javawebinar.topjava;

/**
 * @see <a href="http://topjava.herokuapp.com">Demo</a>
 * @see <a href="https://github.com/JavaOPs/topjava">Initial project</a>
 */
public class Main {

    static void test(String s) {}
    static void test(Object s) {}

    public static void main(String[] args) {
        System.out.format("Hello Topjava Enterprise!");
        test(null);
        Integer.getInteger("");
    }
}
