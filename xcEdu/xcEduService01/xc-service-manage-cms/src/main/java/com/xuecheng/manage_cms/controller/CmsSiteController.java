package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.config.CmsSiteControllerApi;
import com.xuecheng.framework.domain.cms.request.QuerySiteRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.manage_cms.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LiYuan
 * Created on 2018/11/7.
 */
@RestController
@RequestMapping("/cms/site")
public class CmsSiteController implements CmsSiteControllerApi {

    @Autowired
    SiteService siteService;

    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page") int page, @PathVariable("size") int size,
        QuerySiteRequest querySiteRequest) {
        return siteService.findList(page, size, querySiteRequest);
    }

    @Override
    @GetMapping("/list")
    public QueryResponseResult findList(QuerySiteRequest querySiteRequest) {
        return siteService.findList(querySiteRequest);
    }

}
