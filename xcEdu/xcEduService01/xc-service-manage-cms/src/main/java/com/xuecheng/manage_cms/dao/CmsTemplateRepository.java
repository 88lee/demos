package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author LiYuan
 * Created on 2018/12/10.
 */
public interface CmsTemplateRepository extends MongoRepository<CmsTemplate, String> {

}
