package com.xuecheng.manage_cms.service.impl;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.manage_cms.dao.CmsConfigRepository;
import com.xuecheng.manage_cms.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author LiYuan
 * Created on 2018/11/28.
 */
@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private CmsConfigRepository cmsConfigRepository;

    @Override
    public CmsConfig getConfigById(String id) {
        return cmsConfigRepository.findById(id).orElse(new CmsConfig());
    }

}
