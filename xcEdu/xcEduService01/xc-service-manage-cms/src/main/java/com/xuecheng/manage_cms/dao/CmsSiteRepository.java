package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author LiYuan
 * Created on 2018/11/1.
 */
public interface CmsSiteRepository extends MongoRepository<CmsSite, String> {

}
