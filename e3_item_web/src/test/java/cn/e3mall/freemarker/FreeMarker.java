package cn.e3mall.freemarker;


import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;


import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.*;

/**
 * @Auther: YunHai
 * @Date: 2018/12/19 21:17
 * @Description: first use FreeMarker
 */
public class FreeMarker {

    @Test
    public void testFreeMarker() throws Exception{
//        创建模板文件
//        创建一个Configuration对象
        Configuration configuration = new Configuration(Configuration.getVersion());
//        设置模板保存的目录
        configuration.setDirectoryForTemplateLoading(new File("D:\\ideaWork\\e3\\e3_item_web\\src\\main\\webapp\\WEB-INF\\ftl"));
//        设置默认的模板编码格式
        configuration.setDefaultEncoding("utf-8");

//        加载一个模板, 创建模板对象
        Template template = configuration.getTemplate("hello.ftl");
//        创建一个Map键值对 也可以是pojo
        Map data = new HashMap();
        data.put("hello", "helloFreeMarker! 中文");

//        生成的静态页面的存放路径
        Writer writer = new FileWriter(new File("D:\\ideaWork\\e3\\e3_item_web\\src\\main\\webapp\\WEB-INF\\jsp\\hello.txt"));
//        生成 传入键值对和目标路径
        template.process(data, writer);

        writer.close();


    }

    @Test
    public void testPojoFreeMarker() throws Exception{
        Configuration configuration = new Configuration(Configuration.getVersion());
        configuration.setDirectoryForTemplateLoading(new File("D:\\ideaWork\\e3\\e3_item_web\\src\\main\\webapp\\WEB-INF\\ftl"));
        configuration.setDefaultEncoding("utf-8");

        Template tamplate = configuration.getTemplate("student.ftl");

        Writer writer = new FileWriter(new File("D:\\ideaWork\\e3\\e3_item_web\\src\\main\\webapp\\WEB-INF\\jsp\\student.html"));

        Map data = new HashMap();
        data.put("student", new Student(1,"中文",18,"moscow"));
        tamplate.process(data,writer);
        writer.close();
    }

    @Test
    public void testListFreeMarker() throws Exception{
        Configuration configuration = new Configuration(Configuration.getVersion());
        configuration.setDirectoryForTemplateLoading(new File("D:\\ideaWork\\e3\\e3_item_web\\src\\main\\webapp\\WEB-INF\\ftl"));
        configuration.setDefaultEncoding("utf-8");

        Template template = configuration.getTemplate("studentList.ftl");

        Writer writer = new FileWriter(new File("D:\\ideaWork\\e3\\e3_item_web\\src\\main\\webapp\\WEB-INF\\jsp\\student.html"));

        List stuList = new ArrayList<Student>(10);

        stuList.add(new Student(1,"中文",18,"moscow"));
        stuList.add(new Student(2,"中文",18,"moscow"));
        stuList.add(new Student(3,"中文",18,"moscow"));
        stuList.add(new Student(4,"中文",18,"moscow"));
        stuList.add(new Student(5,"中文",18,"moscow"));
        stuList.add(new Student(6,"中文",18,"moscow"));
        stuList.add(new Student(7,"中文",18,"moscow"));
        stuList.add(new Student(8,"中文",18,"moscow"));
        stuList.add(new Student(9,"中文",18,"moscow"));
        stuList.add(new Student(10,"中文",18,"moscow"));

        Map data = new HashMap();
        data.put("stuList",stuList);

        template.process(data, writer);

        writer.close();

    }

    @Test
    public void textDateFreeMarker() throws Exception{
        Configuration configuration = new Configuration(Configuration.getVersion());
        configuration.setDirectoryForTemplateLoading(new File("D:\\ideaWork\\e3\\e3_item_web\\src\\main\\webapp\\WEB-INF\\ftl"));
        configuration.setDefaultEncoding("utf-8");

        Template template = configuration.getTemplate("studentDate.ftl");

        Writer writer = new FileWriter(new File("D:\\ideaWork\\e3\\e3_item_web\\src\\main\\webapp\\WEB-INF\\jsp\\student.html"));

        Map data = new HashMap();
        data.put("date" , new Date());

        template.process(data, writer);

        writer.close();

    }

    @Test
    public void testNullFreeMarker() throws Exception{
        Configuration configuration = new Configuration(Configuration.getVersion());
        configuration.setDirectoryForTemplateLoading(new File("D:\\ideaWork\\e3\\e3_item_web\\src\\main\\webapp\\WEB-INF\\ftl"));
        configuration.setDefaultEncoding("utf-8");

        Template template = configuration.getTemplate("studentNull.ftl");
        Writer writer = new FileWriter(new File("D:\\ideaWork\\e3\\e3_item_web\\src\\main\\webapp\\WEB-INF\\jsp\\student.html"));
        Map date = new HashMap();
        date.put("hello", "from hello.ftl");
        template.process(date,writer);
        writer.close();

    }





}
