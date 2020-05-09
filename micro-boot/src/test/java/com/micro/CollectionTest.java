package com.micro;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CollectionTest {

    static final int HASH_BITS = 0x7fffffff;
    @Test
    public void testConcurrentHashMap() {
        Map<String, Object> map = new ConcurrentHashMap<>();
        /*for (int i=0; i < 20; i++) {
            map.put("key"+i, i);
        }*/
        // 获取hashCode
        String key = "汉字";
        int hashCode = key.hashCode();
        System.out.println(hashCode);
        int hCode = spread(hashCode);
        System.out.println(hCode);
    }

    static final int spread(int h) {
        return (h ^ (h >>> 16)) & HASH_BITS;
    }
}
