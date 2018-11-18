package com.xuecheng.manage_cms.service.impl;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author LiYuan
 * Created on 2018/11/2.
 */
@Service
public class PageServiceImpl implements PageService {

    @Autowired
    CmsPageRepository cmsPageRepository;

    /**
     * 分页查询
     *
     * @param page             页码，1从1开始计数
     * @param size             每页记录数
     * @param queryPageRequest 查询条件
     * @return 查询结果
     */
    @Override
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest) {
        QueryResponseResult queryResponseResult;
        try {
            //分页
            Pageable pageable = PageRequest.of(page <= 0 ? 1 : page - 1, size <= 0 ? 10 : size);

            //查询条件
            if (queryPageRequest == null) {
                queryPageRequest = new QueryPageRequest();
            }
            //条件值
            CmsPage cmsPage = new CmsPage();
            cmsPage.setSiteId(queryPageRequest.getSiteId());
            cmsPage.setPageId(queryPageRequest.getPageId());
            cmsPage.setPageName(queryPageRequest.getPageName());
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
            cmsPage.setTemplateId(queryPageRequest.getTemplateId());
            //匹配规则
            ExampleMatcher matcher = ExampleMatcher.matching();
            matcher = matcher.withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
            Example<CmsPage> example = Example.of(cmsPage, matcher);

            Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);

            QueryResult<CmsPage> queryResult = new QueryResult<>();
            queryResult.setList(all.getContent());
            queryResult.setTotal(all.getTotalElements());
            queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS, queryResult);
        } catch (Exception e) {
            e.printStackTrace();
            QueryResult<CmsPage> queryResult = new QueryResult<>();
            queryResponseResult = new QueryResponseResult(CommonCode.SERVER_ERROR, queryResult);
        }
        return queryResponseResult;
    }

    /**
     * 新增页面
     *
     * @param cmsPage 页面
     * @return 新增结果
     */
    @Override
    public CmsPageResult add(CmsPage cmsPage) {
        CmsPageResult cmsPageResult;
        try {
            //根据siteId,pageName,pageWebPath确定唯一页面
            //先判断是否存在，然后添加
            CmsPage exitsCmsPage = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(),
                cmsPage.getSiteId(), cmsPage.getPageWebPath());
            if (exitsCmsPage == null) {
                cmsPage.setPageId(null);
                CmsPage save = cmsPageRepository.save(cmsPage);
                cmsPageResult = new CmsPageResult(CommonCode.SUCCESS, save);
            } else {
                cmsPageResult = new CmsPageResult(CommonCode.FAIL, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            cmsPageResult = new CmsPageResult(CommonCode.SERVER_ERROR, null);
        }
        return cmsPageResult;
    }

}