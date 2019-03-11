import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: YunHai
 * @Date: 2018/12/25 12:32
 * @Description:
 */
public class TestWork {

    @Test
    public void testString() {

        String str1 = "father";
        String str2 = "mather";
//        转字符数组
        char[] ch1 = str1.toCharArray();
        char[] ch2 = str2.toCharArray();

        String ok = "";
//        获取ch1和ch2的最后一位
        int j = str2.length() - 1;
//        循环判断 如果成立则继续循环 并拼接ok
        for (int i = str1.length() - 1; i > 0 && ch1[i] == ch2[j--]; i--){
            ok = ch1[i] + ok;
        }

//        如果没有 打印null
        if ("".equals(ok)) System.out.println("null");
        else System.out.println(ok);




    }


    @Test
    public void day12_27(){
        short i = 0;

        i += 1;
    }


    @Test
    public void day12_28(){
        System.out.println(~2);
    }

    @Test
    public void IOS8859() throws UnsupportedEncodingException {
        String resouce = "%5B%7B%22id%22%3A154333843223827%2C%22title%22%3A%22%E5%B0%8F%E7%B1%B38+%E5%85%A8%E9%9D%A2%E5%B1%8F%E6%B8%B8%E6%88%8F%E6%99%BA%E8%83%BD%E6%89%8B%E6%9C%BA+6GB%2B64GB+%E9%BB%91%E8%89%B2+%E5%85%A8%E7%BD%91%E9%80%9A4G+%E5%8F%8C%E5%8D%A1%E5%8F%8C%E5%BE%85%22%2C%22sellPoint%22%3A%22%E9%AA%81%E9%BE%99845%E5%A4%84%E7%90%86%E5%99%A8%EF%BC%8C%E5%8F%8C%E9%A2%91GPS%EF%BC%8C%E7%BA%A2%E5%A4%96%E4%BA%BA%E8%84%B8%E8%A7%A3%E9%94%81%EF%BC%8CAI%E5%8F%98%E7%84%A6%E5%8F%8C%E6%91%84%EF%BC%8CAI%E8%AF%AD%E9%9F%B3%E5%8A%A9%E6%89%8B%EF%BC%81%E5%B0%8F%E7%B1%B3%E7%88%86%E5%93%81%E7%89%B9%E6%83%A0%EF%BC%8C%E9%80%89%E5%93%81%E8%B4%A8%EF%BC%8C%E8%B4%AD%E5%B0%8F%E7%B1%B3%EF%BC%81%5Cr%5Cn%E9%80%89%E7%A7%BB%E5%8A%A8%EF%BC%8C%E4%BA%AB%E5%A4%A7%E6%B5%81%E9%87%8F%EF%BC%8C%E4%B8%8D%E6%8D%A2%E5%8F%B7%E8%B4%AD%E6%9C%BA%EF%BC%81%22%2C%22price%22%3A229900%2C%22num%22%3A1%2C%22barcode%22%3A%22124435314214%22%2C%22image%22%3A%22http%3A%2F%2F192.168.25.133%2Fgroup1%2FM00%2F00%2F00%2FwKgZhVv9eRSAASRHAAAQU9oMX1Y439.jpg%22%2C%22cid%22%3A560%2C%22status%22%3A1%2C%22created%22%3A1543338432000%2C%22updated%22%3A1543338432000%7D%5D";

        System.out.println(new String(resouce.getBytes("gbk"),"utf-8"));
    }

    @Test
    public void ListIsNull(){
//        ArrayList<String> arr = null;
        ArrayList<String> arr = new ArrayList<>();
        for (String s : arr){
            System.out.println(arr);
        }

        List<Integer> list = new ArrayList<Integer>(20);
        for (int i = 0; i < 10; i++){
            list.add(i);
        }
        System.out.println(list.size());

    }


    @Test
    public void test01_03(){
        Integer i = new Integer(1);
        int j = 1;
        char c = 2;
        showAB(j);
        showAB(c);
    }

    public void showAB(Integer i){

        System.out.println("Integer");
    }

    public void showAB(int i){
        System.out.println("int");
    }

    class A{
        void show(){
            System.out.println("class A");
        }
    }

    class B extends A{
        void show(){
            System.out.println("class B");
        }
    }



}
