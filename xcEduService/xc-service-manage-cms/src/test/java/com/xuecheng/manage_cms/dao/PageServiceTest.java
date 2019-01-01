package com.xuecheng.manage_cms.dao;

import com.xuecheng.manage_cms.service.PageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author LiYuan
 * Created on 2018/11/1.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PageServiceTest {

    @Autowired

    private PageService pageService;

    @Test
    public void test1() {
        String pageHtml = pageService.getPageHtml("5bf6deb2d8dc172ec44eccd9");
        System.out.println(pageHtml);
    }


}