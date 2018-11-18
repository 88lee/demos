package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;

/**
 * @author LiYuan
 * Created on 2018/11/2.
 */
public interface PageService {

    QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);

    CmsPageResult add(CmsPage cmsPage);
}
