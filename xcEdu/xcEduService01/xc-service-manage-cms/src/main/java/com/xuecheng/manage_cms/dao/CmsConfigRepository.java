package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author LiYuan
 * Created on 2018/11/28.
 */
public interface CmsConfigRepository extends MongoRepository<CmsConfig, String> {

}
