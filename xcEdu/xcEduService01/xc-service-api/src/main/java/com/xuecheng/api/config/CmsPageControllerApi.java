package com.xuecheng.api.config;

import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;

/**
 * @author LiYuan
 * Created on 2018/10/31.
 */
public interface CmsPageControllerApi {

    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);
}
