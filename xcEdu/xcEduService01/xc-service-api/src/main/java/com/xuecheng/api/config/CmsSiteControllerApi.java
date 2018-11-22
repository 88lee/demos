package com.xuecheng.api.config;

import com.xuecheng.framework.domain.cms.request.QuerySiteRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author LiYuan Created on 2018/10/31.
 */
@Api(value = "cms站点管理接口", description = "cms站点管理接口，提供站点的增、删、改、查")
public interface CmsSiteControllerApi {

    @ApiOperation("分页查询站点列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "int"),
        @ApiImplicitParam(name = "size", value = "每页记录数", required = true, paramType = "path", dataType = "int") })
    QueryResponseResult findList(int page, int size, QuerySiteRequest querySiteRequest);

    @ApiOperation("查询所有站点列表")
    QueryResponseResult findAllList(QuerySiteRequest querySiteRequest);

}
