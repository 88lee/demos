package com.xuecheng.manage_cms.service.impl;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import com.xuecheng.manage_cms.service.PageService;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

/**
 * @author LiYuan
 * Created on 2018/11/2.
 */
@Service
public class PageServiceImpl implements PageService {

    @Autowired
    CmsPageRepository cmsPageRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CmsTemplateRepository cmsTemplateRepository;

    @Autowired
    GridFsTemplate gridFsTemplate;

    @Autowired
    GridFSBucket gridFSBucket;

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

        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    /**
     * 新增页面
     *
     * @param cmsPage 页面
     * @return 新增结果
     */
    @Override
    public CmsPageResult add(CmsPage cmsPage) {
        if (cmsPage == null) {
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_PAGEINFOISNULL);
        }
        //根据siteId,pageName,pageWebPath确定唯一页面
        //先判断是否存在，然后添加
        CmsPage exitsCmsPage = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(),
            cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if (exitsCmsPage != null) {
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }
        cmsPage.setPageId(null);
        CmsPage save = cmsPageRepository.save(cmsPage);
        return new CmsPageResult(CommonCode.SUCCESS, save);
    }

    @Override
    public CmsPage findById(String id) {
        return cmsPageRepository.findById(id).orElse(new CmsPage());
    }

    @Override
    public CmsPageResult edit(String id, CmsPage cmsPage) {
        Optional<CmsPage> byId = cmsPageRepository.findById(id);
        if (byId.isPresent()) {
            CmsPage one = byId.get();
            //更新所属站点
            one.setSiteId(cmsPage.getSiteId());
            //更新模板id
            one.setTemplateId(cmsPage.getTemplateId());
            //更新页面名称
            one.setPageName(cmsPage.getPageName());
            //更新页面别名
            one.setPageAliase(cmsPage.getPageAliase());
            //更新访问路径
            one.setPageWebPath(cmsPage.getPageWebPath());
            //更新物理路径
            one.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            //数据Url
            one.setDataUrl(cmsPage.getDataUrl());
            //类型（静态/动态）
            one.setPageType(cmsPage.getPageType());
            //创建时间
            one.setPageCreateTime(cmsPage.getPageCreateTime());
            //执行更新
            CmsPage save = cmsPageRepository.save(one);
            //返回成功
            return new CmsPageResult(CommonCode.SUCCESS, save);
        }
        //返回失败
        return new CmsPageResult(CommonCode.FAIL, new CmsPage());
    }

    @Override
    public ResponseResult delete(String id) {
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if (optional.isPresent()) {
            cmsPageRepository.delete(optional.get());
            return ResponseResult.SUCCESS();
        } else {
            return ResponseResult.FAIL();
        }
    }

    /**
     * 静态化程序获取页面的DataUrl
     * 静态化程序远程请求DataUrl获取数据模型
     * 静态化程序获取页面的模板信息
     * 执行页面静态化
     *
     * @param pageId 页面Id
     * @return 页面信息
     */
    @Override
    public String getPageHtml(String pageId) {
        //获取数据模型
        Map model = getModelByPageId(pageId);
        if (model == null) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }
        //获取页面模板
        String templateContent = getTemplateByPageId(pageId);
        if (StringUtils.isEmpty(templateContent)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        return generateHtml(templateContent, model);
    }

    /**
     * 获取模型对象
     * @param pageId 页面id
     * @return 模型对象
     */
    private Map getModelByPageId(String pageId) {
        //页面信息
        CmsPage cmsPage = this.findById(pageId);
        if (cmsPage == null) {
            //页面不存在
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        //取dataUrl
        String dataUrl = cmsPage.getDataUrl();
        if (StringUtils.isEmpty(dataUrl)) {
            //dataUrl为空
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        }
        //通过restTemplate请求dataUrl数据下
        ResponseEntity<Map> forEntity = restTemplate.getForEntity(dataUrl, Map.class);
        return forEntity.getBody();
    }

    /**
     * 获取模板信息
     * @param pageId 页面id
     * @return 模板信息
     */
    private String getTemplateByPageId(String pageId) {
        //页面信息
        CmsPage cmsPage = this.findById(pageId);
        if (cmsPage == null) {
            //页面不存在
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        String templateId = cmsPage.getTemplateId();
        if (StringUtils.isEmpty(templateId)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        Optional<CmsTemplate> optional = cmsTemplateRepository.findById(templateId);
        if (optional.isPresent()) {
            CmsTemplate cmsTemplate = optional.get();
            //模板的文件Id
            String templateFileId = cmsTemplate.getTemplateFileId();
            //根据id查询文件
            GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
            //打开下载流对象
            if (gridFSFile != null) {
                GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
                //创建gridFsResource，用于获取流对象
                GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
                //获取流中的数据
                try {
                    return IOUtils.toString(gridFsResource.getInputStream(), "UTF-8");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private String generateHtml(String templateContent, Map model) {
        try {
            //生成配置类
            Configuration configuration = new Configuration(Configuration.getVersion());
            //模板加载器
            StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
            stringTemplateLoader.putTemplate("template", templateContent);
            //配置模板加载器
            configuration.setTemplateLoader(stringTemplateLoader);
            //获取模板
            Template template = configuration.getTemplate("template");
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
