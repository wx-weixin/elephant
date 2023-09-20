package cn.wx.biz.test.allison1875.demo;


import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import com.google.common.collect.Lists;
import cn.wx.elephant.core.annotation.FieldAnnotation;
import cn.wx.elephant.core.utils.CompareUtils;
import lombok.Data;

/**
 * @author weixin 2023/9/19 10:56 AM
 */
public class TestClass {

    @Test
    public void test1() {
        A a = getA();
        A a1 = new A();
        String log = CompareUtils.compareObject(a1, a);
        System.out.println(log);

    }

    @Test
    public void test2() {
        long l = System.currentTimeMillis();
        Date a = new Date(l);
        Date b = new Date(l);
        Instant c = Instant.ofEpochMilli(l);
        Instant d = Instant.ofEpochMilli(l);
        System.out.println(a.equals(b));
        System.out.println(c.equals(d));
    }

    @Test
    public void test3() throws Exception {
        A a = A.class.getDeclaredConstructor().newInstance();
        System.out.println(a);
    }

    @Test
    public void test4() throws Exception {
        List<A> a = null;
        Collection<?> b = (Collection<?>)a;
        System.out.println(b);
        System.out.println(a instanceof Collection<?>);
    }

    private A getA() {
        A a = new A();
        a.setAge(new BigDecimal("2.1"));
        a.setName("张三");
        a.setC(new Date());
        a.setD(Instant.now());
        C c = new C();
        c.setCode("001");
        a.setListC(Lists.newArrayList(c));
        a.setE(Lists.newArrayList("a","b","c"));
        return a;
    }

    @Data
    public static class A {

        @FieldAnnotation(describe = "名称")
        private String name;

        @FieldAnnotation(describe = "年龄")
        private BigDecimal age;

        @FieldAnnotation(describe = "c")
        private Date c;

        @FieldAnnotation(describe = "d")
        private Instant d;

        @FieldAnnotation(describe = "e")
        private List<String> e;

        @FieldAnnotation(describe = "C的信息")
        private List<C> listC;

    }

    @Data
    public static class C{

        /**
         * 编码
         */
        @FieldAnnotation(describe = "编码")
        private String code;

    }

}
