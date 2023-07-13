package cn.ex.elephant.core.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * @author weixin 2023/6/5 2:50 PM
 */
public class TestList {

    @Test
    public void testSub(){
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        List<Integer> integers = list.subList(0, 5);
        List<Integer> integers1 = list.subList(5, list.size());
        System.out.println(integers);
        System.out.println(integers1);

        Collections.reverse(integers);
    }
}
