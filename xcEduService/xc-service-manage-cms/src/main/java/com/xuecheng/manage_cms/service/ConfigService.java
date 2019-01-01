package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsConfig;

/**
 * @author LiYuan
 * Created on 2018/11/28.
 */
public interface ConfigService {

    /**
     * 根据id查询config数据
     * @param id id
     * @return config数据
     */
    CmsConfig getConfigById(String id);

}
