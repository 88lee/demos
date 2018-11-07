package com.xuecheng.manage_cms.service.impl;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.request.QuerySiteRequest;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsSiteRepository;
import com.xuecheng.manage_cms.service.SiteService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author LiYuan
 * Created on 2018/11/7.
 */
@Service
public class SiteServiceImpl implements SiteService {

    @Autowired
    CmsSiteRepository cmsSiteRepository;

    @Override
    public QueryResponseResult findList(int page, int size, QuerySiteRequest querySiteRequest) {

        QueryResponseResult queryResponseResult;
        try {
            //分页
            Pageable pageable = PageRequest.of(page <= 0 ? 1 : page - 1, size <= 0 ? 10 : size);

            //查询条件
            if (querySiteRequest == null) {
                querySiteRequest = new QuerySiteRequest();
            }
            //条件值
            CmsSite cmsPage = new CmsSite();
            cmsPage.setSiteId(querySiteRequest.getSiteId());
            cmsPage.setSiteName(querySiteRequest.getSiteName());
            //匹配规则
            ExampleMatcher matcher = ExampleMatcher.matching();
            matcher = matcher.withMatcher("siteName", ExampleMatcher.GenericPropertyMatchers.contains());
            Example<CmsSite> example = Example.of(cmsPage, matcher);

            Page<CmsSite> all = cmsSiteRepository.findAll(example, pageable);

            QueryResult<CmsSite> queryResult = new QueryResult<>();
            queryResult.setList(all.getContent());
            queryResult.setTotal(all.getTotalElements());
            queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS, queryResult);
        } catch (Exception e) {
            QueryResult<CmsSite> queryResult = new QueryResult<>();
            queryResponseResult = new QueryResponseResult(CommonCode.SERVER_ERROR, queryResult);
        }
        return queryResponseResult;
    }

    @Override
    public QueryResponseResult findList(QuerySiteRequest querySiteRequest) {

        QueryResponseResult queryResponseResult;
        try {
            //查询条件
            if (querySiteRequest == null) {
                querySiteRequest = new QuerySiteRequest();
            }
            //条件值
            CmsSite cmsPage = new CmsSite();
            cmsPage.setSiteId(querySiteRequest.getSiteId());
            cmsPage.setSiteName(querySiteRequest.getSiteName());
            //匹配规则
            ExampleMatcher matcher = ExampleMatcher.matching();
            matcher = matcher.withMatcher("siteName", ExampleMatcher.GenericPropertyMatchers.contains());
            Example<CmsSite> example = Example.of(cmsPage, matcher);

            List<CmsSite> all = cmsSiteRepository.findAll(example);

            QueryResult<CmsSite> queryResult = new QueryResult<>();
            queryResult.setList(all);
            queryResult.setTotal(all.size());
            queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS, queryResult);
        } catch (Exception e) {
            QueryResult<CmsSite> queryResult = new QueryResult<>();
            queryResponseResult = new QueryResponseResult(CommonCode.SERVER_ERROR, queryResult);
        }
        return queryResponseResult;
    }

}
