package cn.wx.biz.test.allison1875.demo;

import org.junit.jupiter.api.Test;

/**
 * @author weixin 2023/9/19 10:38 AM
 */
public class TestInstance {


    @Test
    public void test1(){
        A a = new A();
        if(a instanceof B){
            System.out.println("A是B的实现类");
        }
        D d = new D();
        if(d instanceof C){
            System.out.println("D是C的子类");
        }
    }

    public static class A implements B{

    }
    public interface B{

    }
    public static class C{

    }
    public static class D extends C{

    }
}

