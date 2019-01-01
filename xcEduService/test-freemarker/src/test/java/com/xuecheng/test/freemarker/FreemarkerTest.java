package com.xuecheng.test.freemarker;

import com.xuecheng.test.freemarker.model.Student;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

/**
 * @author LiYuan
 * Created on 2018/11/28.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class FreemarkerTest {

    //测试静态化，基于ftl模板文件生成html文件
    @Test
    public void testGenerateHtml() throws IOException, TemplateException {
        //定义配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        //获取classpath路径
        String path = this.getClass().getResource("/").getPath();
        //定义模板路径
        configuration.setDirectoryForTemplateLoading(new File(path + "/templates"));
        //获取模板文件
        Template template = configuration.getTemplate("test1.ftl");
        //定义数据模型
        Map map = getMap();
        //静态化
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        //导出html文件
        InputStream inputStream = IOUtils.toInputStream(content);
        FileOutputStream outputStream = new FileOutputStream(new File("d:/test1.html"));
        IOUtils.copy(inputStream, outputStream);
        inputStream.close();
        outputStream.close();
    }

    //测试静态化，基于字符串配合模板加载器生成html文件
    @Test
    public void testGenerateHtmlByString() throws IOException, TemplateException {
        //定义配置类
        Configuration configuration = new Configuration(Configuration.getVersion());

        //模板内容，这里测试时使用简单的字符串作为模板
        String templateString = "<html><head></head><body>名称：${name}</body></html>";
        //模板加载器
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template", templateString);
        configuration.setTemplateLoader(stringTemplateLoader);
        //得到模板
        Template template = configuration.getTemplate("template", "utf‐8");

        //定义数据模型
        Map map = getMap();
        //静态化
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        //导出html文件
        InputStream inputStream = IOUtils.toInputStream(content);
        FileOutputStream outputStream = new FileOutputStream(new File("d:/test1.html"));
        IOUtils.copy(inputStream, outputStream);
        inputStream.close();
        outputStream.close();
    }

    //获取数据模型
    public Map getMap() {
        Map model = new HashMap();
        //向数据模型放数据
        model.put("name", "黑马程序员");
        Student stu1 = new Student();
        stu1.setName("小明");
        stu1.setAge(18);
        stu1.setMoney(1000.86f);
        stu1.setBirthday(new Date());
        Student stu2 = new Student();
        stu2.setName("小红");
        stu2.setMoney(200.1f);
        stu2.setAge(19);
        stu2.setBirthday(new Date());
        List<Student> friends = new ArrayList<>();
        friends.add(stu1);
        stu2.setFriends(friends);
        stu2.setBestFriend(stu1);
        List<Student> stus = new ArrayList<>();
        stus.add(stu1);
        stus.add(stu2);
        //向数据模型放数据
        model.put("stus", stus);
        //准备map数据
        HashMap<String, Student> stuMap = new HashMap<>();
        stuMap.put("stu1", stu1);
        stuMap.put("stu2", stu2);
        //向数据模型放数据
        model.put("stu1", stu1);
        //向数据模型放数据
        model.put("stuMap", stuMap);
        model.put("point", 999999999);
        //返回模板文件名称,基于resources/templates路径
        return model;
    }

}
