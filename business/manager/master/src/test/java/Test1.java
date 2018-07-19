import org.jsoup.Jsoup;
import org.junit.Test;

/**
 * Create by 18073 on 2018/7/8.
 */
public class Test1 {

    @Test
    public void test1(){
        System.out.println("sdfsafds");
    }

    @Test
    public void test2(){
        System.out.println(Jsoup.parse("<em>阿萨德撒的</em>阿萨德的撒<>sd<").text());
    }

}
