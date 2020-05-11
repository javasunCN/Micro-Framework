package thread;

import com.google.common.collect.Lists;
import org.junit.Test;

import javax.naming.Reference;
import java.lang.reflect.Field;
import java.util.List;

public class ThreadLocalTest {

    private List<String> messages = Lists.newArrayList();

    public static final ThreadLocal<ThreadLocalTest> local = ThreadLocal.withInitial(ThreadLocalTest::new);

    /**
     * 1. ThreadLocal代码示例
     */
    @Test
    public void testThreadLocal() {
        ThreadLocalTest.add("一枝花算不算浪漫");
        System.out.println(local.get().messages);
        ThreadLocalTest.clear();
    }

    // 这个值很特殊，它是斐波那契数  也叫 黄金分割数。
    private static final int HASH_INCREMENT = 0x61c88647;
    /**
     * 桶的平均Hash算法
     */
    @Test
    public void testBuckHash() {
        int hashCode = 0;
        for (int i = 0; i < 16; i++) {
            hashCode = i * HASH_INCREMENT + HASH_INCREMENT;
            int buck = hashCode & 15;
            System.out.println( i + " 在桶的位置: " + buck);

        }
    }

    public static void add(String message) {
        local.get().messages.add(message);
    }

    public static List<String> clear() {
        List<String> messages = local.get().messages;
        local.remove();

        System.out.println("size: " + local.get().messages.size());
        return messages;
    }


    /**
     * 2. 测试ThreadLocal的key的GC回收
     */
    @Test
    public void testThreadLocalMapKeyGC() throws InterruptedException {
        Thread t = new Thread(()-> threadLocalMapKeyGC("abc",false));
        t.start();
        t.join();
        System.out.println("--gc后--");
        Thread t2 = new Thread(() -> threadLocalMapKeyGC("def", true));
        t2.start();
        t2.join();
    }

    /**
     * 3. 子线程获取父线程的线程副本
     *
     */
    @Test
    public void testInheritableThreadLocal() {

        ThreadLocal threadLocal = new ThreadLocal();
        threadLocal.set(" new ThreadLocal() 线程消息");

        ThreadLocal inheritableThreadLocal = new InheritableThreadLocal<>();
        inheritableThreadLocal.set(" new InheritableThreadLocal<>() 线程消息");

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("子线程获取父类threadLocal数据：" + threadLocal.get());
                System.out.println("子线程获取父类inheritableThreadLocal数据：" + inheritableThreadLocal.get());
            }
        }).start();

        System.out.println("结束");
    }

    /**
     * java.lang.ThreadLocal.ThreadLocalMap#cleanSomeSlots(int, int) 算法
     */
    @Test
    public void testThreadLocalThreadLocalMapcleanSomeSlots() {
            int n = 5;
        System.out.println(n>>>=1);
    }

    private static void threadLocalMapKeyGC(String s,boolean isGC)  {
        try {
            // key为弱引用
            // new ThreadLocal<>().set(s);
            // key为强引用
            ThreadLocal<Object> keyLocal = new ThreadLocal<>();
            keyLocal.set(s);
            if (isGC) {
                System.gc();
            }
            Thread t = Thread.currentThread();
            Class<? extends Thread> clz = t.getClass();
            Field field = clz.getDeclaredField("threadLocals");
            field.setAccessible(true);
            Object threadLocalMap = field.get(t);
            Class<?> tlmClass = threadLocalMap.getClass();
            Field tableField = tlmClass.getDeclaredField("table");
            tableField.setAccessible(true);
            Object[] arr = (Object[]) tableField.get(threadLocalMap);
            for (Object o : arr) {
                // key如果是弱引用垃圾回收后 reference 为null
                if (o != null) {
                    Class<?> entryClass = o.getClass();
                    Field valueField = entryClass.getDeclaredField("value");
                    Field referenceField = entryClass.getSuperclass().getSuperclass().getDeclaredField("referent");
                    valueField.setAccessible(true);
                    referenceField.setAccessible(true);
                    System.out.println(String.format("弱引用key:%s,值:%s", referenceField.get(o), valueField.get(o)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
