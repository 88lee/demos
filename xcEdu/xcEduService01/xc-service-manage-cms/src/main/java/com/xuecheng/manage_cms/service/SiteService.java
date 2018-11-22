package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QuerySiteRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;

/**
 * @author LiYuan
 * Created on 2018/11/7.
 */
public interface SiteService {

    /**
     * 按条件分页查询列表
     *
     * @param page             页码
     * @param size             每页记录数
     * @param querySiteRequest 查询条件
     * @return 查询结果
     */
    QueryResponseResult findList(int page, int size, QuerySiteRequest querySiteRequest);

    /**
     * 按条件查询列表
     *
     * @param querySiteRequest 查询条件
     * @return 查询结果
     */
    QueryResponseResult findAllList(QuerySiteRequest querySiteRequest);

}
