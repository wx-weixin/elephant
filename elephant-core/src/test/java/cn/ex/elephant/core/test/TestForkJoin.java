package cn.ex.elephant.core.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.jupiter.api.Test;

/**
 * @author weixin 2023/6/5 2:10 PM
 */
public class TestForkJoin {

    /**
     * 测试 list的并行流
     */
    @Test
    public void test1(){
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(i);
        }
        Map<String,Integer> map= new ConcurrentHashMap<>();
        list.parallelStream().forEach(integer -> {
            Thread thread = Thread.currentThread();
            map.merge(thread.getName(), 1, Integer::sum);
        });
        System.out.println(map.size());
        int a =0;
        for (Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey()+" "+entry.getValue());
            a= a+ entry.getValue();
        }
        System.out.println(a);
    }
}
