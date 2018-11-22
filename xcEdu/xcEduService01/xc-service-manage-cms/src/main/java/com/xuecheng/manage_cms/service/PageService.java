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

    /**
     * 分页查询页面列表
     *
     * @param page             页码
     * @param size             每页记录数
     * @param queryPageRequest 查询条件
     * @return 查询结果
     */
    QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);

    /**
     * 新增页面
     *
     * @param cmsPage 页面信息
     * @return 新增结果
     */
    CmsPageResult add(CmsPage cmsPage);

    /**
     * 根据ID查询页面信息
     *
     * @param id 页面id
     * @return 页面信息
     */
    CmsPage findById(String id);

    /**
     * 修改页面
     *
     * @param id   页面id
     * @param page 页面信息
     * @return 修改结果
     */
    CmsPageResult edit(String id, CmsPage cmsPage);

}
