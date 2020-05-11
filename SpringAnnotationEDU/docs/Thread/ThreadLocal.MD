# ThreadLocal数据结构

> ThreadLocal对象可以提供线程局部变量，每个线程Thread拥有一份自己的副本变量，多个线程互不干扰。
## ThreadLocal.ThreadLocalMap Map结构

`ThreadLocalMap` 与 `HashMap` 的区别:

```textmate
    HashMap: 数组 + 链表
    ThreadLocalMap: 数组
        ThreadLocalMap的key为弱引用(WeakReference)
            注: 弱引用: 当GC回收时，如果对象只被弱引用关联(没有任何强引用关联它)，那么这个对象就会被回收。
```
 
# GC之后key是否为null

```textmate
    1. 如果 key 是弱引用时，GC回收后 key = null;
        new ThreadLocal<>().set(s);
    2. 如果 key 是强引用时，GC回收后 key != null
        ThreadLocal<Object> keyLocal = new ThreadLocal<>();
        keyLocal.set(s);
```

# ThreadLocal.set()原理

```textmate
    1. 获取当前线程 Thread.currentThread();
    2. 获取当前线程 ThreadLocalMap: ThreadLocal.ThreadLocalMap threadLocals;
    3. 如果当前线程 存在 ThreadLocalMap时，将值写入 当前线程的 map 中；ThreadLocalMap.set(this, value);
    4. 如果当前线程 不存在 ThreadLocalMap时， ThreadLocal.createMap(t, value);
```

### **ThreadLocal.createMap(t, value):创建当前线程的ThreadLocalMap并赋值 **

> threshold = len * 2 / 3; 设置 Entry[]  

```java
        java.lang.ThreadLocal.ThreadLocalMap.ThreadLocalMap(ThreadLocal<?> firstKey, Object firstValue) {
            // 创建Entry.table数组，并初始化大小为 16
            table = new Entry[INITIAL_CAPACITY];
            // 通过位运算计算当前值存放的位置
            int i = firstKey.threadLocalHashCode & (INITIAL_CAPACITY - 1);
            // 将值赋值到当前的线程Map中
            table[i] = new Entry(firstKey, firstValue);
            // 设置当前线程的Entry.table的大小为1
            size = 1;
            // 设置数组允许存储的最大阈值
            setThreshold(INITIAL_CAPACITY);
        }
        // Set the resize threshold to maintain at worst a 2/3 load factor.
        private void setThreshold(int len) {
            threshold = len * 2 / 3;
        }
```

### **ThreadLocalMap.set(this, value):给当前线程赋值**

> size >= threshold - threshold / 4: 真正的数组扩容
> <br/> java.lang.ThreadLocal.ThreadLocalMap.resize: 表的容量加倍

```java
    private void set(ThreadLocal<?> key, Object value) {

        Entry[] tab = table;
        int len = tab.length;
        // 计算值存放的位置
        int i = key.threadLocalHashCode & (len-1);
        // 循环ThreadLocaMap
        // 设置算法：存在的问题是 Hash冲突
        for (Entry e = tab[i];
             e != null;
             e = tab[i = nextIndex(i, len)]) {
            ThreadLocal<?> k = e.get();
            // 如果数组的 Entry[] 存在 写入的key,替换当前的key的值
            if (k == key) {
                e.value = value;
                return;
            }
            // 当前 Entry[] 不存在 key
            if (k == null) {
                replaceStaleEntry(key, value, i);
                return;
            }
        }

        tab[i] = new Entry(key, value);
        int sz = ++size;
        if (!cleanSomeSlots(i, sz) && sz >= threshold)
            rehash();
    }


    private void replaceStaleEntry(ThreadLocal<?> key, Object value, int staleSlot) {
        Entry[] tab = table;
        int len = tab.length;
        Entry e;

        // Back up to check for prior stale entry in current run.
        // We clean out whole runs at a time to avoid continual
        // incremental rehashing due to garbage collector freeing
        // up refs in bunches (i.e., whenever the collector runs).
        int slotToExpunge = staleSlot;
        for (int i = prevIndex(staleSlot, len);
             (e = tab[i]) != null;
             i = prevIndex(i, len))
            if (e.get() == null)
                slotToExpunge = i;

        // Find either the key or trailing null slot of run, whichever
        // occurs first
        for (int i = nextIndex(staleSlot, len);
             (e = tab[i]) != null;
             i = nextIndex(i, len)) {
            ThreadLocal<?> k = e.get();

            // If we find key, then we need to swap it
            // with the stale entry to maintain hash table order.
            // The newly stale slot, or any other stale slot
            // encountered above it, can then be sent to expungeStaleEntry
            // to remove or rehash all of the other entries in run.
            if (k == key) {
                e.value = value;

                tab[i] = tab[staleSlot];
                tab[staleSlot] = e;

                // Start expunge at preceding stale entry if it exists
                if (slotToExpunge == staleSlot)
                    slotToExpunge = i;
                cleanSomeSlots(expungeStaleEntry(slotToExpunge), len);
                return;
            }

            // If we didn't find stale entry on backward scan, the
            // first stale entry seen while scanning for key is the
            // first still present in the run.
            if (k == null && slotToExpunge == staleSlot)
                slotToExpunge = i;
        }

        // If key not found, put new entry in stale slot
        tab[staleSlot].value = null;
        tab[staleSlot] = new Entry(key, value);

        // If there are any other stale entries in run, expunge them
        if (slotToExpunge != staleSlot)
            cleanSomeSlots(expungeStaleEntry(slotToExpunge), len);
    }
```

# ThreadLocalMap Hash算法

# ThreadLocalMap Hash冲突

# ThreadLocalMap.set()详解

# ThreadLocalMap过期key的 `探测式清理` 流程

# ThreadLocalMap扩容机制

# ThreadLocalMap.get()详解

# ThreadLocalMap过期key的 `启发式清理` 流程

# InheritableThreadLocal

# ThreadLocal实战

